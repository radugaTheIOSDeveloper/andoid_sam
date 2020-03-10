package ru.pomoysam.itstack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

public class RegistrVerification extends AppCompatActivity {

    private Button regVerification;
    EditText textVerification;
    SharedPreferences sharedPreferencesInfo;
    private ProgressBar progressBar;
    public static final String LOG_TAG = "regVerification = ";
    String phonePut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr_verification);

        regVerification = findViewById(R.id.BtnRegVerification);
        textVerification = findViewById(R.id.regSms);
        progressBar = findViewById(R.id.progressBarRegVerification);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        Intent intent = getIntent();

        phonePut = intent.getStringExtra("phone");

        regVerification.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(ProgressBar.VISIBLE);

                new ItemTask().execute();

            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<RegistrVerificationItem>> {




        @Override
        protected List<RegistrVerificationItem> doInBackground(Void... voids) {

            return   new RegistrVirificationApi().registrVerificationItems(phonePut, textVerification.getText().toString());

        }


        @Override
        protected void onPostExecute(List<RegistrVerificationItem> registrVerificationItems) {
            super.onPostExecute(registrVerificationItems);



            String msg = registrVerificationItems.get(0).getMessages();

            Log.d(LOG_TAG, "msg = " + msg);

            if (msg.equals("ok")){
                Intent intent = new Intent(RegistrVerification.this, RegistrationFull.class);
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

            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }



}
