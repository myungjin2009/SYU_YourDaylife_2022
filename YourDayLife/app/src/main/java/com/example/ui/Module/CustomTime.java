package com.example.ui.Module;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CustomTime {
    static LocalDate localDate = LocalDate.now();
    static long mNow = System.currentTimeMillis();

    //현재 시각을 객체로 반환
    public static LocalDate getToday() {
//        System.out.println("localDateTime : "+localDateTime.toString());
//        System.out.println("getYear : "+localDateTime.getYear());
//        System.out.println("getMonth : "+localDateTime.getMonth());
//        System.out.println("getDayOfMonth : "+localDateTime.getDayOfMonth());
//        System.out.println("getMonthValue : "+localDateTime.getMonthValue());
        return localDate;
    }

    //문자열로된 시각을 객체로 반환
    public static LocalDateTime stringToLocalDateTime(String string) {
        return null;
    }


    //현재 '월(月,month)'을 반환
    public static String getMonth() {
        Date mDate = new Date(mNow);
        SimpleDateFormat mformat = new SimpleDateFormat("MM");
        String getTime = mformat.format(mDate);
        return getTime;
    }


    //月,日 한자릿수 >> 두자리수로 변환 (4월 5일 >> 04월 05일)
    public static List<String> convertZeroDigit(int month, int dayOfMonth) {
        List<String> monthAndDay = new ArrayList<>();
        monthAndDay.add(0,((month+1) >= 1 && (month+1) < 10) ?
                "0"+(month+1) :
                String.valueOf(month+1));
        monthAndDay.add(1,(dayOfMonth >= 1 && dayOfMonth < 10) ?
                "0"+dayOfMonth :
                String.valueOf(dayOfMonth));
        return monthAndDay;
    }
}
