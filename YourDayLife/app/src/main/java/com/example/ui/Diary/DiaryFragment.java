package com.example.ui.Diary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.DiaryData;
import com.example.ui.DB.RoomDB;
import com.example.ui.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DiaryFragment extends Fragment {

    RecyclerView diaryRecyclerView;
    DiaryRecyclerAdapter dra;
    RoomDB database;
    List<DiaryData> dataList;

    public DiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        database = RoomDB.getInstance(container.getContext());
        dataList = database.diaryDAO().getAll();

        diaryRecyclerView = view.findViewById(R.id.recyclerView);

        diaryRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),3));
        dra = new DiaryRecyclerAdapter(dataList);
        diaryRecyclerView.setAdapter(dra);

        return view;
    }
}