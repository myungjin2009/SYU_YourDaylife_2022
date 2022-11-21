package com.example.ui.Todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;
    TextView textDate;

    //spinner 우선순위 값
    String[] priorityItems = {"높음", "중간", "낮음"};
    int selectedPriority = 1;   //우선순위 기본값 : '중간'

    List<TodoData> dataList = new ArrayList<>();
    RoomDB database;
    TodoRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);
        textDate = findViewById(R.id.text_date);


        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), year + "년" + (month+1) + "월" + dayOfMonth + "일", Toast.LENGTH_SHORT).show();
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this, listener, 2013, 9, 22);

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //▼ 우선순위 spinner
        Spinner spinner = findViewById(R.id.priority_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, priorityItems);
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
        //▲ 우선순위 spinner

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();     //불러오기 Load
        dataList = sortTodoByPriority(dataList);    //정렬 알고리즘
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoRecyclerAdapter(TodoActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        //저장 Save
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = editText.getText().toString().trim();
                if (!sText.equals("")) {
                    TodoData data = new TodoData();
                    data.setText(sText);
                    data.setPriority(selectedPriority);
                    spinner.setSelection(1);
                    database.mainDao().insert(data);
                    editText.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    dataList = sortTodoByPriority(dataList);    //정렬 알고리즘
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(dataList);

                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    //Todo를 우선순위 기준으로 정렬
    private List<TodoData> sortTodoByPriority(List<TodoData> dataList) {
        Comparator<TodoData> comparator = new Comparator<TodoData>() {
            @Override
            public int compare(TodoData o1, TodoData o2) {
                if (o1.getPriority() > o2.getPriority()) return 1;
                else if(o1.getPriority() == o2.getPriority()) return 0;
                else return -1;
            }
        };
        Collections.sort(dataList, comparator);
        return dataList;
    }
}
