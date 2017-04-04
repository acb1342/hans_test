package com.hans.sses.common.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class POIStyles 
{
	/* ***************** Settlement *********************** */
	//sheet1
	public static final int BLACK_SELL = 1;
	public static final int GREY_SELL = 2;
	//public static final int MALGUN_GOTHIC = 3;
	public static final int TITLE = 3;
	public static final int SUB_TITLE = 4;
	public static final int ADDRESS = 5;
	public static final int TEXTSIZE_10 = 6;
	public static final int TABLE_TOTAL = 7;
	public static final int TABLE_CONTENT = 8;
	public static final int TABLE_CONTENT_AMOUNT = 9;
	public static final int REFERENCE = 10;
	public static final int TEXTSIZE_10_BOLD = 11;
	public static final int TEXTSIZE_10_WRAP = 12;
	//sheet2
	public static final int TABLE_HEADER_GREY_SELL = 13;
	//public static final int TABLE_CONTENT_SELL = 13;
	public static final int TABLE_CONTENT_BOLD = 14;
	
	/* ***************** Tax *********************** */
	public static final int TITLE_2 = 15;
	public static final int TEXTSIZE_12 = 16;
	public static final int TEXTSIZE_13 = 17;
	public static final int TEXTSIZE_13_RIGHT = 18;
	public static final int TEXTSIZE_13_CENTER = 19;
	public static final int TABLE_CONTENT_AMOUNT_RIGHT = 20;
	public static final int TEXTSIZE_13_BOLD = 21;
	public static final int TABLE_CONTENT_LEFT = 22;
	public static final int CONTENT_BORDER = 23;
	
	public static CellStyle getStyles(Workbook wb, int index)
	{
		CellStyle style = null;
		switch(index)
		{
		case BLACK_SELL :
			style = getBlackSell(wb);
			break;
		case GREY_SELL :
			style = getGreySell(wb);
			break;
		case TITLE :
			style = getTitle(wb);
			break;
		case SUB_TITLE :
			style = getSubTitle(wb);
			break;
		case ADDRESS :
			style = getAdress(wb);
			break;
		case TEXTSIZE_10 :
			style = getTextSize_10(wb);
			break;
		case TABLE_TOTAL :
			style = getTableTotal(wb);
			break;	
		case TABLE_CONTENT :
			style = getTableContent(wb);
			break;	
		case TABLE_CONTENT_AMOUNT :
			style = getTableContentAmount(wb);
			break;	
		case REFERENCE :
			style = getReference(wb);
			break;	
		case TEXTSIZE_10_BOLD :	
			style = getTextSize_10_Bold(wb);
			break;	
		case TEXTSIZE_10_WRAP :		
			style = getTextSize_10_Wrap(wb);
			break;
		case TABLE_HEADER_GREY_SELL :		
			style = getTableHeader_GreySell(wb);
			break;
		case TITLE_2 :		
			style = getTitle2(wb);
			break;
		case TEXTSIZE_12 :		
			style = getTextSize_12(wb);
			break;
		case TEXTSIZE_13 :		
			style = getTextSize_13(wb);
			break;
		case TEXTSIZE_13_RIGHT:		
			style = getTextSize_13_Right(wb);
			break;
		case TEXTSIZE_13_CENTER:		
			style = getTextSize_13_Center(wb);
			break;
		case TABLE_CONTENT_AMOUNT_RIGHT:		
			style = getTableContentAmount_Right(wb);
			break;
		case TEXTSIZE_13_BOLD :		
			style = getTextSize_13_Bold(wb);
			break;
		case TABLE_CONTENT_BOLD :		
			style = getTableContent_Bold(wb);
			break;
		case TABLE_CONTENT_LEFT :		
			style = getTableContent_Left(wb);
			break;
		case CONTENT_BORDER :		
			style = getContent_border(wb);
			break;
		}
		return style;
	}
	/*
	public static Font getFonts(Workbook wb, int index)
	{
		Font font = null;
		switch(index)
		{
		//case MALGUN_GOTHIC :
		//	font = getMalgunGothic(wb);
		//	break;
		}
		return font;
	}
	*/
	private static CellStyle getBlackSell(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		return style;
	}

	private static CellStyle getTableHeader_GreySell(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setWrapText(true);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}	
  
	private static CellStyle getContent_border(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		//style.setWrapText(true);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getGreySell(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setWrapText(true);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTitle(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)16);
		font.setFontName("맑은 고딕");
		//font.setBoldweight((short) 1);
		style.setFont(font);
		return style;
	}

	private static CellStyle getAdress(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)8);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}

	private static CellStyle getTextSize_10(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_10_Wrap(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_10_Bold(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getSubTitle(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTableTotal(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}
	
	private static CellStyle getTableContent(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}

	private static CellStyle getTableContent_Left(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}
	
	private static CellStyle getTableContentAmount(Workbook wb)
	{
		DataFormat format = wb.createDataFormat();

		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setDataFormat(format.getFormat("#,###.##"));
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}
	
	private static CellStyle getTableContentAmount_Right(Workbook wb)
	{
		DataFormat format = wb.createDataFormat();

		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setDataFormat(format.getFormat("#,###.##"));
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}
	
	private static CellStyle getTableContent_Bold(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("맑은 고딕");
		style.setFont(font);
		 return style;
	}
	
	private static CellStyle getReference(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("맑은 고딕");
		font.setItalic(true);
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTitle2(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)24);
		font.setFontName("맑은 고딕");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setUnderline(Font.U_SINGLE);
		//font.setBoldweight((short) 1);
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_12(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_13(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)13);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_13_Center(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)13);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	
	private static CellStyle getTextSize_13_Right(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)13);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}
	
	private static CellStyle getTextSize_13_Bold(Workbook wb)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)13);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setFontName("맑은 고딕");
		style.setFont(font);
		return style;
	}

	private static Font getMalgunGothic(Workbook wb)
	{
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		return font;
	}
	
}
