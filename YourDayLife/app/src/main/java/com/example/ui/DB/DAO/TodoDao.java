package com.example.ui.DB.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ui.DB.Model.TodoData;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = REPLACE)
    void insert(TodoData todoData);

    @Delete
    void delete(TodoData todoData);

    @Delete
    void reset(List<TodoData> todoData);

    @Query("UPDATE TodoData SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    @Query("SELECT * FROM TodoData")
    List<TodoData> getAll();
}
