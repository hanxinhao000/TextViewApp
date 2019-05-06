package com.example.a15002.textviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SlidingTextView slidingTextView;

    private Button btn;

    private Button seep;

    private Button low;

    private TextView ms;

    private int seepl = 1000;

    private int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        slidingTextView = findViewById(R.id.slidingTextView);
        ms = findViewById(R.id.ms);
        seep = findViewById(R.id.seep);
        low = findViewById(R.id.low);
        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("测试专用");
        arrayList.add("ABCD");
        arrayList.add("abcd");
        arrayList.add("0123456789");
        arrayList.add("你好");
        arrayList.add("我是你们的朋友");
        arrayList.add("XINHAO_HAN");
        arrayList.add("哈哈哈哈~~~~~");
        arrayList.add("啦啦啦啦~~~~~");
        arrayList.add("测试超长文本，大兄弟？？？？你知道吗？看我长不长");

        seep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seepl += seepl;
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seepl > 1000) {
                    seepl -= 1000;
                } else {
                    /**
                     *
                     * 实际项目当中，多少ms,请随意
                     *
                     */
                    Toast.makeText(MainActivity.this, "不得少于1000ms", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                temp = seepl;

                while (true) {

                    for (int i = 0; i < arrayList.size(); i++) {

                        Random random = new Random();

                        final int i123 = random.nextInt(2000);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        temp -= 1000;
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (temp == 0 || temp < 0) {
                                    slidingTextView.setText(arrayList.get(finalI) + i123);
                                    slidingTextView.accAnimation();
                                    temp = seepl;
                                }
                                ms.setText("距离下次滚动还有:" + temp + "ms");
                            }
                        });


                    }


                }

            }
        }).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "点击？？？", Toast.LENGTH_SHORT).show();
                slidingTextView.accAnimation();
            }
        });
    }
}
