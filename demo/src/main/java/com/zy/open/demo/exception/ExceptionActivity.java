package com.zy.open.demo.exception;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.zy.open.demo.R;

public class ExceptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
    }

    public void makeException(View view) {
        int[] array = new int[5];

        for (int i = 0; i < 6; i++) {
            System.out.println(array[i]);
        }
    }
}
