package com.example.ui.Todo;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui.DB.Model.TodoData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomSort;
import com.example.ui.R;

import java.util.List;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>
{
    private List<TodoData> dataList;
    private Activity context;
    private RoomDB database;
    private String currentDay;

    public TodoRecyclerAdapter(Activity context, List<TodoData> dataList, String currentDay)
    {
        this.context = context;
        this.dataList = dataList;
        this.currentDay = currentDay;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoRecyclerAdapter.ViewHolder holder, int position)
    {
        final TodoData data = dataList.get(position);
        String priority = "중간";
        database = RoomDB.getInstance(context);

        switch (data.getPriority()) {
            case 0:
                priority = "높음!";
                holder.mainView.setBackgroundColor(Color.parseColor("#ffc0cb")); //핑크
                break;
            case 1:
                priority = "중간";
                holder.mainView.setBackgroundColor(Color.parseColor("#fecdc1")); //살구
                break;
            case 2:
                priority = "낮음";
                holder.mainView.setBackgroundColor(Color.parseColor("#a27c82")); //갈색
                break;
        }
        holder.priorityView.setText(priority);
        holder.textView.setText(data.getText());
        holder.btEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TodoData todoData = dataList.get(holder.getAdapterPosition());

                final int sID = todoData.getId();
                String sText = todoData.getText();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);

                dialog.show();

                final EditText editText = dialog.findViewById(R.id.dialog_edit_text);
                Button bt_update = dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                bt_update.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                        String uText = editText.getText().toString().trim();

                        database.mainDao().update(sID, uText);

                        dataList.clear();
                        dataList.addAll(database.mainDao().getCurrentDate(currentDay));     //해당 날짜의 textString값으로 불러오기 Load
                        dataList = CustomSort.sortTodoByPriority(dataList);    //정렬 알고리즘

                        notifyDataSetChanged();
                    }
                });
            }
        });

        //삭제 버튼
        holder.btDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TodoData todoData = dataList.get(holder.getAdapterPosition());

                database.mainDao().delete(todoData);

                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public String getToday() {
        return currentDay;
    }

    public void setToday(String currentDay) {
        this.currentDay = currentDay;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout mainView;
        TextView textView, priorityView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            mainView = view.findViewById(R.id.mainView);
            priorityView = view.findViewById(R.id.text_priority);
            textView = view.findViewById(R.id.text_view);
            btEdit = view.findViewById(R.id.bt_edit);
            btDelete = view.findViewById(R.id.bt_delete);
        }
    }
}