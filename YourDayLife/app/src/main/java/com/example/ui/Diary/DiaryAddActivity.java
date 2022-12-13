package com.example.ui.Diary;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.DB.Model.DiaryData;
import com.example.ui.DB.RoomDB;
import com.example.ui.Module.CustomTime;
import com.example.ui.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DiaryAddActivity extends AppCompatActivity {
    /*선언*/
    //날짜
    TextView dateDiary, textDiary;
    int diaryY, diaryM, diaryD;
    //버튼
    Button diaryCancle, diaryAdd, imageAdd;

    String date = CustomTime.getTodayToString(); //이미지 파일명을 작성일로 저장 (yyyy-MM-dd)

    RoomDB database;

    //이미지뷰
    ImageView diaryImageView;



    public DiaryAddActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = RoomDB.getInstance(DiaryAddActivity.this);
        setContentView(R.layout.dialog_diaryadd);

        textDiary = findViewById(R.id.text_diary);
        diaryImageView = findViewById(R.id.addImage_diary);

        //이미 작성한 내용이 있으면, 기존 내용 불러오기
        DiaryData diaryData = database.diaryDAO().getCreateDate(date);
        if( diaryData != null ) {
            Toast.makeText(getApplicationContext(), "기존의 일기를 수정합니다", Toast.LENGTH_SHORT).show();
            textDiary.setText(diaryData.getContent());
            try {
                String imgpath = getCacheDir() + "/" + date;   // 내부 저장소에 저장되어 있는 이미지 경로
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                diaryImageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            } catch (Exception e) {
            }
        } else {
            diaryImageView.setImageBitmap(null);
            textDiary.setText("");
        }

        imageAdd = findViewById(R.id.btn_imageadd);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });

        /*날짜 표시*/
        //날짜표시 기능구현
        dateDiary = findViewById(R.id.date_diary);
        Calendar cal = new GregorianCalendar();
        diaryY = cal.get(Calendar.YEAR);
        diaryM = cal.get(Calendar.MONTH)+1;
        diaryD = cal.get(Calendar.DAY_OF_MONTH);
        dateDiary.setText(diaryY + "-" + diaryM + "-" + diaryD);
        //날짜수정 기능구현
        dateDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(DiaryAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        date = year + "-" + month + "-" + dayOfMonth;
                        dateDiary.setText(date);
                        diaryY = year;
                        diaryM = month;
                        diaryD = dayOfMonth;

                        //이미 작성한 내용이 있으면, 기존 내용 불러오기
                        DiaryData diaryData = database.diaryDAO().getCreateDate(date);
                        if( diaryData != null ) {
                            Toast.makeText(getApplicationContext(), "기존의 일기를 수정합니다", Toast.LENGTH_SHORT).show();
                            textDiary.setText(diaryData.getContent());
                            try {
                                String imgpath = getCacheDir() + "/" + date;   // 내부 저장소에 저장되어 있는 이미지 경로
                                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                                diaryImageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
                            } catch (Exception e) {
                            }
                        } else {
                            diaryImageView.setImageBitmap(null);
                            textDiary.setText("");
                        }

                    }
                }, diaryY, diaryM-1, diaryD);
                dpd.show();
            }
        });

        /*버튼*/
        //취소
        diaryCancle = findViewById(R.id.btn_diaryCancle);
        diaryCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //추가
        diaryAdd = findViewById(R.id.btn_diaryAdd);
        diaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(textDiary.getText()).equals("")) {
                    Toast.makeText(getApplicationContext(),"일기 내용을 입력해주세요!", Toast.LENGTH_LONG).show();
                } else {
                    DiaryData diaryData = new DiaryData();
                    diaryData.setCreateDate(date);
                    diaryData.setImgSrc(date);
                    diaryData.setContent(String.valueOf(textDiary.getText()));
                    database.diaryDAO().insert(diaryData);
                    Toast.makeText(getApplicationContext(),"정상적으로 작성 되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }


    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getCacheDir(), date);    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    diaryImageView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    instream.close();   // 스트림 닫아주기
                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
