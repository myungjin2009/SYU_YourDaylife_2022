package com.example.ui.Module;


import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Todo.VariableSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

//안성민 - 학사일정 크롤링 모듈
public class CustomScheduleCrawlling {
    private Context context;
    public CustomScheduleCrawlling(Context context) {
        this.context = context;
    }

    private String url = VariableSet.getSyuScheduleUri(); //학사일정
    private RoomDB database;
    private List<ScheduleData> scheduleData;
    public MutableLiveData<List<ScheduleData>> liveScheduleData = new MutableLiveData<>();


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
                            //"년도" Year.select(".year").text()
                            //"월" Month.select("dt").eq(i).text()
                            //"일정 내용" Scl.select("dd").eq(i).text()
                            ScheduleData scheduleData = new ScheduleData();
                            scheduleData.setYear(Year.select(".year").text());
                            String[] dateSplit = (Month.select("dt").eq(i).text()).split(" ~ ", 2);
                            scheduleData.setStartDate(dateSplit[0]);
                            //종료일이 없을 경우의 예외처리
                            try { scheduleData.setEndDate(dateSplit[1]); }
                            catch (Exception e) { scheduleData.setEndDate(dateSplit[0]); }
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
