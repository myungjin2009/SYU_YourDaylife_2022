package com.example.ui.Todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomSort;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends Dialog {

    Activity activity;
    Context context;
    RecyclerView recyclerView;
    TextView textDate;
    String currentDate;

    RoomDB database;
    List<TodoData> dataList = new ArrayList<>();
    TodoRecyclerAdapter adapter;


    public TodoListActivity(@NonNull Context context) {
        super(context);
        this.context = context;
        activity = (Activity) context;
    }
    public TodoListActivity(@NonNull Context context, String currentDate) {
        super(context);
        this.context = context;
        activity = (Activity) context;
        this.currentDate = currentDate;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_todomain);
        recyclerView = findViewById(R.id.recycler_view);

        textDate = findViewById(R.id.text_date);
        LocalDate localDate = CustomTime.getToday();
        if (currentDate == null) {
            textDate.setText(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            textDate.setText(currentDate);
        }

        database = RoomDB.getInstance(context);
        loadTodo();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TodoRecyclerAdapter(activity, dataList, textDate.getText().toString());
        recyclerView.setAdapter(adapter);


        //달력에서 날짜 바꿨을 때
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                List<String> monthAndDay = CustomTime.convertZeroDigit(month, dayOfMonth);
                textDate.setText(year + "-" + monthAndDay.get(0) + "-" + monthAndDay.get(1));
                loadTodo();
                adapter.setToday(textDate.getText().toString());
                adapter.notifyDataSetChanged();
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this.context, listener, localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());

        //상단 '날짜 텍스트' 선택 시, 달력창 띄우기
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //하단 '확인' 버튼
        Button btn_todoDone = findViewById(R.id.btn_todoDone);
        btn_todoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //하단 '추가' 버튼
        Button btn_todoAdd = findViewById(R.id.btn_todoAdd);
        btn_todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTodoListActivity addTodoListActivity = new AddTodoListActivity(context, localDate);
                addTodoListActivity.show();
                dismiss();
            }
        });

    }



    private void loadTodo() {   //textDate 날짜의 Todo 불러와서 정렬하기
        dataList.clear();
        dataList.addAll(database.mainDao().getCurrentDate(textDate.getText().toString()));     //오늘 날짜의 textString값으로 불러오기 Load
        dataList = CustomSort.sortTodoByPriority(dataList);    //정렬 알고리즘
    }
}
