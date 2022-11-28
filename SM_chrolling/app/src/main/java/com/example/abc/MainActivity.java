package com.example.abc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat mformat = new SimpleDateFormat("MM");
    String getTime = mformat.format(mDate);
    int getMonth = Integer.parseInt(getTime);
    TextView textview, textview1;
    String url = "https://www.syu.ac.kr/academic/major-schedule/";
    String msg = "";
    String msg1 = "";
    String msg2 = "";
    final Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.textview);
        textview1 = (TextView) findViewById(R.id.textview1);
        msg1="이번달은 일정이 없습니다";

        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                textview.setText(bundle.getString("message"));
            }
        };

        new Thread() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    Elements d1 = doc.select(".md_textcalendar").eq(10);
                    Elements d2 = d1.select("ul dt");
                    Elements d3 = d1.select("ul dd");
                    Elements d4 = d1.select(".year");
                    for(int i= 0; i<d3.size(); i++) {
                        msg += d4.select(".year").text() + " "+ d2.select("dt").eq(i).text() + "  "+ d3.select("dd").eq(i).text() + "\n";
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
}