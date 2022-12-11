package com.example.ui_bottom_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    /*선언*/
    //하단메뉴
    HomeFragment homeFragment;
    DiaryFragment diaryFragment;
    SettingFragment settingFragment;
    //다이얼로그
    ImageView toolbar_add, toolbar_sync;
    //상단툴바
    ImageView toolbar_todo;
    TextView toolbar_date;
    //날짜
    DatePickerDialog dpd;
    public int date_y, date_m;

    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면 고정

        /*상단툴바*/
        //날짜표시 기능구현
        toolbar_date = findViewById(R.id.toolbar_date);
        Calendar cal = new GregorianCalendar();
        date_y = cal.get(Calendar.YEAR);
        date_m = cal.get(Calendar.MONTH)+1;
        toolbar_date.setText(date_y + "." + date_m);
        //날짜수정 기능구현
        toolbar_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog spinnerDlg = new spinnerDialog(MainActivity.this);
                spinnerDlg.show();
            }
        });

        /*하단 네비게이션 메뉴*/
        homeFragment = new HomeFragment();
        diaryFragment = new DiaryFragment();
        settingFragment = new SettingFragment();
        //activity_main.xml의 Framelayout에 올리는 방식
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        //하단메뉴 기능구현
        NavigationBarView navigationbarView = findViewById(R.id.bottom_menu);
        navigationbarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.diary_bottom_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diaryFragment).commit();
                        toolbar_todo.setVisibility(View.VISIBLE);
                        toolbar_sync.setVisibility(View.INVISIBLE);
                        toolbar_add.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.home_bottom_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        toolbar_todo.setVisibility(View.VISIBLE);
                        toolbar_sync.setVisibility(View.VISIBLE);
                        toolbar_add.setVisibility(View.INVISIBLE);
                        return true;
                    case R.id.setting_bottom_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        toolbar_todo.setVisibility(View.INVISIBLE);
                        toolbar_sync.setVisibility(View.INVISIBLE);
                        toolbar_add.setVisibility(View.INVISIBLE);
                        return true;
                }
                return false;
            }
        });

        /*다이얼로그*/
        toolbar_todo = findViewById(R.id.toolbar_todo);
        toolbar_add = findViewById(R.id.toolbar_add);
        toolbar_sync = findViewById(R.id.toolbar_sync);
        //투두리스트 기능구현
        toolbar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoDialog todoDlg = new todoDialog(MainActivity.this);
                todoDlg.show();
            }
        });
        //싱크 기능구현
        toolbar_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //싱크버튼 눌를때 채울것 채우기
            }
        });
        //일기 기능구현
        toolbar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryDialog diaryDlg = new diaryDialog(MainActivity.this);
                diaryDlg.show();
            }
        });
    }
}