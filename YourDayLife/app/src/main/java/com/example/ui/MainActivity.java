package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.Module.CustomCrawlling;
import com.example.ui.Schedule.ScheduleRecyclerAdapter;
import com.example.ui.Todo.TodoListActivity;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView toolbar_menu, toolbar_todo, toolbar_sync;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidThreeTen.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navi_view);
        drawerLayout = findViewById(R.id.drawer);
        drawerLayout.setDrawerLockMode(drawerLayout.LOCK_MODE_LOCKED_CLOSED); //드로어 swipe 금지
        toolbar_menu = findViewById(R.id.toolbar_menu);
        toolbar_todo = findViewById(R.id.toolbar_todo);
        toolbar_sync = findViewById(R.id.toolbar_sync);

        //올해 주요 학사일정(Schedule Data) DB >> RecyclerView 불러오기
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScheduleRecyclerAdapter sra = new ScheduleRecyclerAdapter(this);
        recyclerView.setAdapter(sra);

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
                CustomCrawlling customCrawlling = new CustomCrawlling(MainActivity.this);
                customCrawlling.getCrawlling();

                customCrawlling.liveScheduleData.observe(MainActivity.this, new Observer<List<ScheduleData>>() {
                    @Override
                    public void onChanged(List<ScheduleData> scheduleData) {
                        sra.updateSchedule();
                    }
                });
            }
        });


        //왼쪽 상단 드로어 메뉴(삼지창)버튼 눌렀을때
        toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //드로어 메뉴(삼지창) 옵션
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.menu_uniCal:
                        Intent intent_main = new Intent(MainActivity.this, CrawllingTestActivity.class);
                        startActivity(intent_main);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_diary:
                        Intent intent_diary = new Intent(MainActivity.this, DiaryActivity.class);
                        startActivity(intent_diary);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_setting:
                        Intent intent_setting = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent_setting);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });
    }
}
