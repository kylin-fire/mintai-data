package com.mintai.data.common.helper;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class CommonHelper {

    private static Gson gson = new Gson();
    ;

    public static String formatDate(Date date, String pattern) {
        if (null == date || pattern == null || pattern.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();

            formatter.applyPattern(pattern);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate(String date, String format) {
        if (date == null || date.isEmpty() || format == null || format.isEmpty()) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
