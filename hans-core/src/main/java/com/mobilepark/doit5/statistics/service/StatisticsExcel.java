package com.mobilepark.doit5.statistics.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public interface StatisticsExcel<E>
{
	public Workbook getWorkbook(String name, List<E> list) throws Exception;
}
