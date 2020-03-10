package ru.pomoysam.itstack;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentGood extends AppCompatActivity {

    Button btn;
    TextView siztCoins;

    TextView tw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_good);

        btn = findViewById(R.id.backPaymentGood);
        siztCoins = findViewById(R.id.twopayGood);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.tool_bar);
        tw = findViewById(R.id.mytext);
        tw.setText("Оплата");


        Intent intent = getIntent();

        String size = intent.getStringExtra("sizecnt");
        String min = intent.getStringExtra("minuts");

        Log.d("LOG_TAGG", min);

        siztCoins.setText(size);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(PaymentGood.this, NavigationActivity.class);
                intent.putExtra("screen","true");

                startActivity(intent);


            }
        });

    }
}
