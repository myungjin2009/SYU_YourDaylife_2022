package com.example.ui.DB.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ScheduleData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String year;

    @ColumnInfo
    private String startDate;

    @ColumnInfo
    private String endDate;

    @ColumnInfo
    private String content;

    //1-오늘의 일정 | 0-아직 안 다가온 일정
    @Ignore
    private boolean isTodaySchedule;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean getIsTodaySchedule() {
        return isTodaySchedule;
    }

    public void setIsTodaySchedule(boolean isTodaySchedule) {
        this.isTodaySchedule = isTodaySchedule;
    }
}
