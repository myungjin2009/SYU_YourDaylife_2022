package com.example.ui.Diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.DiaryData;
import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomSort;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;
import com.example.ui.Todo.TodoListDialog;
import com.example.ui.Todo.TodoRecyclerAdapter;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DiaryViewDialog extends Dialog {

    Activity activity;
    DiaryData data;
    TextView textDate, textContent;
    ImageView imageView;
    RecyclerView recyclerViewTodo;
    Button goBack;

    RoomDB database;
    List<TodoData> todoData = new ArrayList<>();
    TodoRecyclerAdapter todoAdapter;


    Context context;
    public DiaryViewDialog(@NonNull Context context, DiaryData data) {
        super(context);
        this.context = context;
        this.data = data;
        activity = (Activity) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(context);
        setContentView(R.layout.dialog_diary);

        imageView = findViewById(R.id.addImage_diary);
        textDate = findViewById(R.id.date_diary);
        textContent = findViewById(R.id.text_diary_content);
        recyclerViewTodo = findViewById(R.id.recycler_view_todo);

        textDate.setText(data.getCreateDate());
        textContent.setText(data.getContent());

        database = RoomDB.getInstance(context);
        loadTodo();
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(context));
        todoAdapter = new TodoRecyclerAdapter(activity, todoData, textDate.getText().toString());
        recyclerViewTodo.setAdapter(todoAdapter);

        goBack = findViewById(R.id.btn_diary_cancel);

        try {
            String imgpath = context.getCacheDir() + "/" + data.getImgSrc();
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            imageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
        } catch (Exception e) {
            //이미지 없을 때
        }


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void loadTodo() {   //투두리스트 불러와서 정렬하기
        //todoData.clear();
        todoData.addAll(database.mainDao().getCurrentDate(textDate.getText().toString()));     //오늘 날짜의 textString값으로 불러오기 Load
        todoData = CustomSort.sortTodoByPriority(todoData);    //정렬 알고리즘
    }
}
