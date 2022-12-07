package com.example.ui.Todo;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomSort;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Deprecated
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);
        textDate = findViewById(R.id.text_date);
        LocalDate localDate = CustomTime.getToday();
        textDate.setText(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        //달력에서 날짜 바꿨을 때
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                loadTodo();
                adapter.setToday(textDate.getText().toString());
                adapter.notifyDataSetChanged();
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this, listener, localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());

        //달력 창 띄우기
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
        loadTodo();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoRecyclerAdapter(TodoActivity.this, dataList, textDate.getText().toString());
        recyclerView.setAdapter(adapter);

        //저장 Save
        btAdd.setOnClickListener(new View.OnClickListener() {
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
                    editText.setText("");
                    loadTodo();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(dataList);
                dataList.clear();
                loadTodo();
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void loadTodo() {   //textDate 날짜의 Todo 불러와서 정렬하기
        dataList.clear();
        dataList.addAll(database.mainDao().getCurrentDate(textDate.getText().toString()));     //오늘 날짜의 textString값으로 불러오기 Load
        dataList = CustomSort.sortTodoByPriority(dataList);    //정렬 알고리즘
    }
}
