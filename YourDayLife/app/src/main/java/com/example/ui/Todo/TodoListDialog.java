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

import java.util.ArrayList;
import java.util.List;

public class TodoListDialog extends Dialog {

    Activity activity;
    Context context;
    RecyclerView recyclerView;
    TextView textDate;
    String currentDate;
    List<Integer> todayDate = new ArrayList<>();

    RoomDB database;
    List<TodoData> dataList = new ArrayList<>();
    TodoRecyclerAdapter adapter;


    public TodoListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        activity = (Activity) context;
    }
    public TodoListDialog(@NonNull Context context, String currentDate) {
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

        if(currentDate == null) {
            currentDate = CustomTime.getTodayToString();
        }

        textDate = findViewById(R.id.text_date);
        todayDate = CustomTime.getTodayToList();
        textDate.setText(currentDate);

        database = RoomDB.getInstance(context);
        loadTodo();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TodoRecyclerAdapter(activity, dataList, textDate.getText().toString());
        recyclerView.setAdapter(adapter);


        //???????????? ?????? ????????? ???
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                List<String> monthAndDay = CustomTime.convertZeroDigit(month, dayOfMonth);
                currentDate = year + "-" + monthAndDay.get(0) + "-" + monthAndDay.get(1);
                textDate.setText(currentDate);
                loadTodo();
                adapter.setToday(textDate.getText().toString());
                adapter.notifyDataSetChanged();
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this.context, listener, todayDate.get(0), todayDate.get(1), todayDate.get(2));

        //?????? '?????? ?????????' ?????? ???, ????????? ?????????
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //?????? '??????' ??????
        Button btn_todoDone = findViewById(R.id.btn_todoDone);
        btn_todoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //?????? '??????' ??????
        Button btn_todoAdd = findViewById(R.id.btn_todoAdd);
        btn_todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTodoListDialog addTodoListDialog = new AddTodoListDialog(context, currentDate);
                addTodoListDialog.show();
                dismiss();
            }
        });

    }

    private void loadTodo() {   //textDate ????????? ??????????????? ???????????? ????????????
        dataList.clear();
        dataList.addAll(database.mainDao().getCurrentDate(textDate.getText().toString()));     //?????? ????????? textString????????? ???????????? Load
        dataList = CustomSort.sortTodoByPriority(dataList);    //?????? ????????????
    }
}
