package com.example.hwayoung.shinseoyugi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GameIdiom extends Activity {

    ConstraintLayout layout_game_start;
    TextView tv_randomIdiom;

    String data[] = new String[20]; // 사자성어 데이터
    int visited[] = new int[20];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_idiom);

        layout_game_start = (ConstraintLayout)findViewById(R.id.layout_game_start);
        tv_randomIdiom = (TextView)findViewById(R.id.tv_randomIdiom);

        // 초기화 - 사자성어 text file 읽어놓기
        readText();
    }

    public void onBtnGameIdiomStartClicked(View v){

        layout_game_start.setVisibility(View.INVISIBLE); // 게임 스타트 화면 사라지도록
        startGame();
    }

    public void startGame(){
        int random_num =new Random().nextInt(18); // 0~18 +1 -> 1~19
        while(visited[random_num]!=0){
            // 이미 뽑혔던 숫자라면 다시뽑기
            random_num =new Random().nextInt(18);
        }
        visited[random_num]=1; // 방문표시
        tv_randomIdiom.setText(data[random_num]);
    }

    public void readText(){

        // /res/raw 위치에 있는 데이터는 컴파일하지 않음.
        InputStream inputStream = getResources().openRawResource(R.raw.idiom_data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        int cnt = 0;
        try{
            i = inputStream.read();
            // while문을 돌며 InputStream의 내용을 ByteArrayoutputStream에 저장
            while(i!=-1){
                cnt++;
                byteArrayOutputStream.write(i);

                if(cnt%8==0){ // 8byte 읽음 -> 한글 4글자
                    data[cnt/8] = new String(byteArrayOutputStream.toByteArray(),"MS949");
                    byteArrayOutputStream.reset(); // 이전에 읽어서 저장해놓은 stream 초기화
                }
                i = inputStream.read();
            }

            // ByteArrayoutputStream에 저장한 데이터를 MS949를 이용해 변환
            // -> 문자가 깨지는 것을 방지
            inputStream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
}
