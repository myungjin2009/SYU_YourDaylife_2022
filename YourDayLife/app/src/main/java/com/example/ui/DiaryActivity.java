package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class DiaryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView toolbar_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);

        drawerLayout = findViewById(R.id.drawer);
        drawerLayout.setDrawerLockMode(drawerLayout.LOCK_MODE_LOCKED_CLOSED); //드로어 swipe 금지

        toolbar_menu = findViewById(R.id.toolbar_menu);

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
                        Intent intent_main = new Intent(DiaryActivity.this, MainActivity.class);
                        startActivity(intent_main);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_diary:
                        /*Intent intent_diary = new Intent(DiaryActivity.this, DiaryActivity.class);
                        startActivity(intent_diary);*/
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_setting:
                        Intent intent_setting = new Intent(DiaryActivity.this, SettingActivity.class);
                        startActivity(intent_setting);
                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });

    }
}