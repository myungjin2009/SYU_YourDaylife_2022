package com.example.ui.DB.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.Model.TodoData;

import java.util.List;

@Dao
public interface ScheduleDAO {

    @Insert(onConflict = REPLACE)
    void insert(ScheduleData scheduleModel);

    @Delete
    void reset(List<TodoData> todoData);

    @Query("SELECT * FROM ScheduleData")
    List<ScheduleData> getAll();

    @Query("SELECT * FROM ScheduleData WHERE date LIKE '%' || :monthAndDay  || '%'")
    List<ScheduleData> getCurrentMonth(String monthAndDay);

}
