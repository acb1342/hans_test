package com.hans.sses.common.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : TimeUtilz.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2013 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2013. 7. 15.      최초 버전
 * =================================================================================
 */
class DateFormats {
	final SimpleDateFormat formatter_stat = new SimpleDateFormat("yyyy/MM/dd");
	final SimpleDateFormat formatter_stat2 = new SimpleDateFormat("yyyy-MM-dd");
	final SimpleDateFormat formatter_rollover = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");
	final SimpleDateFormat formatter_standard = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss z");
	final SimpleDateFormat formatter_simple = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	final SimpleDateFormat fmt14 = new SimpleDateFormat("yyyyMMddHHmmss");
	final SimpleDateFormat fmt12 = new SimpleDateFormat("yyyyMMddHHmm");
	final SimpleDateFormat fmt10 = new SimpleDateFormat("yyyyMMddHH");
	final SimpleDateFormat fmt8 = new SimpleDateFormat("yyyyMMdd");
	final SimpleDateFormat fmt8EN = new SimpleDateFormat("dd/MM/yyyy");
	final SimpleDateFormat fmt8AM = new SimpleDateFormat("MM/dd/yyyy");
	final SimpleDateFormat fmt17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
}

public class TimeUtilz {
	/** Format pattern HTTP = "EEE, dd MMM yyyy HH:mm:ss 'GMT'" */
	public static final int HTTP = 1;

	/** Format pattern XLF = "yyyy/MMM/dd HH:mm:ss" */
	public static final int XLF = 2;

	/** Format pattern CLF = "dd/MMM/yyyy:HH:mm:ss" */
	public static final int CLF = 3;

	/** Format pattern ROLLOVER = "yyyyMMdd_HHmmss" */
	public static final int ROLLOVER = 4;

	/** Format pattern STANDARD = "yyyy/MM/dd HH:mm:ss z" */
	public static final int STANDARD = 5;

	/** Format pattern SIMPLE = "yyyy/MM/dd HH:mm:ss" */
	public static final int SIMPLE = 6;

	/** Format pattern FMT8 = "yyyyMMdd" */
	public static final int FMT8 = 10;

	/** Format pattern FMT8EN = "dd/MM/yy" */
	public static final int FMT8EN = 11;

	/** Format pattern FMT8AM = "MM/dd/yy" */
	public static final int FMT8AM = 12;

	private final Calendar calendar;

	private final NumberFormat numberFormat;

	private final Date m_date;

	@SuppressWarnings("unused")
	private int m_format; // format style

	private static long millisPerHour = 0x36ee80L;

	private static long millisPerMinute = 60000L;

	private static ThreadLocal<DateFormats> formats = new ThreadLocal<DateFormats>();

	public TimeUtilz(long date) {
		this.calendar = Calendar.getInstance();
		this.numberFormat = NumberFormat.getInstance();
		this.m_date = new Date(date);
		this.m_format = STANDARD;
	}

	public TimeUtilz(long date, int format) {
		this(date);
		this.setDefaultFormat(format);
	}

	public void setDefaultFormat(int format) {
		this.m_format = format;
	}

	public String getZoneOffset() {
		StringBuffer sb = new StringBuffer();

		int i = this.calendar.get(Calendar.ZONE_OFFSET)
				+ this.calendar.get(Calendar.DST_OFFSET);

		if (i < 0) {
			sb.append("-");
			i = -i;
		} else {
			sb.append("+");
		}

		sb.append(this.zeroPad((int) (i / millisPerHour), 2, 2));
		sb.append(this.zeroPad((int) ((i % millisPerHour) / millisPerMinute), 2, 2));
		return sb.toString();
	}

	private String zeroPad(long l, int minint, int maxint) {
		this.numberFormat.setMinimumIntegerDigits(minint);
		this.numberFormat.setMaximumIntegerDigits(maxint);
		return this.numberFormat.format(l);
	}

	protected long parse(String str) throws ParseException {
		SimpleDateFormat sdf = null;
		Date date = null;

		try {
			sdf = new SimpleDateFormat("E, dd MMM yyy HH:mm:ss 'GMT'",
					Locale.US);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			date = sdf.parse(str);

			return date.getTime();
		} catch (ParseException ex) {
		}

		try {
			sdf = new SimpleDateFormat("E, dd-MMM-yy HH:mm:ss 'GMT'", Locale.US);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			date = sdf.parse(str);

			return date.getTime();
		} catch (ParseException ex) {
			sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy", Locale.US);
		}

		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		date = sdf.parse(str);

		return date.getTime();
	}

