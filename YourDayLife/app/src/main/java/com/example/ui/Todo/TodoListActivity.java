package com.example.ui.Todo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomSort;
import com.example.ui.R;
import com.example.ui.todoDialogAdd;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends Dialog {

    Activity activity;
    Context context;
    RecyclerView recyclerView;

    RoomDB database;
    List<TodoData> dataList = new ArrayList<>();
    TodoRecyclerAdapter adapter;

    public TodoListActivity(@NonNull Context context) {
        super(context);
        this.context = context;
        activity = (Activity) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_todomain);
        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(context);
        loadTodo();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TodoRecyclerAdapter(activity, dataList, "2022-12-07");
        recyclerView.setAdapter(adapter);

        Button btn_todoDone = findViewById(R.id.btn_todoDone);
        btn_todoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btn_todoAdd = findViewById(R.id.btn_todoAdd);
        btn_todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoDialogAdd todoDAdd = new todoDialogAdd(context);
                todoDAdd.show();
            }
        });

    }

    private void loadTodo() {   //textDate 날짜의 Todo 불러와서 정렬하기
        dataList.clear();
        dataList.addAll(database.mainDao().getCurrentDate("2022-12-07"));     //오늘 날짜의 textString값으로 불러오기 Load
        dataList = CustomSort.sortTodoByPriority(dataList);    //정렬 알고리즘
    }
}
