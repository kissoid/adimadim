/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ergo.insyst.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * @author Mehmet Adem Sengul
 */
public class DateUtil {

	public static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
	public static final String UK_DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
	public static final int DAY_COUNT_OF_CURRENT_YEAR = isLeapYear(systemDate()) ? 366 : 365;
	public static final int DEFAULT_DAY_COUNT_OF_YEAR = 365;
	public static final long DEFAULT_DAY_COUNT_OF_YEAR_IN_MILIS = 365 * 24 * 60 * 60 * 1000L;
	public static final long DAY_IN_MILIS = 24 * 60 * 60 * 1000L;
	public static final Locale DEFAULT_LOCALE = new Locale("tr_TR");

	public static int retrieveYearOfDate(Date date) {
		if(date == null)  {
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	public static String retrieveLast2DigitYearOfDate(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return String.valueOf(cal.get(Calendar.YEAR)).substring(2);
	}

	public static Date stringToDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		return simpleDateFormat.parse(date);
	}

	public static Date stringToDatePattern(String date, String pattern, Locale locale) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
		return simpleDateFormat.parse(date);
	}

	public static String dateToString(Date date) throws ParseException {
		return dateToString(date, DEFAULT_DATE_PATTERN);
	}

	public static Date sqlDateToUtilDate(java.sql.Date date) {
		return new Date(date.getTime());
	}

	public static Date utilDateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static int dayDifferenceAbsolute(Date startDate, Date endDate) {
		return (int) Math.abs(dayDifferenceOfDates(startDate, endDate));
	}

	public static boolean isDateEqual(Date startDate, Date endDate) {
		return (dayDifferenceOfDates(startDate, endDate) == 0);
	}

	public static boolean isDateAfter(Date startDate, Date endDate) {
		return (dayDifferenceOfDates(startDate, endDate) > 0);
	}

	public static boolean isDateBefore(Date startDate, Date endDate) {
		return (dayDifferenceOfDates(startDate, endDate) < 0);
	}

	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static int dayDifferenceOfDates(Date startDate, Date endDate) {	
		if(startDate == null || endDate == null)
			return -1;
		try {
			//saat farkından dolayı gün sayısını bazen -1 azaltabiliyor. Zamansız olarak fark alması daha doğru olur.
			return (int) TimeUnit.DAYS.convert((getDateWithFirstTime(endDate).getTime() - getDateWithFirstTime(startDate).getTime()), TimeUnit.MILLISECONDS);
		} catch (Exception ex) {
			Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public static Date addDaysToDate(Date date, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, count);
		return cal.getTime();
	}

	public static Date addMonthsToDate(Date date, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, count);
		return cal.getTime();
	}

	public static Date addYearsToDate(Date date, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, count);
		return cal.getTime();
	}

	public static boolean isLeapYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String str = sdf.format(date);
		int year = Integer.parseInt(str);
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}

	public static Date formatDateToPattern(Date date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = sdf.format(date);
		return sdf.parse(dateStr);
	}

	public static Date getDateWithFirstTime(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDateWithLastTime(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
		} catch (DatatypeConfigurationException ex) {
			Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return xmlCalendar;
	}

	/**
	 * 
	 * @param calendar
	 * @return {@link Date}
	 */
	public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static Date systemDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date XMLGregorianCalendarToDate(XMLGregorianCalendar xmlDate) {
		return xmlDate.toGregorianCalendar().getTime();
	}

	public static int monthDifference(Date startDate, Date endDate) {
		Calendar startCal = new GregorianCalendar();
		startCal.setTime(startDate);
		Calendar endCal = new GregorianCalendar();
		endCal.setTime(endDate);
		int diffYear = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
		return diffMonth;
	}
	
	public static boolean isDateBefore(Date startDate,String endDate) throws ParseException {
		Date endDate2 = null;
		try {
			endDate2 = "".equals(endDate) ? null : new SimpleDateFormat(UK_DATE_PATTERN, Locale.UK).parse(endDate);	         
	        return getDateWithFirstTime(endDate2).before(getDateWithFirstTime(startDate));
        } catch (ParseException e) {
        	endDate2 = stringToDate(endDate);
        	return getDateWithFirstTime(endDate2).before(getDateWithFirstTime(startDate));
        }
	}
	
	public static boolean isDateAfter(Date startDate,String endDate) throws ParseException {
		Date endDate2 = null;
		try {
			endDate2 = "".equals(endDate) ? null : new SimpleDateFormat(UK_DATE_PATTERN, Locale.UK).parse(endDate);
	         
	        return getDateWithFirstTime(endDate2).after(getDateWithFirstTime(startDate));
        } catch (ParseException e) {
        	endDate2 = stringToDate(endDate);
        	return getDateWithFirstTime(endDate2).after(getDateWithFirstTime(startDate));
        }
	}
	
	public static List<String> getMonths() {
	        List<String> months = new ArrayList<String>();
	        months.add("01");
	        months.add("02");
	        months.add("03");
	        months.add("04");
	        months.add("05");
	        months.add("06");
	        months.add("07");
	        months.add("08");
	        months.add("09");
	        months.add("10");
	        months.add("11");
	        months.add("12");
	        return months;
	}

	public static List<String> getYears2Digit() {
	        List<String> years = new ArrayList<String>();
	        String currentYear = DateUtil.retrieveLast2DigitYearOfDate(new Date());
	        for (int i = 0; i < 50; i++) {
	            years.add(String.valueOf(Integer.parseInt(currentYear) + i));
	        }
	        return years;
	}

	public static List<String> getYears4Digit() {
		List<String> years = new ArrayList<String>();
		String currentYear = String.valueOf(DateUtil.retrieveYearOfDate(new Date()));
		for (int i = 0; i < 50; i++) {
			years.add(String.valueOf(Integer.parseInt(currentYear) + i));
		}
		return years;
	}
	
	public static Date retrieveFirstDayOfJuneForCurrentYear() {		
		try {
	        return DateUtil.stringToDate("01/06/"+retrieveYearOfDate(new Date())+"");
        } catch (ParseException e) {
	        
        }
		return null;
	}
	
	public static long timeDiffferenceInMilis(Date startDate, Date endDate) throws ParseException {
		if (startDate == null || endDate == null)
			return -1;
		return (endDate.getTime() - startDate.getTime());
	}
	/**
	 * 
	 * @param startDate
	 * @param wantedWorkDays : eklenecek iş günü sayısı
	 * @return verilen tarihe istenen iş günü ekler ve ona göre tarih hesaplar.
	 */
	public static Date calculateEndDateByWorkDays(Date startDate, int wantedWorkDays) {		
	  Calendar startCal = Calendar.getInstance();	 
	  startCal.setTime(startDate);			
	  for (int i = 0; i <= wantedWorkDays; i++) {
	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
	    {
	      startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }
	  }
	 
	  return startCal.getTime();
	}
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return iki tarih arasındaki iş günü sayısını verir.
	 */
	public static int calculateWorkDaysBetweenDates(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}
		do {
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				workDays++;
			}
		} while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

		return workDays;
	}
	
}
