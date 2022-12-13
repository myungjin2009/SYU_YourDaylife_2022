package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ui.Diary.DiaryAddActivity;
import com.example.ui.Diary.DiaryFragment;
import com.example.ui.Module.CustomEventBus;

import com.example.ui.Todo.TodoListDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    public static Context context_main;

    //날짜
    private DatePickerDialog dpd;
    public int date_y, date_m;

    //상단툴바
    public Toolbar toolbar;
    public TextView toolbar_date;
    private ImageView toolbar_add, toolbar_todo, toolbar_sync;

    //하단메뉴
    private HomeFragment homeFragment;
    private DiaryFragment diaryFragment;
    private SettingFragment settingFragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidThreeTen.init(this);
        super.onCreate(savedInstanceState);
        context_main = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면회전 강제 고정
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar_todo = findViewById(R.id.toolbar_todo);
        toolbar_add = findViewById(R.id.toolbar_add);
        toolbar_sync = findViewById(R.id.toolbar_sync);

        homeFragment = new HomeFragment();
        diaryFragment = new DiaryFragment();
        settingFragment = new SettingFragment();


        /*상단툴바*/
        //오른쪽 상단 날짜표시(년도,월) 기능
        toolbar_date = findViewById(R.id.toolbar_date);
        Calendar cal = new GregorianCalendar();
        date_y = cal.get(Calendar.YEAR);
        date_m = cal.get(Calendar.MONTH)+1;
        toolbar_date.setText(date_y + "." + date_m);
        //날짜수정 기능
        toolbar_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog spinnerDlg = new spinnerDialog(MainActivity.this);
                spinnerDlg.show();
            }
        });


        //오른쪽 상단 체크(TodoList) 버튼
        toolbar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoListDialog todoListDialog = new TodoListDialog(MainActivity.this);
                todoListDialog.show();
            }
        });
        //오른쪽 상단 새로고침(Sync) 버튼 (Home)
        toolbar_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HomeFragment로 CustomEventBus 이벤트 전달
                EventBus.getDefault().post(new CustomEventBus.CustomOnClickEvent("toolbar_sync"));
            }
        });
        //오른쪽 상단 추가하기(Plus) 버튼 (Diary)
        toolbar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DiaryAddActivity.class);
                startActivity(myIntent);
            }
        });


        //앱 구동시 첫 화면으로 HomeFragment 띄움
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();


        /*하단메뉴*/
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

    }
}
