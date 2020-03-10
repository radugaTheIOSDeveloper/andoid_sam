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

public class RegistActivity extends AppCompatActivity {


    private Button enterReg;
    private Button registrBtn;
    private Button licenseBtn;

    public EditText phoneReg;
    public EditText passReg;
    SharedPreferences sharedPreferencesInfo;

    private ProgressBar progressBar;
    String finalString;

    String strreg;
    public static final String LOG_TAG = "authTag = ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        progressBar = findViewById(R.id.progressBarRegVerification);

        enterReg = findViewById(R.id.enterReg);
        registrBtn = findViewById(R.id.registrBtn);
        licenseBtn = findViewById(R.id.licenseBtn);

        phoneReg = findViewById(R.id.phoneReg);


        enterReg.setTransformationMethod(null);
        registrBtn.setTransformationMethod(null);
        licenseBtn.setTransformationMethod(null);


        Slot[] slots = new PhoneNumberUnderscoreSlotsParser().parseSlots("7 (___) ___-____");
        final Mask inputMask = MaskImpl.createTerminated(slots);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(phoneReg);
        strreg = "7";

//
//        phoneReg.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                phoneReg.setText(strreg);
//            } });

        phoneReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phoneReg.setText(strreg);

                } } });



//        phoneReg.setOnKeyListener(new View.OnKeyListener()
//                                       {
//
//                                           public boolean onKey(View v, int keyCode, KeyEvent event)
//                                           {
//
//
//                                               if(event.getAction() == KeyEvent.ACTION_DOWN &&
//                                                       (keyCode == KeyEvent.KEYCODE_ENTER))
//                                               {
//                                                   strreg = phoneReg.getText().toString();
//                                                   inputMask.insertFront(phoneReg.getText());
//                                                   Log.d(LOG_TAG, inputMask.toUnformattedString());
//                                                   finalString = inputMask.toUnformattedString();
//                                                   InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                   in.hideSoftInputFromWindow(phoneReg.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//
//                                                   return true;
//                                               }
//                                               return false;
//                                           }
//                                       }
//        );




        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);



        registrBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                progressBar.setVisibility(ProgressBar.VISIBLE);


                strreg = phoneReg.getText().toString();
                inputMask.insertFront(phoneReg.getText());
                Log.d(LOG_TAG, inputMask.toUnformattedString());
                finalString = inputMask.toUnformattedString();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(phoneReg.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                new ItemTask().execute();


            }
        });


        enterReg.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });


        licenseBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                Intent intent = new Intent(RegistActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<RegistrItem>> {




        @Override
        protected List<RegistrItem> doInBackground(Void... voids) {

            return   new RegistrApi().registrItems(finalString);

        }


        @Override
        protected void onPostExecute(List<RegistrItem> registrItems) {
            super.onPostExecute(registrItems);



            String msg = registrItems.get(0).getMessages();

            Log.d(LOG_TAG, "msg = " + msg);

            if (msg.equals("ok")){
                Intent intent = new Intent(RegistActivity.this, RegistrVerification.class);
                intent.putExtra("phone", finalString);
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
