package com.example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView toolbar_menu, toolbar_todo;
    private Dialog dialog_todoMain;
    private Button btn_todoAdd, btn_todoDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);

        drawerLayout = findViewById(R.id.drawer);
        drawerLayout.setDrawerLockMode(drawerLayout.LOCK_MODE_LOCKED_CLOSED); //드로어 swipe 금지

        toolbar_menu = findViewById(R.id.toolbar_menu);
        toolbar_todo = findViewById(R.id.toolbar_todo);

        dialog_todoMain = new Dialog(MainActivity.this);
        //dialog_todoMain.setContentView(R.layout.dialog_todoMain);

        btn_todoAdd = findViewById(R.id.btn_todoAdd);
        btn_todoDone = findViewById(R.id.btn_todoDone);

        toolbar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_todoMain.show();
            }
        });

//        btn_todoDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog_todoMain.dismiss();
//            }
//        });
//
//        btn_todoAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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
                        /*Intent intent_main = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent_main);*/
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
