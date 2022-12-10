package com.example.ui.Schedule;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
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
        List<ScheduleData> afterDataList = new ArrayList<>();
        getDataList = database.scheduleDao().getAll();
        if (getDataList.isEmpty()) {
            ScheduleData scheduleData = new ScheduleData();
            scheduleData.setYear("0000");
            scheduleData.setStartDate("");
            scheduleData.setEndDate("");
            scheduleData.setContent("불러온 일정이 없습니다!");
            scheduleData.setIsTodaySchedule(false);
            afterDataList.add(scheduleData);
        } else {
            LocalDate today = CustomTime.getToday();
            String scheduleStartDateString, scheduleEndDateString;
            LocalDate startDate, endDate;
            int startResult, endResult;

            for (ScheduleData scheduleData : getDataList) {
                scheduleEndDateString = scheduleData.getYear() +"-"+ scheduleData.getEndDate().substring(0,2) + "-" + scheduleData.getEndDate().substring(3);
                endDate = LocalDate.parse(scheduleEndDateString, DateTimeFormatter.ISO_DATE);
                endResult = today.compareTo(endDate);
                if(endResult == 0){
                    //동일한 날짜
                    scheduleData.setIsTodaySchedule(true);
                    afterDataList.add(scheduleData);
                }
                else if (endResult < 0 ) {
                    scheduleStartDateString = scheduleData.getYear() +"-"+ scheduleData.getStartDate().substring(0,2) + "-" + scheduleData.getStartDate().substring(3);
                    startDate = LocalDate.parse(scheduleStartDateString, DateTimeFormatter.ISO_DATE);
                    startResult = today.compareTo(startDate);
                    if(startResult > 0) {
                        //일정 사이에 있음
                        scheduleData.setIsTodaySchedule(true);
                        afterDataList.add(scheduleData);
                    } else {
                        //아직 다가오지 않은 일정
                        scheduleData.setIsTodaySchedule(false);
                        afterDataList.add(scheduleData);
                    }
                }
                //지난 일정은 가져오지 않음
            }
        }
        return afterDataList;
    }

    public void updateSchedule() {
        dataList = getSchedule();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout mainView;
        TextView textViewYear, textViewStartDate, textViewEndDate, textViewContent;


        public ViewHolder(@NonNull View view)
        {
            super(view);
            //textViewYear = view.findViewById(R.id.text_view_year);
            mainView = view.findViewById(R.id.mainView);
            textViewStartDate = view.findViewById(R.id.textStartDate);
            textViewEndDate = view.findViewById(R.id.textEndDate);
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
        holder.textViewStartDate.setText(data.getStartDate());
        holder.textViewEndDate.setText(data.getEndDate());
        holder.textViewContent.setText(data.getContent());

        if (data.getIsTodaySchedule()) {
            color = "46FFFF";
        } else if (!data.getIsTodaySchedule()) {
            color = "DFDFDF";
        }
        holder.mainView.setBackgroundColor(Color.parseColor("#"+color));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
