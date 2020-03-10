package ru.pomoysam.itstack;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import android.support.annotation.NonNull;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.ColorScheme;
import ru.yandex.money.android.sdk.GooglePayCardNetwork;
import ru.yandex.money.android.sdk.GooglePayParameters;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.TestParameters;
import ru.yandex.money.android.sdk.TokenizationResult;
import ru.yandex.money.android.sdk.UiParameters;



public class PayActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_3DS = 34;
    private static final int REQUEST_CODE_TOKENIZE = 33;
    private BigDecimal amount = BigDecimal.ZERO;
    private  String token;
    Button btnPay;
    private String url3ds = "";
    Integer summs;
    Integer cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btnPay = findViewById(R.id.pay);

        Intent intent = getIntent();
        Integer summ = intent.getIntExtra("ammount", 0);
        summs = summ;
        amount = new BigDecimal(summ/50);
        cnt = intent.getIntExtra("cnt", 0);
        Log.d("LOG_TAG","decimal " + amount);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeToStartCheckout();

            }
        });



    }

    void timeToStartCheckout() {



        final Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();
        paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY);
        paymentMethodTypes.add(PaymentMethodType.BANK_CARD);

        final Set<GooglePayCardNetwork> allowedCardNetworks = new HashSet<>();
        allowedCardNetworks.add(GooglePayCardNetwork.MASTERCARD);
        allowedCardNetworks.add(GooglePayCardNetwork.VISA);
        //allowedCardNetworks.add(GooglePayCardNetwork.OTHER);


        PaymentParameters paymentParameters = new PaymentParameters(
                new Amount(amount, Currency.getInstance("RUB")),
                "Название товара",
                "Описание товара",
                "live_NjI5ODE47cquPSD7939_gasmXhbT0ccjlnABYEq9rHg",
                "629818",
                paymentMethodTypes,
                null,
                null,
                null,
                new GooglePayParameters(allowedCardNetworks)
        );

        UiParameters uiParameters = new UiParameters(true, new ColorScheme(Color.rgb(255, 0, 0)));
        Intent intent = Checkout.createTokenizeIntent(this, paymentParameters, new TestParameters(), uiParameters);

        startActivityForResult(intent, REQUEST_CODE_TOKENIZE);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            switch (resultCode) {
                case RESULT_OK:
                    show3dsAlertDialog("3ds прошел");

                    break;
                case RESULT_CANCELED:
                    show3dsAlertDialog("3ds был закрыт");
                    break;
                case Checkout.RESULT_ERROR:

                    show3dsAlertDialog("произошла какая то ошибка");

                    // во время 3ds произошла какая-то ошибка (нет соединения или что-то еще)
                    // более подробную информацию можно посмотреть в data
                    // data.getIntExtra(Checkout.EXTRA_ERROR_CODE) - код ошибки из WebViewClient.ERROR_* или Checkout.ERROR_NOT_HTTPS_URL
                    // data.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION) - описание ошибки (может отсутствовать)
                    // data.getStringExtra(Checkout.EXTRA_ERROR_FAILING_URL) - url по которому произошла ошибка (может отсутствовать)
                    break;
            }
        }


        if (requestCode == REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case RESULT_OK:
                    // successful tokenization
                    TokenizationResult result = Checkout.createTokenizationResult(data);

                    String getPaymentToken = result.getPaymentToken();
                    String getPaymentMethodType = result.getPaymentMethodType().name();
                    String component1 = result.component1();
                    String component2 = result.component1();

                    token = result.component1();
                    Log.d("LOG_TAG", "getPaymentToken = " + getPaymentToken +"\ngetPaymentMethodType = " + getPaymentMethodType + "\n component1 = " + component1 +
                            "\n component2 = " +component2);


                    new ItemTaskPyment().execute();


                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization

                    show3dsAlertDialog(getString(R.string.cancel_3ds));


                    break;
            }
        }
    }


    private void show3dsAlertDialog(@NonNull String message) {
        new AlertDialog.Builder(this, R.style.DialogToken)
                .setMessage(message)
                .setPositiveButton(R.string.token_cancel, (dialog, which) -> { })
                .show();
    }




    void timeToStart3DS(String url) {
        Intent intent = Checkout.create3dsIntent(
                this,
                url
        );
        startActivityForResult(intent, 1);
    }




    private void open3dsConfirmation() {
        if (URLUtil.isHttpsUrl(url3ds) || URLUtil.isAssetUrl(url3ds)) {
            Intent intent = Checkout.create3dsIntent(this, url3ds);
            startActivityForResult(intent, REQUEST_CODE_3DS);
        } else {
            Toast.makeText(this, getString(R.string.error_wrong_url), Toast.LENGTH_SHORT).show();
        }
    }





    public  class ItemTaskPyment extends AsyncTask<Void, Void, PaymentItem> {


        @Override
        protected PaymentItem doInBackground(Void... voids) {


            return  new PaymentApi().paymentItem(token);
        }

        @Override
        protected void onPostExecute(PaymentItem paymentItem) {
            super.onPostExecute(paymentItem);

            timeToStart3DS(paymentItem.getConfUrl());


        }
    }



    //let params: NSDictionary = ["app_token": token.paymentToken, "user_token": self.tokenUser, "coins_cnt": String(self.cntCoin), "summ": String(self.rubles)]




    public class PaymentApi {

        public static final String LOG_TAG = "REGISTR_FULL ";
        private OkHttpClient client = new OkHttpClient();


        String token;



        public String getJSONString (String UrlSpec) throws IOException {


            FormBody body = new FormBody.Builder()
                    .add("app_token", token)
                    .add("user_token", "Token 82e9a798f760c96f230f0e59c48e3a314a28bd78")
                    .add("coins_cnt", cnt.toString())
                    .add("summ", summs.toString())


                    .build();


            Request request = new Request.Builder()
                    .url(UrlSpec)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String result = response.body().string();
            return result;

        }


        public PaymentItem paymentItem(String _token){

            PaymentItem paymentItem = new PaymentItem();
            token = _token;


            try {
                String url = Uri.parse("https://app.pomoysam.ru/api/v0/payment/")
                        .buildUpon()
                        //        .appendQueryParameter("format", "json")
                        .build().toString();

                String jsonString = getJSONString(url);
                //JSONObject jsonBody = new JSONObject(jsonString);

                parseItems(paymentItem, jsonString);

            } catch (IOException ioe){
                Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);

            }catch (JSONException joe){
                Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);


            }
            return paymentItem;

        }

        private void parseItems (PaymentItem items , String jsonString) throws IOException, JSONException {

            Log.d(LOG_TAG , "jsonBody - " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            items.setConfUrl(jsonBody.getString("confirmation_url"));

        }


    }




}
