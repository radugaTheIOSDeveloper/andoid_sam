package ru.pomoysam.itstack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

public class NewPasswordActivity extends AppCompatActivity {


    private Button btnNewPass;
    public EditText passNew;

    private ProgressBar newProgress;
    String phonePut;
    SharedPreferences sharedPreferencesInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        btnNewPass = findViewById(R.id.btnNewPass);

        passNew = findViewById(R.id.passNew);

        newProgress = findViewById(R.id.newProgress);


        btnNewPass.setTransformationMethod(null);
        Intent intent = getIntent();

        phonePut = intent.getStringExtra("phone");

        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        newProgress.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        newProgress.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        btnNewPass.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);
                newProgress.setVisibility(ProgressBar.VISIBLE);

                 Log.d("LOGTAG" , "str = " + phonePut +" pass = " + passNew.getText().toString());

                new ItemTask().execute();
//
//                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<NewPasswordItem>> {




        @Override
        protected List<NewPasswordItem> doInBackground(Void... voids) {

            return   new NewPasswordApi().newPasswordItems(phonePut,passNew.getText().toString());

        }


        @Override
        protected void onPostExecute(List<NewPasswordItem> newPasswordItems) {
            super.onPostExecute(newPasswordItems);

            String msg = newPasswordItems.get(0).getMessages();

            if (msg.equals("bads")){

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка востановления пароля",
                        Toast.LENGTH_SHORT);
                toast.show();


            } else {

                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
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

            newProgress.setVisibility(ProgressBar.INVISIBLE);

        }
    }


}
