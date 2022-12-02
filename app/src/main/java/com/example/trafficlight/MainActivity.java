package com.example.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trafficlight.databinding.ActivityMainBinding;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private LinearLayout bulb_1, bulb_2, bulb_3;
    private Button button;
    private boolean start_stop = false;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bulb_1 = binding.bulb1;
        bulb_2 = binding.bulb2;
        bulb_3 = binding.bulb3;
        button = binding.button1;
    }

    public void onClickStart(View view) {
        start_stop = !start_stop;
        if (start_stop) {
            button.setText(R.string.stop);
            new Thread(this::run).start();
        } else {
            button.setText(R.string.start);
        }

    }

    private void run() {
        while (start_stop) {
            counter = counter < 3 ? counter + 1 : 1;
            switch (counter) {
                case 1:
                    bulb_1.setBackgroundColor(getResources().getColor(R.color.green));
                    bulb_3.setBackgroundColor(getResources().getColor(R.color.gray));
                    break;
                case 2:
                    bulb_2.setBackgroundColor(getResources().getColor(R.color.yellow));
                    bulb_1.setBackgroundColor(getResources().getColor(R.color.gray));
                    break;
                case 3:
                    bulb_3.setBackgroundColor(getResources().getColor(R.color.red));
                    bulb_2.setBackgroundColor(getResources().getColor(R.color.gray));
                    break;
            }
            for (int i = 0; i < 500; i++) {
                if (!start_stop) {
                    setGray();
                    break;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setGray() {
        bulb_1.setBackgroundColor(getResources().getColor(R.color.gray));
        bulb_2.setBackgroundColor(getResources().getColor(R.color.gray));
        bulb_3.setBackgroundColor(getResources().getColor(R.color.gray));
        counter = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start_stop = false;
    }
}