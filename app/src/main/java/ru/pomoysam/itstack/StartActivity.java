package ru.pomoysam.itstack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;


public class StartActivity extends AppCompatActivity {

    ImageView imageLogo;
    SharedPreferences sPref;
    SharedPreferences sharedPreferencesInfo;

    String statusToken;
    String statusStart;
    String savedText;
    String pushToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        sPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        savedText = sPref.getString("token", "");
        statusToken = sPref.getString("status","");
        pushToken = sPref.getString("push_token","");


        sharedPreferencesInfo = getSharedPreferences("pref",MODE_PRIVATE);
        statusStart = sharedPreferencesInfo.getString("statusStart","");


        Log.d("123","token = " +sPref.getAll());

        imageLogo = findViewById(R.id.startLogo);

//        if (ActivityCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//            Log.d("LOG TAG" , "if");
//            return;
//
//
//        }else{
//
//
//            Log.d("LOG TAG" , "else");
//
//        }

        startAnimation();



    }


    void  startAnimation(){
        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.logo);


        imageLogo.startAnimation(animationAlpha);

        animationAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {





                if (statusStart == null|| statusStart.equals("")){

                    Intent intent = new Intent(StartActivity.this, StartActivityOne.class);
                    startActivity(intent);


                }else {

                    if (savedText.equals("")|| savedText == null){


                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }else {

                        Intent intent = new Intent(StartActivity.this, NavigationActivity.class);
                        startActivity(intent);

                    }



                }




            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }




}
