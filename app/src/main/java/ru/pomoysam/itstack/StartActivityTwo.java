package ru.pomoysam.itstack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartActivityTwo extends AppCompatActivity {


    Button close;
    Button next;
    SharedPreferences sharedPreferencesInfo;
    String statusToken;
    String savedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_two);


        close = findViewById(R.id.btnClose);
        next = findViewById(R.id.btnNext);





        sharedPreferencesInfo = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        statusToken = sharedPreferencesInfo.getString("status","");

        savedText = sharedPreferencesInfo.getString("token", "");


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (savedText == null|| savedText.equals("")){


                    Intent intent = new Intent(StartActivityTwo.this, LoginActivity.class);
                    startActivity(intent);


                }else {
                    Intent intent = new Intent(StartActivityTwo.this, NavigationActivity.class);
                    startActivity(intent);

                }



            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedText == null|| savedText.equals("")){


                    Intent intent = new Intent(StartActivityTwo.this, LoginActivity.class);
                    startActivity(intent);


                }else {
                    Intent intent = new Intent(StartActivityTwo.this, NavigationActivity.class);
                    startActivity(intent);

                }

            }
        });



    }
}
