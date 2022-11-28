package com.example.ui.Module;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CustomTime {
    static LocalDate localDate = LocalDate.now();

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
}
