package com.example.timechoose;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WheelView wheelView = findViewById(R.id.wheelview);
        wheelView.setCyclic(false);

        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("item0");
        mOptionsItems.add("item1");
        mOptionsItems.add("item2");

        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(MainActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void click1(View view) {
        TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(MainActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        }).build();

        pvTime.show();
    }

    public void click2(View view) {
        TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(MainActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .isCyclic(true)//是否循环滚动
                .setSubmitColor(Color.parseColor("#ff2c4d"))//确定按钮文字颜色
                .setCancelColor(Color.RED)//取消按钮文字颜色
                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                //z.setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                //.setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                //.setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        pvTime.show();

    }
}
