package com.example.testqiniu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import android.widget.Toast;


import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private Button btnChoose;
    private Button btnUpload;
    private List<String> educations = new ArrayList<>(Arrays.asList("大专以下", "大专", "本科", "研究生及以上"));
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnChoose = findViewById(R.id.btn_choose);
        btnUpload = findViewById(R.id.btn_upload);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void create() {

        CardView cardView = new CardView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
        cardView.setRadius(16f);
        cardView.addView(linearLayout);
        for (String education : educations) {
            TextView textView = new TextView(this);
            textView.setText(education);
            textView.setTextSize(16f);
            textView.setOnClickListener(popupwindoiwClickListener); //点击选项的监听
            textView.setTextColor(Color.parseColor("#212121"));
            textView.setPadding(25, 25, 25, 25);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
        }
        popupWindow = new PopupWindow(cardView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.9f;
        getWindow().setAttributes(attributes);

        popupWindow.showAtLocation(btnUpload,Gravity.BOTTOM,0,0);
        //popupWindow.showAsDropDown(, -10, 20);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                attributes.alpha = 1.0f;
                getWindow().setAttributes(attributes);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100001 && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
        }
    }


    /**
     * popupwindow 中选项的点击事件
     */
    View.OnClickListener popupwindoiwClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String education = ((TextView) view).getText().toString();
            Toast.makeText(Main2Activity.this, ""+education, Toast.LENGTH_SHORT).show();
        }
    };
}
