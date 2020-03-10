package ru.pomoysam.itstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

import ru.tinkoff.decoro.Mask;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.PhoneNumberUnderscoreSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class LoginActivity extends AppCompatActivity  {

    private Button btnEnter;
    private Button btnRegist;
    private Button forgotPass;

    public EditText passwordEditText;
    public EditText phoneEditText;

    private ProgressBar progressBar;
    String str;

    String finalString;
    SharedPreferences sharedPreferencesInfo;
    SharedPreferences sharedPrefStatus;
    SharedPreferences sPref;

    Mask inputMask;

    public static final String LOG_TAG = "authTag = ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




        sharedPrefStatus = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPrefStatus.edit();
        ed.putString("statusStart", "true");
        ed.commit();



        progressBar = findViewById(R.id.progressBar);

        btnEnter = findViewById(R.id.enterBtn);
        btnRegist = findViewById(R.id.registrBtn);
        forgotPass = findViewById(R.id.forgotPass);

        passwordEditText = findViewById(R.id.passReg);
        phoneEditText = findViewById(R.id.phoneReg);



        Slot[] slots = new PhoneNumberUnderscoreSlotsParser().parseSlots("7 (___) ___-____");
        inputMask = MaskImpl.createTerminated(slots);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(phoneEditText);
        str = "7";

//        phoneEditText.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                phoneEditText.setText(str);
//            } });

        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phoneEditText.setText(str);

                } } });


        btnEnter.setTransformationMethod(null);
        btnRegist.setTransformationMethod(null);
        forgotPass.setTransformationMethod(null);



//        phoneEditText.setOnKeyListener(new View.OnKeyListener()
//                                {
//
//                                    public boolean onKey(View v, int keyCode, KeyEvent event)
//                                    {
//
//
//                                        if(event.getAction() == KeyEvent.ACTION_DOWN &&
//                                                (keyCode == KeyEvent.KEYCODE_ENTER))
//                                        {
//
//                                            passwordEditText.requestFocus();
//
//                                            return true;
//                                        }
//                                        return false;
//                                    }
//                                }
//        );



        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);





        btnEnter.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                progressBar.setVisibility(ProgressBar.VISIBLE);
//
//                if (phoneEditText.getText().toString().length() <= 10 || passwordEditText.getText().toString().length() <= 6){
//
//                    Toast toast = Toast.makeText(getApplicationContext(),
//                            "Некорректно введенные данные",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                } else  {
//
//
//                    new ItemTask().execute();
//
//                }

                str = phoneEditText.getText().toString();
                inputMask.insertFront(phoneEditText.getText());
                finalString = inputMask.toUnformattedString();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(phoneEditText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                Log.d(LOG_TAG, finalString + " finals string");

                new ItemTask().execute();



            }
        });


        btnRegist.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);


            }
        });


        forgotPass.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);
                Intent intent = new Intent(LoginActivity.this, RecoveryActivity.class);
                startActivity(intent);

            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void , List<AuthItem>> {




        @Override
        protected List<AuthItem> doInBackground(Void... voids) {

            return   new AuthApi().authItems(finalString, passwordEditText.getText().toString());

        }


        @Override
        protected void onPostExecute(List<AuthItem> authItems) {
            super.onPostExecute(authItems);


            String msg = authItems.get(0).getToken();

            if (msg.equals("bads")){

                authItems.clear();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Неверное имя пользователя или пароль.",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {

                   Log.d(LOG_TAG, "12312 " +authItems.get(0).getToken());


                sPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("token", authItems.get(0).getToken());
                ed.putString("status", "true");
                ed.commit();

//
//                sharedPreferencesInfo = getSharedPreferences("pref",MODE_PRIVATE);
//                SharedPreferences.Editor ed = sharedPreferencesInfo.edit();
//                ed.putString("status", "true");
//                ed.putString("token", authItems.get(0).getToken());
//                ed.commit();

                Log.d(LOG_TAG, "shared all = " + sPref.getAll());


                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                startActivity(intent);
            }

            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }






}


