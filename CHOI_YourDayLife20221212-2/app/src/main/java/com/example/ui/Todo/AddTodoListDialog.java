package com.example.ui.Todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class AddTodoListDialog extends Dialog {

    //spinner 우선순위 값
    String[] priorityItems = {"높음", "중간", "낮음"};
    int selectedPriority = 1;   //우선순위 기본값 : '중간'
    //LocalDate localDate;         //선택된 날짜
    String currentDate;
    List<Integer> currentDateList;
    RoomDB database;
    TextView textDate;
    EditText editText;


    Context context;
    public AddTodoListDialog(@NonNull Context context, String currentDate) {
        super(context);
        this.context = context;
        this.currentDate = currentDate;
    }

    private void showTodoDialog(String currentDate) {
        TodoListDialog todoListDialog;
        if (currentDate != null) {
            todoListDialog = new TodoListDialog(context, currentDate);
        } else {
            todoListDialog = new TodoListDialog(context);
        }
        todoListDialog.show();
        dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(context);
        setContentView(R.layout.dialog_todoadd);
        editText = findViewById(R.id.edit_text);
        textDate = findViewById(R.id.text_date);
        textDate.setText(currentDate);
        database = RoomDB.getInstance(context);
        currentDateList = CustomTime.stringToLocalDate(currentDate);

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


        //달력에서 날짜 바꿨을 때
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                List<String> monthAndDay = CustomTime.convertZeroDigit(month, dayOfMonth);
                textDate.setText(year + "-" + monthAndDay.get(0) + "-" + monthAndDay.get(1));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this.context, listener, currentDateList.get(0), currentDateList.get(1), currentDateList.get(2));

        //달력 창 띄우기
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        //하단 '취소' 버튼
        Button btn_todoCancel = findViewById(R.id.btn_todoCancel);
        btn_todoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTodoDialog(null);
            }
        });

        //하단 '추가' 버튼
        Button btn_todoAdd2 = findViewById(R.id.bt_add);
        btn_todoAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = editText.getText().toString().trim();
                if (!sText.equals("")) {
                    TodoData data = new TodoData();
                    data.setCreatedDate(textDate.getText().toString());
                    data.setText(sText);
                    data.setPriority(selectedPriority);
                    spinner.setSelection(1);
                    database.mainDao().insert(data);
                    showTodoDialog(textDate.getText().toString());
                }
            }
        });

    }
}
