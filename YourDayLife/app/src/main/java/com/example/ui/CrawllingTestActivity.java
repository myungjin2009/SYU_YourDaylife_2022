package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.ui.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawllingTestActivity extends AppCompatActivity {

    //현재 월 설정
   /* long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat mformat = new SimpleDateFormat("MM");
    String getTime = mformat.format(mDate);
    int getMonth = Integer.parseInt(getTime);*/
    TextView textview, textview1, textview2, textview3, textview4, textview5;
    String url = "https://www.syu.ac.kr/academic/major-schedule/"; //학사일정
    String url1 = "https://www.syu.ac.kr/academic/academic-notice/"; //학사공지
    String msg = "";
    String msg1 = "";
    final Bundle bundle = new Bundle();
    final Bundle bundle1 = new Bundle();
    final Bundle bundle2 = new Bundle();
    final Bundle bundle3 = new Bundle();
    final Bundle bundle4 = new Bundle();
    final Bundle bundle5 = new Bundle();
    String[] my_link = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_crawlling);
        textview = (TextView) findViewById(R.id.textview);
        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview3 = (TextView) findViewById(R.id.textview3);
        textview4 = (TextView) findViewById(R.id.textview4);
        textview5 = (TextView) findViewById(R.id.textview5);
        msg1="학사 일정 없음";
        textview.setText(msg1);

        textview1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_link[0]));
                startActivity(urlintent);
            }
        });
        textview2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_link[1]));
                startActivity(urlintent);
            }
        });
        textview3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_link[2]));
                startActivity(urlintent);
            }
        });
        textview4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_link[3]));
                startActivity(urlintent);
            }
        });
        textview5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_link[4]));
                startActivity(urlintent);
            }
        });
        //핸들러 부분 텍스트뷰에 메세지 전달
        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                textview.setText(bundle.getString("message"));
            }
        };




        Handler handler1 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle1 = msg.getData();
                textview1.setText(bundle1.getString("message"));
            }
        };
        Handler handler2 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle2 = msg.getData();
                textview2.setText(bundle2.getString("message"));
            }
        };
        Handler handler3 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle3 = msg.getData();
                textview3.setText(bundle3.getString("message"));
            }
        };
        Handler handler4 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle4 = msg.getData();
                textview4.setText(bundle4.getString("message"));
            }
        };
        Handler handler5 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle5 = msg.getData();
                textview5.setText(bundle5.getString("message"));
            }
        };

        //학사공지
        new Thread() {
            @Override
            public void run() {
                Document doc =null;
                String[] hyperlink = new String[5];
                Pattern[] Pattern = new Pattern[5];
                try{
                    doc = Jsoup.connect(url1).get();
                    Elements links = doc.select("a[href]");
                    Elements link = doc.select(".tit");
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




                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

        //버튼 연결 및 클릭시
        Button AcademicSchduleAdd = (Button) findViewById(R.id.AcademicSchduleAdd);
        AcademicSchduleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).get();
                            //2022.03~ 2023.02까지 학사일정
                            for(int k= 0; k<doc.select(".md_textcalendar").size(); k++) {
                                Elements cal = doc.select(".md_textcalendar").eq(k);
                                //년도
                                Elements Year = cal.select(".year");
                                //날짜
                                Elements Month = cal.select("ul dt");
                                //일정
                                Elements Scl = cal.select("ul dd");

                                for (int i = 0; i < Month.size(); i++) {
                                    //데베에 저장할땐 여기 부분 차례대로 속성마다 저장하면 될듯
                                    msg += Year.select(".year").text() + " " + Month.select("dt").eq(i).text() + "  " + Scl.select("dd").eq(i).text() + "\n";
                                }
                            }
                            bundle.putString("message", msg);
                            Message msg = handler.obtainMessage();
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}