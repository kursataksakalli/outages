package com.krakenflex.outages.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * util for the date operations
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * returns date from string parameter
     * @param date
     * date in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z
     * @return
     * date instance
     */
    public static Date getDateFromString(String date){
        Date result = null;

        if(date == null) return null;

        try {
            result = dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("Date parse error:"+date, e);
        }

        return result;
    }
}
