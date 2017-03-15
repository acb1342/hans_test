package com.mobilepark.doit5.cms.statistics.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uangel.platform.collection.BaseData;

/*==================================================================================
 * @Project      : opscc-admin
 * @Package      : com.opscc.cms.statistics.view
 * @Filename     : PushStatExcelView.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2015. 2. 17.       Initial Coding & Update
 * =================================================================================
 */
public class PushStat1HExcelView extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			HSSFWorkbook wb,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFSheet sheet = wb.createSheet();
		int rowIdx = 0;

		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFillForegroundColor(HSSFColor.TURQUOISE.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

		String[] titleList = {
			"일자", "성공 수", "실패 수", "합계"
		};
		HSSFRow titleRow = sheet.createRow(rowIdx++);
		for (int i = 0; i < titleList.length; i++) {
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(new HSSFRichTextString(titleList[i]));
			cell.setCellStyle(titleStyle);
		}

		HSSFCellStyle numStyle = wb.createCellStyle();
		numStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0"));

		HSSFCellStyle percentStyle = wb.createCellStyle();
		percentStyle.setDataFormat(wb.createDataFormat().getFormat("0%"));

		List<BaseData> pushStatList = (List) model.get("list");
		BaseData totalCnt = (BaseData) model.get("totalcnt");
		for (BaseData bd : pushStatList) {
			HSSFRow dataRow = sheet.createRow(rowIdx++);

			HSSFCell dateCell = dataRow.createCell(0);
			dateCell.setCellValue(new HSSFRichTextString(bd.getString("stat_date")));

			HSSFCell cntCell = dataRow.createCell(1);
			cntCell.setCellValue(bd.getInt("successCnt"));
			cntCell.setCellStyle(numStyle);

			HSSFCell uncntCell = dataRow.createCell(2);
			uncntCell.setCellValue(bd.getInt("failCnt"));
			uncntCell.setCellStyle(numStyle);

			HSSFCell totCell = dataRow.createCell(3);
			totCell.setCellValue(bd.getInt("total"));
			totCell.setCellStyle(numStyle);
		}

		HSSFRow dataRow = sheet.createRow(rowIdx++);

		HSSFCell totalCell = dataRow.createCell(0);
		totalCell.setCellValue(new HSSFRichTextString("Total"));
		totalCell.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCell successCntTotCell = dataRow.createCell(1);
		successCntTotCell.setCellValue(totalCnt.getInt("successCnt"));
		successCntTotCell.setCellStyle(numStyle);

		HSSFCell failCntTotCell = dataRow.createCell(2);
		failCntTotCell.setCellValue(totalCnt.getInt("failCnt"));
		failCntTotCell.setCellStyle(numStyle);

		HSSFCell totalPushCell = dataRow.createCell(3);
		totalPushCell.setCellValue(totalCnt.getInt("totalPush"));
		totalPushCell.setCellStyle(numStyle);

		for (int i = 0; i < titleList.length; i++) {
			sheet.autoSizeColumn((short) i);
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + this.createFileName(model) + "\"");
	}

	private String createFileName(Map<String, Object> model) {
		String fromDate = (String) model.get("fromDate");
		String toDate = (String) model.get("toDate");

		StringBuilder sb = new StringBuilder("PushStat");
		if (StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)) {
			sb.append(String.format("_%s_%s", fromDate, toDate));
		}
		sb.append(".xls");

		return sb.toString();
	}
}
