package ru.pomoysam.itstack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class NavigationActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {




        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_sam:
                    tw.setText("Новости CАМ");
                    selectedFragment = new NewsFragment();
                    break;
                case R.id.question:
                    tw.setText("Обратная связь");

                    selectedFragment = new QuestionGroup();

                    break;
                case R.id.myBy:
                    tw.setText("Мои покупки");


                    selectedFragment = new MyByFragment();

                    break;
                case R.id.maps:
                    tw.setText("Карта");

                    if (ActivityCompat.checkSelfPermission(NavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(NavigationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(NavigationActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                1);
                    }
                    selectedFragment = new MapsFragment();

                    break;
                case R.id.instruction:
                    tw.setText("Как пользоваться");
                    selectedFragment = new InstructionFragment();

                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contayner,selectedFragment).commit();
            return true;
        }
    };


    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.tool_bar);
        tw = findViewById(R.id.mytext);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();


        if (intent.getStringExtra("screen") == null){

            tw.setText("Новости САМ");

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contayner,new NewsFragment()).commit();

        }else {

            tw.setText("Мойка САМ");
            navigation.setSelectedItemId(R.id.myBy);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contayner,new MyByFragment()).commit();

        }









    }


    @Override
    public void onBackPressed() {
    }


}
