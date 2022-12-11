package com.example.ui_bottom_menu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class todoDialogAdd extends Dialog {
    Context context;
    public todoDialogAdd(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_todoadd);

        Button btn_todoCancel = findViewById(R.id.btn_todoCancel);
        btn_todoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btn_todoAdd2 = findViewById(R.id.btn_todoAdd2);
        btn_todoAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.dialog_todomain);
            }
        });

    }
}
