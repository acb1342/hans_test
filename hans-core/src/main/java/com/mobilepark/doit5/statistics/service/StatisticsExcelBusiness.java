package com.mobilepark.doit5.statistics.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.mobilepark.doit5.common.util.POIStyles;

public class StatisticsExcelBusiness
{
	protected Workbook wb;
	
	protected void createHeader(Sheet sheet, String[] header)
	{
		Row row = sheet.createRow(0);
		for (int i=0 ; i<header.length ; i++)
		{
			Cell cell = row.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(POIStyles.getStyles(wb, POIStyles.GREY_SELL));
		}
	}
	
	protected void createHeader(Sheet sheet, String[] header, int rowNum)
	{
		Row row = sheet.createRow(rowNum);
		for (int i=1 ; i<header.length ; i++)
		{
			Cell cell = row.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(POIStyles.getStyles(wb, POIStyles.GREY_SELL));
		}
	}
	
	protected Row createRow(Sheet sheet, Object obj, String[] fields)
	{
		int rowNum = sheet.getLastRowNum();
		Row row = sheet.createRow(rowNum+1);
		
		for (int j=0 ; j<fields.length ; j++)
		{
			Cell cell = row.createCell(j);
			
			Object value = getValue(obj, fields[j]);
			if (value instanceof Double) cell.setCellValue(((Double) value).doubleValue());
			else if (value instanceof Integer) cell.setCellValue(((Integer) value).intValue());
			else if (value instanceof Long) cell.setCellValue(((Long) value).longValue());
			else if (value instanceof Date) cell.setCellValue((Date) value);
			else cell.setCellValue((String)value);
			
			cell.setCellStyle(POIStyles.getStyles(wb, POIStyles.CONTENT_BORDER));
		}
		
		return row;
	}
	
	protected Row createRow(Sheet sheet, Map<String, Object> map, String[] fields)
	{
		int rowNum = sheet.getLastRowNum();
		Row row = sheet.createRow(rowNum+1);
		
		for (int j=0 ; j<fields.length ; j++)
		{
			Cell cell = row.createCell(j);
			
			Object value = map.get(fields[j]);
			if (value instanceof Double) cell.setCellValue(((Double) value).doubleValue());
			else if (value instanceof Integer) cell.setCellValue(((Integer) value).intValue());
			else if (value instanceof Long) cell.setCellValue(((Long) value).longValue());
			else if (value instanceof Float) cell.setCellValue(((Float) value).floatValue());
			else if (value instanceof Date) cell.setCellValue((Date) value);
			else cell.setCellValue((String)value);
			
			cell.setCellStyle(POIStyles.getStyles(wb, POIStyles.CONTENT_BORDER));
		}
		
		return row;
	}
	
	private Object getValue(Object obj, String field)
	{
		Object value = "";
		try
		{
			String firstStr = field.substring(0, 1).toUpperCase();
			String name = "get" + firstStr + field.substring(1);
			
			Method method = obj.getClass().getMethod(name);
			value = method.invoke(obj, null);
		}
		catch(Exception e) {}
		
		return value;
	}
	
	protected Cell createCell(Sheet sheet, int rowNum, int cellNum, Integer heightInPoints) throws Exception
	{
		Row row = sheet.createRow(rowNum);
		if(heightInPoints != 0) row.setHeightInPoints((float)heightInPoints);
		Cell cell = row.createCell(cellNum);
		return cell;
	}

	protected Cell createCell(Sheet sheet, int rowNum, int cellNum, Integer heightInPoints, String value) throws Exception
	{
		Row row = sheet.createRow(rowNum);
		if(heightInPoints != 0) row.setHeightInPoints((float)heightInPoints);
		Cell cell = row.createCell(cellNum);
		cell.setCellValue(value);
		return cell;
	}
}
