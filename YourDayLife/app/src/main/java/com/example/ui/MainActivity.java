package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.ui.Todo.TodoListActivity;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView toolbar_menu, toolbar_todo;


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


        //오른쪽 상단 체크(TodoList) 버튼
        toolbar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoListActivity todoListActivity = new TodoListActivity(MainActivity.this);
                todoListActivity.show();
            }
        });


        //메뉴버튼 눌렀을때
        toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //드로어 메뉴 옵션
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
