package com.example.ui.Module;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CustomTime {
    static LocalDate localDate = LocalDate.now();
    static long mNow = System.currentTimeMillis();

    //오늘 날짜를 객체로 반환
    public static LocalDate getToday() {
//        System.out.println("localDateTime : "+localDateTime.toString());
//        System.out.println("getYear : "+localDateTime.getYear());
//        System.out.println("getMonth : "+localDateTime.getMonth());
//        System.out.println("getDayOfMonth : "+localDateTime.getDayOfMonth());
//        System.out.println("getMonthValue : "+localDateTime.getMonthValue());
        return localDate;
    }

    //오늘 날짜를 문자열 "yyyy-MM-dd" 로 반환
    public static String getTodayToString() {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    //오늘 날짜를 숫자형 리스트로 반환
    public static List<Integer> getTodayToList() {
        List<Integer> ListToday = new ArrayList<>();
        ListToday.add(0,localDate.getYear());
        ListToday.add(1, localDate.getMonthValue() - 1);
        ListToday.add(2, localDate.getDayOfMonth());
        return ListToday;
    }

    //"yyyy-MM-dd"로 이로어진 문자열을 List<Integer> 객체로 반환
    public static List<Integer> stringToLocalDate(String string) {
        LocalDate date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
        List<Integer> ListDate = new ArrayList<>();
        ListDate.add(0,localDate.getYear());
        ListDate.add(1, localDate.getMonthValue() - 1);
        ListDate.add(2, localDate.getDayOfMonth());
        return ListDate;
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
