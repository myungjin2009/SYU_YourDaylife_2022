package com.example.ui.DB.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ui.DB.Model.DiaryData;
import com.example.ui.DB.Model.ScheduleData;

import java.util.List;

@Dao
public interface DiaryDAO {

    @Insert(onConflict = REPLACE)
    void insert(DiaryData diaryData);

    @Delete
    void reset(List<DiaryData> diaryData);

    @Query("SELECT * FROM DiaryData")
    List<DiaryData> getAll();

    @Query("SELECT * FROM DiaryData WHERE createDate LIKE '%' || :createDate  || '%'")
    DiaryData getCreateDate(String createDate);

}
