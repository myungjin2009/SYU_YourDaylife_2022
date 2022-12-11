/*
package com.example.ui_bottom_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class diaryAdapter extends RecyclerView.Adapter<diaryViewHolder> {

    private ArrayList<String> arrayList;

    public diaryAdapter() {
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public diaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.diary_list, parent, false);
        diaryViewHolder viewHolder = new diaryViewHolder(context, view);
        return viewHolder;
    }

    public void setArrayData(String strDate){
        arrayList.add(strDate);
    }
}
*/
