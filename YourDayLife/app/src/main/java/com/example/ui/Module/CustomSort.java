package com.example.ui.Module;

import com.example.ui.DB.Model.TodoData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomSort {

    //Todo를 우선순위 기준으로 정렬
    public static List<TodoData> sortTodoByPriority(List<TodoData> dataList) {
        Comparator<TodoData> comparator = new Comparator<TodoData>() {
            @Override
            public int compare(TodoData o1, TodoData o2) {
                if (o1.getPriority() > o2.getPriority()) return 1;
                else if(o1.getPriority() == o2.getPriority()) return 0;
                else return -1;
            }
        };
        Collections.sort(dataList, comparator);
        return dataList;
    }


}
