package com.example.ui.Diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.DiaryData;
import com.example.ui.Notice.NoticeRecyclerAdapter;
import com.example.ui.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<DiaryData> dataList;

    public DiaryRecyclerAdapter(List<DiaryData> dataList) {
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView diaryListCard;
        ImageView imageView;
        TextView createDateText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diaryListCard = itemView.findViewById(R.id.diary_list_card);
            imageView = itemView.findViewById(R.id.diary_image);
            createDateText = itemView.findViewById(R.id.diary_dateTextView);
        }
    }


    @NonNull
    @Override
    public DiaryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.diary_list, parent, false);
        return new DiaryRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryData data = dataList.get(position);

        try {
            String imgpath = context.getCacheDir() + "/" + data.getImgSrc();
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            holder.imageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
        } catch (Exception e) {
            //이미지 없을 때
        }
        holder.createDateText.setText(data.getCreateDate());
        holder.diaryListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이어리 클릭했을 때!
                DiaryViewDialog diaryViewDialog = new DiaryViewDialog(context, data);
                diaryViewDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
