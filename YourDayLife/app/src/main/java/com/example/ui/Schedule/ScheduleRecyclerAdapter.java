package com.example.ui.Schedule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ViewHolder> {
    private RoomDB database;
    private List<ScheduleData> dataList;
    private String color = "AFAFAF";

    public ScheduleRecyclerAdapter(Context context) {
        database = RoomDB.getInstance(context);
        dataList = getSchedule();
    }

    public List<ScheduleData> getSchedule() {
        List<ScheduleData> getDataList = new ArrayList<>();
        getDataList = database.scheduleDao().getAll();
        if (getDataList.isEmpty()) {
            ScheduleData scheduleData = new ScheduleData();
            scheduleData.setYear("0000");
            scheduleData.setDate("");
            scheduleData.setContent("불러온 일정이 없습니다!");
            getDataList.add(scheduleData);
        }
        return getDataList;
    }

    public void updateSchedule() {
        dataList = getSchedule();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout mainView;
        TextView textViewYear, textViewDate, textViewContent;


        public ViewHolder(@NonNull View view)
        {
            super(view);
            //textViewYear = view.findViewById(R.id.text_view_year);
            mainView = view.findViewById(R.id.mainView);
            textViewDate = view.findViewById(R.id.text_view_date);
            textViewContent = view.findViewById(R.id.text_view_content);
        }
    }


    @NonNull
    @Override
    public ScheduleRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_schedule, parent, false);
        return new ScheduleRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ScheduleData data = dataList.get(position);

        //holder.textViewYear.setText(data.getYear());
        holder.textViewDate.setText(data.getDate());
        holder.textViewContent.setText(data.getContent());

        if(position % 2 == 0){ color = "EFEFEF"; }
        else if (position % 2 == 1) { color = "AFAFAF"; }
        holder.mainView.setBackgroundColor(Color.parseColor("#"+color));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
