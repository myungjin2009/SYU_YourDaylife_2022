package com.example.ui_bottom_menu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class diaryViewHolder extends RecyclerView.ViewHolder {

    public ImageView dImageView;
    public TextView dDateTextView;

    diaryViewHolder(Context context, View itemView) {
        super(itemView);

        dImageView = itemView.findViewById(R.id.diary_image);
        dDateTextView = itemView.findViewById(R.id.diary_dateTextView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryDialog diaryDlg = new diaryDialog(context);
                diaryDlg.show();
            }
        });


    }
}