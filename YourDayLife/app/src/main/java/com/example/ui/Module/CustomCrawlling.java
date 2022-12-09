package com.example.ui.Module;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.RoomDB;
import com.example.ui.MainActivity;
import com.example.ui.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//안성민 - 크롤링 모듈
public class CustomCrawlling {
    private Context context;
    public CustomCrawlling(Context context) {
        this.context = context;
    }

    private String url = "https://www.syu.ac.kr/academic/major-schedule/"; //학사일정
    private RoomDB database;
    private List<ScheduleData> scheduleData;
    public MutableLiveData<List<ScheduleData>> liveScheduleData = new MutableLiveData<>();
    private Bundle bundle = new Bundle();


    //삼육대학교 홈페이지(syu.ac.kr)에서 학사 일정 (1년) 크롤링 해서 DB에 저장하기
    public void getCrawlling() {
        Toast.makeText(context, "학사일정을 불러오는 중입니다..", Toast.LENGTH_SHORT).show();
        database = RoomDB.getInstance(context);
        scheduleData = database.scheduleDao().getAll();

        new Thread(){
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    //2022.03~ 2023.02까지 학사일정
                    for(int k= 0; k<doc.select(".md_textcalendar").size(); k++) {
                        Elements cal = doc.select(".md_textcalendar").eq(k);
                        //년도
                        Elements Year = cal.select(".year");
                        //날짜
                        Elements Month = cal.select("ul dt");
                        //일정
                        Elements Scl = cal.select("ul dd");

                        database.scheduleDao().reset(scheduleData);

                        for (int i = 0; i < Month.size(); i++) {
                            Log.d("년도", Year.select(".year").text());
                            Log.d("월", Month.select("dt").eq(i).text());
                            Log.d("일정 내용",  Scl.select("dd").eq(i).text());


                            ScheduleData scheduleData = new ScheduleData();
                            scheduleData.setYear(Year.select(".year").text());
                            scheduleData.setDate(Month.select("dt").eq(i).text());
                            scheduleData.setContent(Scl.select("dd").eq(i).text());
                            database.scheduleDao().insert(scheduleData);
                        }
                    }
                    liveScheduleData.postValue(scheduleData);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
