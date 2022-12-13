package com.example.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.Module.CustomEventBus;
import com.example.ui.Module.CustomNoticeCrawlling;
import com.example.ui.Module.CustomScheduleCrawlling;
import com.example.ui.Notice.NoticeRecyclerAdapter;
import com.example.ui.Schedule.ScheduleRecyclerAdapter;
import com.example.ui.Todo.VariableSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView scheduleRecyclerView, noticeRecyclerView;
    private Context parentContext;
    private TextView textScheduleShowAll,textNoticeShowAll;
    private ScheduleRecyclerAdapter sra;
    private CustomScheduleCrawlling customCrawlling;

    public HomeFragment() {
        // Required empty public constructor
    }

    //CustomEventBus >> MainFragment에서의 toolbar_sync(새로고침)버튼 이벤트를 받기위한 등록
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReceiveButtonOnClickEventBus(CustomEventBus.CustomOnClickEvent onClickEvent) {

        switch (onClickEvent.currentEvent) {
            case "toolbar_sync" : {
                reloadSchedule();
                break;
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) { }
    }
    @Override
    public void onStop() {
        super.onStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) { }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        parentContext = container.getContext();
        scheduleRecyclerView = view.findViewById(R.id.schedule_recycler_view);
        noticeRecyclerView = view.findViewById(R.id.notice_recycler_view);
        textScheduleShowAll = view.findViewById(R.id.text_schedule_show_all);
        textNoticeShowAll = view.findViewById(R.id.text_notice_show_all);

        /*화면 주요 컨텐츠*/
        //올해 주요 학사일정(Schedule Data) DB >> RecyclerView 불러오기
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(parentContext));
        sra = new ScheduleRecyclerAdapter(parentContext);
        scheduleRecyclerView.setAdapter(sra);

        //전체 공지사항 Web Crawlling >> RecyclerView 불러오기
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(parentContext));
        CustomNoticeCrawlling customNoticeCrawlling = new CustomNoticeCrawlling(parentContext);
        customNoticeCrawlling.getCrawlling();
        customNoticeCrawlling.returnDataList.observe(this.getViewLifecycleOwner(), new Observer<List<CustomNoticeCrawlling.DataList>>() {
            @Override
            public void onChanged(List<CustomNoticeCrawlling.DataList> dataLists) {
                NoticeRecyclerAdapter nra = new NoticeRecyclerAdapter(parentContext, dataLists);
                noticeRecyclerView.setAdapter(nra);
            }
        });

        //학사일정 '전체보기' 텍스트 클릭시
        textScheduleShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VariableSet.getSyuScheduleUri()));
                startActivity(intent);
            }
        });

        //공지사항 '전체보기' 텍스트 클릭시
        textNoticeShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VariableSet.getSyuNoticeUri()));
                startActivity(intent);
            }
        });

        return view;
    }

    private void reloadSchedule() {
        customCrawlling = new CustomScheduleCrawlling(parentContext);
        customCrawlling.getCrawlling();

        customCrawlling.liveScheduleData.observe(this.getViewLifecycleOwner(), new Observer<List<ScheduleData>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<ScheduleData> scheduleData) {
                sra.updateSchedule();
            }
        });
    }
}