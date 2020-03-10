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

public class RegistrationFull extends AppCompatActivity {

    private Button regFull;
    EditText textCode;
    SharedPreferences sharedPreferencesInfo;
    private ProgressBar progressBar;
    public static final String LOG_TAG = "regVerification = ";
    String phonePut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_full);

        regFull = findViewById(R.id.BtnRegFull);
        textCode = findViewById(R.id.regFull);
        progressBar = findViewById(R.id.progressFull);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        phonePut = intent.getStringExtra("phone");

        regFull.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(ProgressBar.VISIBLE);

                new ItemTask().execute();
            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<RegistrationFullItem>> {



        @Override
        protected List<RegistrationFullItem> doInBackground(Void... voids) {

            return   new RegistrationFullApi().registrationFullItems(phonePut, textCode.getText().toString());

        }


        @Override
        protected void onPostExecute(List<RegistrationFullItem> registrationFullItems) {
            super.onPostExecute(registrationFullItems);



            String msg = registrationFullItems.get(0).getMessages();

            Log.d(LOG_TAG, "msg = " + msg);

            if (msg.equals("bads")){

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка",
                        Toast.LENGTH_SHORT);
                toast.show();


            } else {

                sharedPreferencesInfo =   getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferencesInfo.edit();
                ed.putString("status", "true");
                ed.putString("token", registrationFullItems.get(0).getToken());
                ed.commit();




                Intent intent = new Intent(RegistrationFull.this, NavigationActivity.class);
                startActivity(intent);
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
