package com.krakenflex.outages.unittest.util;

import com.krakenflex.outages.util.DateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @Test
    @DisplayName("getDateFromStringTest method test")
    public void getDateFromStringTest(){
        Date date = DateUtil.getDateFromString("2022-01-01T00:00:00.000Z");
        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        assertEquals(2022,calendar.get(Calendar.YEAR));
        assertEquals(0,calendar.get(Calendar.MONTH));
        assertEquals(1,calendar.get(Calendar.DATE));

        assertEquals(0,calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0,calendar.get(Calendar.MINUTE));
        assertEquals(0,calendar.get(Calendar.SECOND));
        assertEquals(0,calendar.get(Calendar.MILLISECOND));
    }
}
