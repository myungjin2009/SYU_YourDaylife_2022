package com.example.ui.Module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.ui.DB.Model.ScheduleData;
import com.example.ui.DB.RoomDB;
import com.example.ui.R;
import com.example.ui.Todo.VariableSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//안성민 - 전체공지 크롤링 모듈
/* 학사공지 바로가기 -< 검색하면 학사공지 바로가기 코드들 고친거,
       학사 공지 등록일 수정 부분 -< 검색하면 공지 등록일 코드들 나와
       xml 텍스트 뷰랑, 코드들만 위치에 맞게 복붙하면 너가 적용하기 쉬울꺼야
       돌린 다음에 학사공지 바로가기 클릭하면 학사공지 나오거든? 이걸로도 충분하지 않나 어차피 모바일 화면으로 나와서 이쁘게 나오는데
       확인 부탁해
     */
public class CustomNoticeCrawlling {
    Context context;
    public CustomNoticeCrawlling(Context context) {
        this.context = context;
    }

    public class DataList {
        private String title, date, href;
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getHref() { return href; }
        public void setHref(String href) { this.href = href; }
    }

    String url1 = VariableSet.getSyuNoticeUri(); //학사공지
    String[] title = new String[5];
    String[] date = new String[5];
    String[] my_link = new String[5];

    //데이터 반환을 위한 List 추가
    private List<DataList> resultSet = new ArrayList<>();
    public MutableLiveData<List<DataList>> returnDataList = new MutableLiveData<>();

    //글 제목
    final Bundle bundle1 = new Bundle();
    final Bundle bundle2 = new Bundle();
    final Bundle bundle3 = new Bundle();
    final Bundle bundle4 = new Bundle();
    final Bundle bundle5 = new Bundle();

    //작성일
    final Bundle bundle6 = new Bundle();
    final Bundle bundle7 = new Bundle();
    final Bundle bundle8 = new Bundle();
    final Bundle bundle9 = new Bundle();
    final Bundle bundle10 = new Bundle();

    public void getCrawlling() {
        //핸들러 부분 텍스트뷰에 메세지 전달

        Handler handler1 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle1 = msg.getData();
                title[0] = bundle1.getString("message");
            }
        };
        Handler handler2 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle2 = msg.getData();
                title[1] = bundle2.getString("message");
            }
        };
        Handler handler3 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle3 = msg.getData();
                title[2] = bundle3.getString("message");
            }
        };
        Handler handler4 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle4 = msg.getData();
                title[3] = bundle4.getString("message");
            }
        };
        Handler handler5 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle5 = msg.getData();
                title[4] = bundle5.getString("message");
            }
        };

        //학사 공지 등록일 수정 부분
        Handler handler6 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle6 = msg.getData();
                date[0] = bundle6.getString("message");
            }
        };
        Handler handler7 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle7 = msg.getData();
                date[1] = bundle7.getString("message");
            }
        };
        Handler handler8 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle8 = msg.getData();
                date[2] = bundle8.getString("message");
            }
        };
        Handler handler9 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle9 = msg.getData();
                date[3] = bundle9.getString("message");
            }
        };
        Handler handler10 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle10 = msg.getData();
                date[4] = bundle10.getString("message");
            }
        };


        //학사공지
        new Thread() {
            @Override
            public void run() {
                Document doc =null;
                try{
                    doc = Jsoup.connect(url1).get();

                    Elements link = doc.select(".tit");
                    //학사 공지 등록일 수정 부분
                    Elements registerdate =  doc.select("td[class=step4]");

                    my_link[0] = doc.select("td[class=step2] a").eq(0).attr("href");
                    my_link[1] = doc.select("td[class=step2] a").eq(1).attr("href");
                    my_link[2] = doc.select("td[class=step2] a").eq(2).attr("href");
                    my_link[3] = doc.select("td[class=step2] a").eq(3).attr("href");
                    my_link[4] = doc.select("td[class=step2] a").eq(4).attr("href");


                    String mg1 = link.select(".tit").eq(0).text();
                    bundle1.putString("message", mg1);
                    Message msg1 = handler1.obtainMessage();
                    msg1.setData(bundle1);
                    handler1.sendMessage(msg1);

                    String mg2 = link.select(".tit").eq(1).text();
                    bundle2.putString("message", mg2);
                    Message msg2 = handler1.obtainMessage();
                    msg2.setData(bundle2);
                    handler2.sendMessage(msg2);

                    String mg3 = link.select(".tit").eq(2).text();
                    bundle3.putString("message", mg3);
                    Message msg3 = handler1.obtainMessage();
                    msg3.setData(bundle3);
                    handler3.sendMessage(msg3);

                    String mg4 = link.select(".tit").eq(3).text();
                    bundle4.putString("message", mg4);
                    Message msg4 = handler1.obtainMessage();
                    msg4.setData(bundle4);
                    handler4.sendMessage(msg4);

                    String mg5 = link.select(".tit").eq(4).text();
                    bundle5.putString("message", mg5);
                    Message msg5 = handler1.obtainMessage();
                    msg5.setData(bundle5);
                    handler5.sendMessage(msg5);

                    /*학사 공지 등록일 수정 부분 --> 만약 위에서 붙여서 사용할꺼면  이어붙어질듯? 사이에 +" "+
                    String mg1 = link.select(".tit").eq(0).text() +" "+ registerdate.eq(0).text();
                    1-6, 2-7, 3-8, 4-9 5-10 매칭
                     */
                    String mg6 = registerdate.eq(0).text(); //등록일 텍스트
                    bundle6.putString("message", mg6);
                    Message msg6 = handler6.obtainMessage();
                    msg6.setData(bundle6);
                    handler6.sendMessage(msg6);

                    String mg7 = registerdate.eq(1).text();
                    bundle7.putString("message", mg7);
                    Message msg7 = handler6.obtainMessage();
                    msg7.setData(bundle7);
                    handler7.sendMessage(msg7);

                    String mg8 = registerdate.eq(2).text();
                    bundle8.putString("message", mg8);
                    Message msg8 = handler8.obtainMessage();
                    msg8.setData(bundle8);
                    handler8.sendMessage(msg8);

                    String mg9 = registerdate.eq(3).text();
                    bundle9.putString("message", mg9);
                    Message msg9 = handler9.obtainMessage();
                    msg9.setData(bundle9);
                    handler9.sendMessage(msg9);

                    String mg10 = registerdate.eq(4).text();
                    bundle10.putString("message", mg10);
                    Message msg10 = handler10.obtainMessage();
                    msg10.setData(bundle10);
                    handler10.sendMessage(msg10);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < title.length; i++) {
                    //new 생성자를 for문 밖에다 둘 경우,
                    //resultSet.add에는 전부 다 똑같은 객체주소값이 들어간다. (전부 동일 값)
                    //결론적으로, new 생성자는 for문 안에다 두어야 한다.
                    //좀 더 공부가 필요함
                    DataList dataList = new DataList();
                    dataList.setTitle(title[i]);
                    dataList.setDate(date[i]);
                    dataList.setHref(my_link[i]);
                    resultSet.add(dataList);
                }
                returnDataList.postValue(resultSet);
            }
        }.start();
    }
}