	public static Date parse(String dateString, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}

	public String formatForRollover() {
		return getDateFormats().formatter_rollover.format(this.m_date);
	}

	public String formatForStandard() {
		return getDateFormats().formatter_standard.format(this.m_date);
	}

	public String formatForSimple() {
		return getDateFormats().formatter_simple.format(this.m_date);
	}

	private static DateFormats getDateFormats() {
		DateFormats ret;
		ret = formats.get();
		if (ret == null) {
			ret = new DateFormats();
			formats.set(ret);
		}
		return ret;
	}

	public static String getCurrentTimeAs14Format() {
		return getCurrentTimeAs14Format(0);
	}

	public static String getCurrentTimeAs14Format(int dayDuration) {
		long tick = System.currentTimeMillis();
		return getCurrentTimeAs14Format(dayDuration, tick);
	}

	public static String getCurrentTimeAs14Format(int dayDuration, long tick) {
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt14.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs12Format() {
		return getCurrentTimeAs12Format(0);
	}

	public static String getCurrentTimeAs12Format(int dayDuration) {
		long tick = System.currentTimeMillis();
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt12.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs12FormatByMin(int minDuration) {
		long tick = System.currentTimeMillis();
		tick += (long) minDuration * 60 * 1000;
		return getDateFormats().fmt12.format(new java.util.Date(tick));
	}

	public static String get14StrFormatFrom14FormatByMin(String time,
			int minDuration) {
		long tick = getTickFrom14StrFormat(time);
		tick += (long) minDuration * 60 * 1000;
		return getDateFormats().fmt14.format(new java.util.Date(tick));
	}

	public static String get14StrFormatFrom14FormatByHour(String time,
			int hourDuration) {
		long tick = getTickFrom14StrFormat(time);
		tick += (long) hourDuration * 60 * 60 * 1000;
		return getDateFormats().fmt14.format(new java.util.Date(tick));
	}

	public static String get14StrFormatFrom14FormatBfromDay(String time,
			int dayDuration) {
		long tick = getTickFrom14StrFormat(time);
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt14.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs10Format() {
		return getCurrentTimeAs10Format(0);
	}

	public static String getCurrentTimeAs10Format(int dayDuration) {
		long tick = System.currentTimeMillis();
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt10.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs10FormatByHour(int hourDuration) {
		long tick = System.currentTimeMillis();
		tick += (long) hourDuration * 60 * 60 * 1000;
		return getDateFormats().fmt10.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs17Format() {
		long tick = System.currentTimeMillis();
		return getDateFormats().fmt17.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs8Format() {
		return getCurrentTimeAs8Format(0);
	}

	public static String getCurrentTimeAs8StatFormat() {
		return getDateFormats().formatter_stat.format(new java.util.Date(System
				.currentTimeMillis()));
	}

	public static String getCurrentTimeAs8StatFormat1() {
		return getDateFormats().fmt8.format(new java.util.Date(System
				.currentTimeMillis()));
	}

	public static String getCurrentTimeAs8StatFormat2() {
		return getDateFormats().formatter_stat2.format(new java.util.Date(
				System.currentTimeMillis()));
	}

	public static String getCurrentTimeAs8Format(int dayDuration) {
		long tick = System.currentTimeMillis();
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt8.format(new java.util.Date(tick));
	}

	public static String getCurrentTimeAs8Format(int dayDuration, long tick) {
		tick += (long) dayDuration * 24 * 60 * 60 * 1000;
		return getDateFormats().fmt8.format(new java.util.Date(tick));
	}

	public static String getTimeAs14Format(java.util.Date date, long dayDuration) {
		if (date == null) {
			return null;
		}
		String strDate = getDateFormats().fmt14.format(date);
		long retDate = Long.parseLong(strDate) + (dayDuration * 1000000);
		return Long.toString(retDate);
	}

	public static String getTimeAs14Format(java.util.Date date) {
		if (date == null) {
			return null;
		}

		return getDateFormats().fmt14.format(date);
	}

	public static String getTimeAs8Format(java.util.Date date) {
		if (date == null) {
			return null;
		}

		return getTimeAs8Format(date, FMT8);
	}

	public static String getTimeAs8Format(int fmt) {
		long tick = System.currentTimeMillis();
		return getTimeAs8Format(new java.util.Date(tick), fmt);
	}

	public static String getTimeAs8Format(java.util.Date date, int fmt) {
		if (date == null) {
			return null;
		}

		if (fmt == FMT8) {
			return getDateFormats().fmt8.format(date);
		} else if (fmt == FMT8EN) {
			return getDateFormats().fmt8EN.format(date);
		} else if (fmt == FMT8AM) {
			return getDateFormats().fmt8AM.format(date);
		} else {
			return null;
		}
	}

	public static Date toDateAs14Format(String str) {
		if (str == null || str.length() != 14) {
			return null;
		}

		try {
			return new Date(getDateFormats().fmt14.parse(str).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date toDateAs8Format(String str) {
		if (str == null || str.length() != 8) {
			return null;
		}

		try {
			return new Date(getDateFormats().fmt8.parse(str).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static long getTickFrom14StrFormat(String time) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(getDateFormats().fmt14.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}

	public static long getTickFrom8StrFormat(String time) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(getDateFormats().fmt8.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}

	public static String get14StrFormatFromTick(long tick) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return getDateFormats().fmt14.format(c.getTime());
	}

	public static String get8StrFormatFromTick(long tick) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return getDateFormats().fmt8.format(c.getTime());
	}

	public static int getDayOfWeek() {
		long tick = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDayOfMonth() {
		long tick = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfYear() {
		long tick = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return c.get(Calendar.DAY_OF_YEAR);
	}

	public static long getTickFromCurrentYearMonthDayHour() {
		long tick = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}

	public static int getMonth(long tick) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tick);

		return c.get(Calendar.MONTH);
	}

	public static String getFirstDateOfMonthAs8StatFormat() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);

		return getDateFormats().formatter_stat.format(c.getTime());
	}

	public static String getFirstDateOfMonthAs8StatFormat1() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);

		return getDateFormats().fmt8.format(c.getTime());
	}

	public static String getFirstDateOfMonthAs8StatFormat2() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);

		return getDateFormats().formatter_stat2.format(c.getTime());
	}

	public static String getPreviousDay(String day) {
		return getPreviousDay(getTickFrom8StrFormat(day));
	}

	public static String getNextDay(String day) {
		return getNextDay(getTickFrom8StrFormat(day));
	}

	public static String getPreviousDay(long tick) {
		return get8StrFormatFromTick(tick - (24 * (60 * (60 * 1000))));
	}

	public static String getNextDay(long tick) {
		return get8StrFormatFromTick(tick + (24 * (60 * (60 * 1000))));
	}

	public static String getPreviousMonth(int monthDuration) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, monthDuration);
		return getTimeAs8Format(c.getTime());
	}

	public static Date getDate(Date given, int amount) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(given);
		calendar.add(Calendar.DATE, amount);

		return calendar.getTime();
	}

	public static long getDiffDay(Date from, Date to) {
		Calendar fromDay = Calendar.getInstance();
		Calendar toDay = Calendar.getInstance();

		fromDay.setTime(from);
		toDay.setTime(to);

		// 두 날짜간의 차이를 얻으려면, getTimeInMillis()를 이용해서 천분의 일초 단위로 변환해야한다.
		// 1일 = 24 * 60 * 60
		long diffSec = (toDay.getTimeInMillis() - fromDay.getTimeInMillis()) / 1000; // 초
		long diffDay = diffSec / (60 * 60 * 24);

		return diffDay;
	}

	public static void main(String[] args) throws ParseException {
		Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2015-03-13");
		Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2015-03-20");
		System.out.println("from:" + from);
		System.out.println("to:" + to);
		long diffDay = getDiffDay(from, to);
		System.out.println("두 날자의 일 차이수 = " + diffDay);

		System.out.println(System.currentTimeMillis());
		System.out.println(getCurrentTimeAs8StatFormat());
		System.out.println(getFirstDateOfMonthAs8StatFormat());
		System.out.println(get14StrFormatFromTick(-347184000000L));
		System.out
				.println(get14StrFormatFromTick(getTickFromCurrentYearMonthDayHour()));
	}
}
