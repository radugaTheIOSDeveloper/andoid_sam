package ru.pomoysam.itstack;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

public class RecoverySMS extends AppCompatActivity {

    private Button smsBtn;
    public EditText smsRecovery;

    private ProgressBar smsProgress;
    String strRecovery;
    String finalString;
    String phonePut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_sms);

        smsBtn = findViewById(R.id.smsBtn);

        smsRecovery = findViewById(R.id.smsRecovery);

        smsProgress = findViewById(R.id.smsProgress);



        Intent intent = getIntent();

        phonePut = intent.getStringExtra("phone");


        smsBtn.setTransformationMethod(null);


        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        smsProgress.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        smsProgress.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);



        smsBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);
                smsProgress.setVisibility(ProgressBar.VISIBLE);

                new ItemTask().execute();
            }
        });

    }


    public  class ItemTask extends AsyncTask<Void, Void, List<RecoverySmsItem>> {




        @Override
        protected List<RecoverySmsItem> doInBackground(Void... voids) {

            return   new RecoverySmsApi().recoverySmsItems(phonePut,smsRecovery.getText().toString());

        }


        @Override
        protected void onPostExecute(List<RecoverySmsItem> recoverySmsItems) {
            super.onPostExecute(recoverySmsItems);



            String msg = recoverySmsItems.get(0).getMessages();


            if (msg.equals("ok")){
                Intent intent = new Intent(RecoverySMS.this, NewPasswordActivity.class);
                intent.putExtra("phone", phonePut);
                startActivity(intent);

            } else {


                Toast toast = Toast.makeText(getApplicationContext(),
                        msg,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

//
//            if (msg.equals("bads")){
//
//                registrItems.clear();
//
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        "Неверное имя пользователя или пароль.",
//                        Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//
//                Log.d(LOG_TAG, "12312 " +registrItems.get(0).getToken());
//
//
//                sharedPreferencesInfo = getPreferences(MODE_PRIVATE);
//                SharedPreferences.Editor ed = sharedPreferencesInfo.edit();
//                ed.putString("status", "true");
//                ed.putString("token", registrItems.get(0).getToken());
//                ed.commit();
//

//            }

            smsProgress.setVisibility(ProgressBar.INVISIBLE);

        }
    }

}
