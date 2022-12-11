package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.Module.CustomNoticeCrawlling;
import com.example.ui.Module.CustomScheduleCrawlling;
import com.example.ui.Notice.NoticeRecyclerAdapter;
import com.example.ui.Schedule.ScheduleRecyclerAdapter;
import com.example.ui.Todo.TodoListActivity;
import com.example.ui.Todo.VariableSet;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView toolbar_todo, toolbar_sync;
    private TextView textScheduleShowAll,textNoticeShowAll;
    private RecyclerView scheduleRecyclerView, noticeRecyclerView;

    //하단메뉴
    private HomeFragment homeFragment;
    private DiaryFragment diaryFragment;
    private SettingFragment settingFragment;
    //다이얼로그
    private ImageView toolbar_add;
    //상단툴바
    private TextView toolbar_date;
    //날짜
    private DatePickerDialog dpd;
    public int date_y, date_m;

    public static Context context_main;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidThreeTen.init(this);
        super.onCreate(savedInstanceState);
        context_main = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면 고정
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navi_view);
        toolbar_todo = findViewById(R.id.toolbar_todo);
        toolbar_sync = findViewById(R.id.toolbar_sync);
        textScheduleShowAll = findViewById(R.id.text_schedule_show_all);
        textNoticeShowAll = findViewById(R.id.text_notice_show_all);

        scheduleRecyclerView = findViewById(R.id.schedule_recycler_view);
        noticeRecyclerView = findViewById(R.id.notice_recycler_view);


        /*상단툴바*/
        //날짜표시 기능구현
        toolbar_date = findViewById(R.id.toolbar_date);
        Calendar cal = new GregorianCalendar();
        date_y = cal.get(Calendar.YEAR);
        date_m = cal.get(Calendar.MONTH)+1;
        toolbar_date.setText(date_y + "." + date_m);



        //올해 주요 학사일정(Schedule Data) DB >> RecyclerView 불러오기
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScheduleRecyclerAdapter sra = new ScheduleRecyclerAdapter(this);
        scheduleRecyclerView.setAdapter(sra);



        //전체 공지사항 Web Crawlling >> RecyclerView 불러오기
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomNoticeCrawlling customNoticeCrawlling = new CustomNoticeCrawlling(MainActivity.this);
        customNoticeCrawlling.getCrawlling();
        customNoticeCrawlling.returnDataList.observe(MainActivity.this, new Observer<List<CustomNoticeCrawlling.DataList>>() {
            @Override
            public void onChanged(List<CustomNoticeCrawlling.DataList> dataLists) {
                NoticeRecyclerAdapter nra = new NoticeRecyclerAdapter(MainActivity.this, dataLists);
                noticeRecyclerView.setAdapter(nra);
            }
        });


        //오른쪽 상단 체크(TodoList) 버튼
        toolbar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoListActivity todoListActivity = new TodoListActivity(MainActivity.this);
                todoListActivity.show();
            }
        });

        //오른쪽 상단 새로고침(Sync) 버튼
        toolbar_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomScheduleCrawlling customCrawlling = new CustomScheduleCrawlling(MainActivity.this);
                customCrawlling.getCrawlling();

                customCrawlling.liveScheduleData.observe(MainActivity.this, new Observer<List<ScheduleData>>() {
                    @Override
                    public void onChanged(List<ScheduleData> scheduleData) {
                        sra.updateSchedule();
                    }
                });
            }
        });


        //학사일정 '전체보기' 텍스트 클릭시
        textScheduleShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VariableSet.getSyuScheduleUri()));
                startActivity(intent);
            }
        });

        //공지사항 '전체보기' 텍스트 클릭시
        textNoticeShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VariableSet.getSyuNoticeUri()));
                startActivity(intent);
            }
        });
    }
}
