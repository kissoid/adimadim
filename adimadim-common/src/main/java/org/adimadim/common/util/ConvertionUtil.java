/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.common.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Adem
 */
public class ConvertionUtil implements Serializable {

    public static int dayDifference(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }
    
    public static Date stringToDate(String date, String dateFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.parse(date);
    }

    public static Double timeToMinute(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        Double time = (double) hour * 60;
        time += (double) minute;
        time += (double) second / 60;
        return time;
    }

    public static long yearDifference(Date firstDate, Date lastDate) {
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(firstDate);
        Calendar secondCal = Calendar.getInstance();
        secondCal.setTime(lastDate);
        long firstMilliSecond = firstCal.getTimeInMillis();
        long secondMilliSecond = secondCal.getTimeInMillis();
        long differMilliSecond = Math.abs(firstMilliSecond - secondMilliSecond);
        long differSecond = differMilliSecond / 1000;
        long differMinutes = differSecond / 60;
        long differHours = differMinutes / 60;
        long differDays = differHours / 24;
        long differMonth = differDays / 30;
        long differYear = differMonth / 12;
        return differYear;
    }
    
    public static String firstCharUpperCase(String str) {
        while (str.indexOf("  ") > 0) {
            str = str.replace("  ", " ");
        }
        String[] words = str.split(" ");
        String sentence = "";
        for (String word : words) {
            char[] chars = word.toCharArray();
            for(int j = 0; j < chars.length; j++){
                if( j == 0){
                    chars[j] = charToUpper(chars[j]);
                } else {
                    chars[j] = charToLower(chars[j]);
                }
            }
            if (!sentence.equals("")) {
                sentence += " ";
            }
            sentence += new String(chars);
        }

        return sentence;
    }

    private static char charToUpper(char c) {
        if (c == 'i') {
            c = 'İ';
        } else if (c == 'ı') {
            c = 'I';
        } else {
            c = Character.toUpperCase(c);
        }
        return c;
    }
    
    private static char charToLower(char c) {
        if (c == 'İ') {
            c = 'i';
        } else if (c == 'I') {
            c = 'ı';
        } else {
            c = Character.toLowerCase(c);
        }
        return c;
    }
}
