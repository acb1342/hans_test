package com.mobilepark.doit5.statistics.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KepcoExcelServiceImpl extends StatisticsExcelBusiness implements StatisticsExcel<Map<String, Object>> 
{
	public KepcoExcelServiceImpl() 
	{
		super.wb = new XSSFWorkbook();
	}
	
	@Override
	public Workbook getWorkbook(String name, List<Map<String, Object>> list) throws Exception
	{
		Sheet sheet = wb.createSheet(name);
		
		sheet.setColumnWidth(0, 10*256);
		sheet.setColumnWidth(1, 10*256);
		sheet.setColumnWidth(2, 20*256);
		sheet.setColumnWidth(3, 10*256);
		sheet.setColumnWidth(4, 10*256);
		sheet.setColumnWidth(5, 10*256);
		sheet.setColumnWidth(6, 10*256);
			
		String[] header = {"정산대상기간","건물명","상세동명","주소","납기일","충전 전력량(KWh)","사용자 충전이용금액(원)"};
		super.createHeader(sheet, header);
		
		String[] fields = {"","BD_GROUP_NAME","BD_NAME","ADDR","PERIOD_DAY","CHARGE_AMT","CHARGE_FEE"};
		
		for (Map<String, Object> map : list)
		{
			Row row = super.createRow(sheet, map, fields);
			
			Cell cell = row.getCell(0);
			
			String period = map.get("START_YMD").toString() + "~" + map.get("END_YMD").toString();
			cell.setCellValue(period);
		}
		return wb;
	}
}
