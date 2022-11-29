package com.example.ui.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ui.DB.DAO.ScheduleDAO;
import com.example.ui.DB.DAO.TodoDao;
import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.Model.TodoData;

@Database(entities = {TodoData.class, ScheduleData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;

    private static String DATABASE_NAME = "database";

    //DB 생성, Singleton
    public static RoomDB getInstance(Context context) {
        if (database == null) {
            synchronized (RoomDB.class) {
                if (database == null) {

                    database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return database;
    }
    //---

    public abstract TodoDao mainDao();

    public abstract ScheduleDAO scheduleDao();

}
