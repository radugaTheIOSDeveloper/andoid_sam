package ru.pomoysam.itstack;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class PaymentCardBind extends AppCompatActivity {


    ListView lv;
    ArrayList<PaymentCardItem> paymentCardItems = new ArrayList<PaymentCardItem>();
    PaymentListCardAdapter paymentListCardAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card_bind);


        lv = findViewById(R.id.listPayment);




        paymentCardItems.add(new PaymentCardItem("3333 3321 1233 1233",1));

        paymentCardItems.add(new PaymentCardItem("**** *** **33 4542",1));

        paymentCardItems.add(new PaymentCardItem("**** *** **33 5432",1));


        paymentListCardAdapter = new PaymentListCardAdapter(PaymentCardBind.this, paymentCardItems);

        lv.setAdapter(paymentListCardAdapter);



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);

                return true;
            }
        });





        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("token", "123124§24125152");
        ed.commit();





    }
}
