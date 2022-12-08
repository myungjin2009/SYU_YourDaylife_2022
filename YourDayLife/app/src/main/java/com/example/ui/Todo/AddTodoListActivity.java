package com.example.ui.Todo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.R;

import java.util.ArrayList;
import java.util.List;

public class AddTodoListActivity extends Dialog {

    //spinner 우선순위 값
    String[] priorityItems = {"높음", "중간", "낮음"};
    int selectedPriority = 1;   //우선순위 기본값 : '중간'
    List<TodoData> dataList = new ArrayList<>();
    RoomDB database;


    Context context;
    public AddTodoListActivity(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_todoadd);

        //우선순위 (높음,중간,낮음) spinner
        Spinner spinner = findViewById(R.id.priority_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this.context, android.R.layout.simple_spinner_item, priorityItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(1); //우선순위 기본값 : '중간'

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPriority = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPriority = 1;   //우선순위 기본값 : '중간'
            }
        });


        //하단 '취소' 버튼
        Button btn_todoCancel = findViewById(R.id.btn_todoCancel);
        btn_todoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //하단 '추가' 버튼
        Button btn_todoAdd2 = findViewById(R.id.bt_add);
        btn_todoAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.dialog_todomain);
            }
        });

    }
}
