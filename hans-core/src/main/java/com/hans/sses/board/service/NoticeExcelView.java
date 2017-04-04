package com.hans.sses.board.service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hans.sses.board.model.BoadNotice;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class NoticeExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileName = URLEncoder.encode("test.xls", "utf-8");
				
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		HSSFSheet sheet = createFirstSheet(workbook);
		
		//공통 적용 스타일
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// head style
		HSSFCellStyle headStyle = workbook.createCellStyle();
		headStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// head 생성
		createColumnLabel(sheet, headStyle);
		
		// body 생성
		List<BoadNotice> list = (List<BoadNotice>) model.get("list");
		for (int i = 0; i <= list.size() - 1; i++) {
			createPageRow(sheet, style, list, i);
		}
	}
	
	// sheet 생성
	private HSSFSheet createFirstSheet(HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "sheet_1");
		sheet.setDefaultRowHeight((short)(30*20));
		for (int i = 0; i < 5; i++) {
			sheet.setColumnWidth(i, 256*30);
		}
		
		return sheet;
	}
	
	// head 생성
	private void createColumnLabel(HSSFSheet sheet, HSSFCellStyle headStyle) {
	
		// Row(0) 생성
		HSSFRow firstRow = sheet.createRow(0);
		
		HSSFCell cell = null;
		for (int i = 0; i < 5; i++) {
			cell = firstRow.createCell(i);
			cell.setCellStyle(headStyle);
			switch(i) {
				case 0 : cell.setCellValue("No.");			break;
				case 1 : cell.setCellValue("SN_ID");		break;
				case 2 : cell.setCellValue("TITLE");		break;
				case 3 : cell.setCellValue("FST_RG_DT");	break;
				case 4 : cell.setCellValue("DISPLAY_YN");	break;
			}
		}
	}
	
	// body 생성
	private void createPageRow(HSSFSheet sheet, HSSFCellStyle style, List<BoadNotice> list, int rowNum) {
		HSSFRow row = sheet.createRow(rowNum + 1);
		
		HSSFCell cell = null;
		for (int i = 0; i < 5; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			switch(i) {
				case 0 : cell.setCellValue(rowNum + 1);	break;
				case 1 : cell.setCellValue(list.get(rowNum).getSeq());	break;
				case 2 : cell.setCellValue(list.get(rowNum).getTitle());	break;
				case 3 : cell.setCellValue(list.get(rowNum).getRegDate().toString());	break;
				case 4 : cell.setCellValue(list.get(rowNum).getDisplayYn());	break;
			}
		}
	}
	
	
	
	
}
