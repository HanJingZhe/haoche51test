package com.example.calender;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CalendarView.OnCalendarSelectListener {

    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private TextView tvMonthAndDay;
    private TextView tvYear;
    private TextView tvDayNum;
    private CalendarLayout mCalendarLayout;
    private ImageView ivDayNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMonthAndDay = findViewById(R.id.tv_monthandday);
        tvYear = findViewById(R.id.tv_year);
        tvDayNum = findViewById(R.id.tv_daynum);
        ivDayNum = findViewById(R.id.iv_daynum);

        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView = findViewById(R.id.calendarView);
        mRecyclerView = findViewById(R.id.recyclerView);

        //赋值
        tvDayNum.setText(String.valueOf(mCalendarView.getCurDay()));
        tvMonthAndDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");

        //监听
        ivDayNum.setOnClickListener(this);
        tvDayNum.setOnClickListener(this);

        mCalendarView.setOnCalendarSelectListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_daynum:
            case R.id.tv_daynum:
                mCalendarView.scrollToCurrent();
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvMonthAndDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvYear.setText(String.valueOf(calendar.getYear()));
    }

    public void tiao(View view){
        startActivity(new Intent(this,Main2Activity.class));
    }
}
