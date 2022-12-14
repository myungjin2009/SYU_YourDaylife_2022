package com.example.ui.Notice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.Module.CustomNoticeCrawlling;
import com.example.ui.R;

import java.util.List;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder> {

    private List<CustomNoticeCrawlling.DataList> dataList;

    public NoticeRecyclerAdapter(Context context, List<CustomNoticeCrawlling.DataList> dataList) {
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainView;
        TextView textNoticeDate, textNoticeContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainView = itemView.findViewById(R.id.mainView);
            textNoticeContent = itemView.findViewById(R.id.text_notice_content);
            textNoticeDate = itemView.findViewById(R.id.text_notice_date);
        }
    }

    @NonNull
    @Override
    public NoticeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_notice, parent, false);
        return new NoticeRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CustomNoticeCrawlling.DataList cncd = dataList.get(position);
        holder.textNoticeDate.setText(cncd.getDate());
        holder.textNoticeContent.setText(cncd.getTitle());
        holder.textNoticeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cncd.getHref()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
