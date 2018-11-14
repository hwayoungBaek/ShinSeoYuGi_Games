package com.example.hwayoung.shinseoyugi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GameIdiom extends Activity {

    ConstraintLayout layout_game_start;
    TextView tv_randomIdiom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_idiom);

        layout_game_start = (ConstraintLayout)findViewById(R.id.layout_game_start);
        tv_randomIdiom = (TextView)findViewById(R.id.tv_randomIdiom);

    }

    public void onBtnGameIdiomStartClicked(View v){

        layout_game_start.setVisibility(View.INVISIBLE); // 게임 스타트 화면 사라지도록
        startGame();
    }

    public void startGame(){
        tv_randomIdiom.setText(readText(3));
    }

    public String readText(int _randLine){
        String data=null;
        // /res/raw 위치에 있는 데이터는 컴파일하지 않음.
        InputStream inputStream = getResources().openRawResource(R.raw.idiom_data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try{
            i = inputStream.read();
            // while문을 돌며 InputStream의 내용을 ByteArrayoutputStream에 저장
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            // ByteArrayoutputStream에 저장한 데이터를 MS949를 이용해 변환
            // -> 문자가 깨지는 것을 방지
            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
