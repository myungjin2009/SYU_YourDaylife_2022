package com.example.yourdaylife.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yourdaylife.DB.DAO.UserDao;
import com.example.yourdaylife.DB.Entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDBInstance(Context context) {
        //INSTANCE가 null이면 초기화, 싱글톤 패턴
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "LocalDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
