package com.example.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class todoDialog extends Dialog {
    Context context;

    public todoDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_todomain);

        Button btn_todoDone = findViewById(R.id.btn_todoDone);
        btn_todoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btn_todoAdd = findViewById(R.id.btn_todoAdd);
        btn_todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoDialogAdd todoDAdd = new todoDialogAdd(context);
                todoDAdd.show();
            }
        });




    }
}
