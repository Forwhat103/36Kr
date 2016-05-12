package com.zhuxiaoming.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Person<Hanxu> person = new Person<>();
        Hanxu hanxu = new Hanxu();
        hanxu.setEges("大");
        person.setEges(hanxu);
        Log.d("dddddddd", person.getEges().getEges()+ "");
    }
}
