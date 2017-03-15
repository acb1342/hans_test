package com.mobilepark.doit5.cms.history.view;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uangel.platform.collection.BaseData;

@Component
public class PushHistoryExcelView extends AbstractExcelView {
	@Autowired
	private MessageSourceAccessor msgSrcAccessor;

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
				this.msgSrcAccessor.getMessage("label.history.pushHistory.histDate"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.requestNo"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.cpId"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.cpName"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.sendReqType"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.os"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.appId"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.groupId"),
				this.msgSrcAccessor.getMessage("label.history.pushHistory.status")
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

		HSSFCellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy/mm/dd"));

		List<BaseData> historyList = (List) model.get("histories");

		for (BaseData bd : historyList) {
			HSSFRow dataRow = sheet.createRow(rowIdx++);

			HSSFCell dateCell = dataRow.createCell(0);
			dateCell.setCellValue(new HSSFRichTextString(bd.getString("histDate")));

			HSSFCell requestNoCell = dataRow.createCell(1);
			requestNoCell.setCellValue(new HSSFRichTextString(bd.getString("requestNo")));

			HSSFCell cpIdCell = dataRow.createCell(2);
			cpIdCell.setCellValue(new HSSFRichTextString(bd.getString("cpId")));

			HSSFCell cpNameCell = dataRow.createCell(3);
			cpNameCell.setCellValue(new HSSFRichTextString(bd.getString("cpName")));

			HSSFCell sendReqTypeCell = dataRow.createCell(4);
			sendReqTypeCell.setCellValue(new HSSFRichTextString(bd.getString("sendReqType")));

			HSSFCell osCell = dataRow.createCell(5);
			osCell.setCellValue(new HSSFRichTextString(bd.getString("os")));

			HSSFCell appIdCell = dataRow.createCell(6);
			appIdCell.setCellValue(new HSSFRichTextString(bd.getString("appId")));

			HSSFCell groupIdCell = dataRow.createCell(7);
			groupIdCell.setCellValue(new HSSFRichTextString(bd.getString("groupId")));

			HSSFCell statusCell = dataRow.createCell(8);
			statusCell.setCellValue(new HSSFRichTextString(bd.getString("status")));
		}

		// HSSFRow dataRow = sheet.createRow(rowIdx++);
		//
		// HSSFCell totalCell = dataRow.createCell(0);
		// totalCell.setCellValue(new HSSFRichTextString("Total"));
		// totalCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		//
		// HSSFCell successCntTotCell = dataRow.createCell(1);
		// successCntTotCell.setCellValue(totalCnt.getInt("successCnt"));
		// successCntTotCell.setCellStyle(numStyle);
		//
		// HSSFCell failCntTotCell = dataRow.createCell(2);
		// failCntTotCell.setCellValue(totalCnt.getInt("failCnt"));
		// failCntTotCell.setCellStyle(numStyle);
		//
		// HSSFCell totalPushCell = dataRow.createCell(3);
		// totalPushCell.setCellValue(totalCnt.getInt("totalPush"));
		// totalPushCell.setCellStyle(numStyle);

		for (int i = 0; i < titleList.length; i++) {
			sheet.autoSizeColumn((short) i);
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + this.createFileName(model) + "\"");
	}

	private String createFileName(Map<String, Object> model) {
		String fromDate = (String) model.get("fromDate");
		String toDate = (String) model.get("toDate");

		StringBuilder sb = new StringBuilder("PushHistory");
		if (StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)) {
			sb.append(String.format("_%s_%s", fromDate, toDate));
		}
		sb.append(".xls");

		return sb.toString();
	}
}
