package com.example.ui_bottom_menu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class diaryDialog extends Dialog {
    Context context;

    /*선언*/
    //날짜
    TextView dateDiary;
    int diaryY, diaryM, diaryD;
    //버튼
    Button diaryCancle, diaryAdd;
    public diaryDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diary);

        /*날짜 표시*/
        //날짜표시 기능구현
        dateDiary = findViewById(R.id.date_diary);
        Calendar cal = new GregorianCalendar();
        diaryY = cal.get(Calendar.YEAR);
        diaryM = cal.get(Calendar.MONTH)+1;
        diaryD = cal.get(Calendar.DAY_OF_MONTH);
        dateDiary.setText(diaryY + "." + diaryM + "." + diaryD);
        //날짜수정 기능구현
        dateDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = year + "." + month + "." + dayOfMonth;
                        dateDiary.setText(date);
                        diaryY = year;
                        diaryM = month;
                        diaryD = dayOfMonth;
                    }
                }, diaryY, diaryM-1, diaryD);
                dpd.show();
            }
        });

        /*버튼*/
        //취소
        diaryCancle = findViewById(R.id.btn_diaryCancle);
        diaryCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //추가
        diaryAdd = findViewById(R.id.btn_diaryAdd);
        diaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추가 시 해야할 것
                dismiss();
            }
        });
    }
}
