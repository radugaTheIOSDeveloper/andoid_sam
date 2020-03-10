package ru.pomoysam.itstack;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    SharedPreferences sPref;

    TextView rw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        sPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);


        rw = findViewById(R.id.textView6);

        rw.setText("sp = " + sPref.getAll());

    }
}
