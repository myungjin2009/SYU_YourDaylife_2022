package com.example.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class spinnerDialog extends Dialog {
    Context context;
    int set_y, set_m, get1, get2;
    public spinnerDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_spinner);

        NumberPicker yearpicker, monthpicker;
        Button spinnerCancle, spinncerSet;

        Calendar cal = new GregorianCalendar();

        //날짜변경 기능구현
        set_y = ((MainActivity)MainActivity.context_main).date_y;
        set_m = ((MainActivity)MainActivity.context_main).date_m;

        yearpicker = findViewById(R.id.yearpicker_datepicker);
        monthpicker = findViewById(R.id.monthpicker_datepicker);
        spinnerCancle = findViewById(R.id.btn_spinnerCancel);
        spinncerSet = findViewById(R.id.btn_spinnerSet);

        //순환 금지
        yearpicker.setWrapSelectorWheel(false);
        //에딧텍스트 해제
        yearpicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        monthpicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //최소값 설정
        yearpicker.setMinValue(2000);
        monthpicker.setMinValue(1);
        //최대값 설정
        yearpicker.setMaxValue(2050);
        monthpicker.setMaxValue(12);
        //초기값 설정
        yearpicker.setValue(set_y);
        monthpicker.setValue(set_m);
        //취소버튼
        spinnerCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //설정버튼
        spinncerSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get1 = yearpicker.getValue();
                get2= monthpicker.getValue();

                ((MainActivity)MainActivity.context_main).date_y = get1;
                ((MainActivity)MainActivity.context_main).date_m = get2;

                ((MainActivity)MainActivity.context_main).toolbar_date.setText(get1+ "." + get2);
                dismiss();
            }
        });
    }
}
