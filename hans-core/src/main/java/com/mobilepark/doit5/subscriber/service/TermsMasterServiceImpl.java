package com.mobilepark.doit5.subscriber.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.common.DeviceType;
import com.mobilepark.doit5.common.TermsType;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.subscriber.dao.TermsMasterDao;
import com.mobilepark.doit5.subscriber.model.TermsMaster;
import com.mobilepark.doit5.subscriber.model.TermsMaster.TermsId;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.service.AbstractGenericService;
import com.uangel.platform.util.DateUtil;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.FileUtil;
import com.uangel.platform.util.TarGzUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.service
 * @Filename     : TermsMasterServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
@Transactional
public class TermsMasterServiceImpl extends AbstractGenericService<TermsMaster, TermsMaster.TermsId> implements TermsMasterService {
	@Autowired
	private TermsMasterDao termsMasterDao;

	@Override
	protected GenericDao<TermsMaster, TermsMaster.TermsId> getGenericDao() {
		return this.termsMasterDao;
	}

	@Override
	public TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType) {
		return this.getLastTerms(deviceType, termsType, null);
	}

	@Override
	public TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType, String termsVerId) {
		return this.termsMasterDao.getLastTerms(deviceType, termsType, termsVerId);
	}

	@Override
	public List<TermsMaster> searchByType(DeviceType deviceType, TermsType termsType) {
		return this.termsMasterDao.searchByType(deviceType, termsType);
	}

	@Override
	public TermsMaster get(DeviceType deviceType, TermsType type, String version) {
		return this.termsMasterDao.get(deviceType, type, version);
	}

	@Override
	public int delete(TermsId id) {
		TermsMaster termsMaster = this.get(id);
		termsMaster.setUseFlag(UseFlag.N);

		return this.termsMasterDao.update(termsMaster);
	}

	@Override
	public int delete(DeviceType deviceType, TermsType type, String version) {
		TermsMaster termsMaster = this.get(deviceType, type, version);
		termsMaster.setUseFlag(UseFlag.N);

		return this.termsMasterDao.update(termsMaster);
	}

	@Override
	public TermsMaster create(TermsMaster termsMaster) {
		String version = this.newVersion(termsMaster.getDeviceType(), termsMaster.getTermsType());
		termsMaster.setVersion(version);
		try {
			this.saveTermsFile(termsMaster);
		} catch (IOException e) {
			TraceLog.printStackTrace(e);
			return null;
		}

		return super.create(termsMaster);
	}

	private String newVersion(DeviceType deviceType, TermsType type) {
		String today = DateUtil.dateToString(new Date(), "yyyyMMdd");
		int newVersionSeq = 1;
		String maxVersion = this.termsMasterDao.maxVersion(deviceType, type, today);
		if (maxVersion != null) {
			try {
				newVersionSeq += Integer.parseInt(maxVersion.substring(maxVersion.length() - 3));
			} catch (NumberFormatException e) {
				TraceLog.error(e.getMessage(), e);
			}
		}

		return String.format("%s%03d", today, newVersionSeq);
	}

	@Override
	public int update(TermsMaster termsMaster) {
		try {
			this.saveTermsFile(termsMaster);
		} catch (IOException e) {
			TraceLog.printStackTrace(e);
			return 0;
		}

		return super.update(termsMaster);
	}

	private void saveTermsFile(TermsMaster termsMaster) throws IOException {
		DeviceType deviceType = termsMaster.getDeviceType();
		TermsType type = termsMaster.getTermsType();
		String version = termsMaster.getVersion();
		String termsSumBefore = termsMaster.getTermsSumBefore();
		String termsSumAfter = termsMaster.getTermsSumAfter();
		String content = termsMaster.getTermsContent();

		String uploadDir = Env.get("terms.file.uploadDir");
		if (StringUtils.isNotEmpty(termsMaster.getTermsFilePath())) {
			File oldTermsFile = new File(uploadDir, termsMaster.getTermsFilePath());
			if (oldTermsFile.exists()) {
				boolean result = oldTermsFile.delete();
				TraceLog.info("delete old terms file [file:%s, result:%s]", oldTermsFile.getAbsoluteFile(),
						result ? "SUCCESS" : "FAIL");
			} else {
				TraceLog.warn("old terms file not exist! [%s]", oldTermsFile.getAbsoluteFile());
			}
		}

		FileUtil.makeDirectory(uploadDir);

		TermsMaster tm = this.getLastTerms(deviceType, type, termsMaster.getVersion());
		String allBefore = type.name().toLowerCase() + "_all_before_" + version + ".txt";
		File allBeforeFile = new File(allBefore);
		if (tm != null && StringUtils.isNotBlank(tm.getTermsContent())) {
			FileUtil.writeByteArrayToFile(allBeforeFile, tm.getTermsContent().replaceAll("<!--\\[", "<!-- \\[").replaceAll("\\]-->", "\\] -->")
					.replaceAll("&lt;!--\\[", "&lt;!-- \\[").replaceAll("\\]--&gt;", "\\] --&gt;").getBytes());
		} else {
			FileUtil.writeStringToFile(allBeforeFile, "");
		}

		String allAfter = type.name().toLowerCase() + "_all_after_" + version + ".txt";
		File allAfterFile = new File(allAfter);
		FileUtil.writeByteArrayToFile(allAfterFile, content.replaceAll("<!--\\[", "<!-- \\[").replaceAll("\\]-->", "\\] -->")
				.replaceAll("&lt;!--\\[", "&lt;!-- \\[").replaceAll("\\]--&gt;", "\\] --&gt;").getBytes());

		String sumBefore = type.name().toLowerCase() + "_sum_before_" + version + ".txt";
		File sumBeforeFile = new File(sumBefore);
		FileUtil.writeByteArrayToFile(sumBeforeFile, termsSumBefore.replaceAll("<!--\\[", "<!-- \\[").replaceAll("\\]-->", "\\] -->")
				.replaceAll("&lt;!--\\[", "&lt;!-- \\[").replaceAll("\\]--&gt;", "\\] --&gt;").getBytes());

		String sumAfter = type.name().toLowerCase() + "_sum_after_" + version + ".txt";
		File sumAfterFile = new File(sumAfter);
		FileUtil.writeByteArrayToFile(sumAfterFile, termsSumAfter.replaceAll("<!--\\[", "<!-- \\[").replaceAll("\\]-->", "\\] -->")
				.replaceAll("&lt;!--\\[", "&lt;!-- \\[").replaceAll("\\]--&gt;", "\\] --&gt;").getBytes());

		// compress
		String allTar = type.name().toLowerCase() + "_" + version + ".tar";
		String allGzip = allTar + ".gz";

		File allTarFile = new File(allTar);
		File allGzipFile = new File(uploadDir, allGzip);

		TarGzUtil.tar(allTarFile, allBeforeFile, allAfterFile, sumBeforeFile, sumAfterFile);

		TarGzUtil.gzip(allTarFile, allGzipFile);

		if (allBeforeFile != null) {
			allBeforeFile.delete();
		}
		allAfterFile.delete();
		sumBeforeFile.delete();
		sumAfterFile.delete();
		allTarFile.delete();

		TraceLog.info("write terms archive file [file:%s]", allGzipFile.getAbsoluteFile());

		termsMaster.setTermsFilePath(allGzip);
	}

	@Override
	public List<TermsMaster> searchByTerms(TermsMaster termsMaster, int page, int rowPerPage) {
		return this.termsMasterDao.searchByTerms(termsMaster, page, rowPerPage);
	}

	@Override
	public long countByTerms(TermsMaster termsMaster) {
		return this.termsMasterDao.countByTerms(termsMaster);
	}
}
