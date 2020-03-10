package ru.pomoysam.itstack;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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

public class RecoveryActivity extends AppCompatActivity {

    private Button recoveryButton;
    public EditText phoneRecovery;

    private ProgressBar progressBar;

    String strRecovery;
    String finalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);


        recoveryButton = findViewById(R.id.recoveryButton);

        phoneRecovery = findViewById(R.id.phoneRecovery);

        progressBar = findViewById(R.id.recoveryProgress);


        Slot[] slots = new PhoneNumberUnderscoreSlotsParser().parseSlots("7 (___) ___-____");
        final Mask inputMask = MaskImpl.createTerminated(slots);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(phoneRecovery);
        strRecovery = "7";


//        phoneRecovery.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                phoneRecovery.setText(strRecovery);
//            } });

        phoneRecovery.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phoneRecovery.setText(strRecovery);

                } } });



//        phoneRecovery.setOnKeyListener(new View.OnKeyListener()
//                                  {
//
//                                      public boolean onKey(View v, int keyCode, KeyEvent event)
//                                      {
//
//
//                                          if(event.getAction() == KeyEvent.ACTION_DOWN &&
//                                                  (keyCode == KeyEvent.KEYCODE_ENTER))
//                                          {
//                                              strRecovery = phoneRecovery.getText().toString();
//                                              inputMask.insertFront(phoneRecovery.getText());
//                                              Log.d("log", inputMask.toUnformattedString());
//                                              finalString = inputMask.toUnformattedString();
//                                              InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                              in.hideSoftInputFromWindow(phoneRecovery.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//
//                                              return true;
//                                          }
//                                          return false;
//                                      }
//                                  }
//        );

        recoveryButton.setTransformationMethod(null);


        final Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final int color = getResources().getColor(R.color.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);



        recoveryButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);
                progressBar.setVisibility(ProgressBar.VISIBLE);


                strRecovery = phoneRecovery.getText().toString();
                inputMask.insertFront(phoneRecovery.getText());
                Log.d("log", inputMask.toUnformattedString());
                finalString = inputMask.toUnformattedString();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(phoneRecovery.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                new ItemTask().execute();

//                Intent intent = new Intent(RecoveryActivity.this, RecoverySMS.class);
//                startActivity(intent);
            }
        });


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<RecoveryItem>> {




        @Override
        protected List<RecoveryItem> doInBackground(Void... voids) {

            return   new RecoveryApi().recoveryItems(finalString);

        }


        @Override
        protected void onPostExecute(List<RecoveryItem> recoveryItems) {
            super.onPostExecute(recoveryItems);



            String msg = recoveryItems.get(0).getMessages();


            if (msg.equals("ok")){
                Intent intent = new Intent(RecoveryActivity.this, RecoverySMS.class);
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
