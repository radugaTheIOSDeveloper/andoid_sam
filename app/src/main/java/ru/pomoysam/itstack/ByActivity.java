package ru.pomoysam.itstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.ColorScheme;
import ru.yandex.money.android.sdk.GooglePayCardNetwork;
import ru.yandex.money.android.sdk.GooglePayParameters;
import ru.yandex.money.android.sdk.MockConfiguration;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.SavedBankCardPaymentParameters;
import ru.yandex.money.android.sdk.TestParameters;
import ru.yandex.money.android.sdk.TokenizationResult;
import ru.yandex.money.android.sdk.UiParameters;

public class ByActivity extends AppCompatActivity {

    public static final String LOG_TAG = "ByTag = ";


    Button plusTwo;
    Button minusTwo;
    String promoDiscount;
    TextView numTwo;
    TextView tw;
    Integer two;
    Integer four;

    Integer mTwo;
    Integer mFour;

    Integer tTime;
    Integer fTime;

    EditText promoCode;
    Button buy;

    TextView infoSumm;
    TextView infoBalls;

    CheckBox checkbox;

    Float sum;
    Float balls;
    Integer time;

    Integer min = 0;

    String promo;
    String tokenUser;
    String tokenApp;

    private ProgressBar progressBar;

    Integer percentCachBack;

    Float discount;

    String statusCard;



    //TODO save card parametrs

    String paiment_id;
    String textSaveCard;
    Button canceledSavedCard;

    // TODO payment variable and constant
    public static final int REQUEST_CODE_3DS = 34;
    private static final int REQUEST_CODE_TOKENIZE = 33;
    private BigDecimal amount = BigDecimal.ZERO;
    private String url3ds = "";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by);

        plusTwo = (Button)findViewById(R.id.plus2);
        minusTwo = (Button)findViewById(R.id.minus2);

        numTwo = (TextView) findViewById(R.id.num2);

        infoSumm = (TextView)findViewById(R.id.infoSumm);
        infoBalls = (TextView)findViewById(R.id.infoBulls);
        promoCode = findViewById(R.id.editTextBy);
        canceledSavedCard = findViewById(R.id.canceledSavedCard);
        buy = findViewById(R.id.bybtn);


        checkbox = findViewById(R.id.checkBox);



        promoDiscount = "";

        two = 0;
        four = 0;
        sum = 0f;
        balls = 0f;
        time = 0;

        mTwo = 0;
        mFour = 0;

        tTime = 0;
        fTime = 0;
        discount = 0f;







        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkbox.isChecked()){

                    checkbox.setText(textSaveCard);

                } else {
                    checkbox.setText(textSaveCard);

                }

            }
        });


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.tool_bar);
        tw = findViewById(R.id.mytext);
        tw.setText("Купить жетоны");


        getTextNumending(Float.valueOf(time),0);
        infoSumm.setText(sum.toString()+" рублей");
        infoBalls.setText(balls.toString()+ " баллов");

        numTwo.setText(two.toString());


        SharedPreferences pref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        tokenUser = pref.getString("token","");
        Log.d(LOG_TAG, "token = " + tokenUser);

        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(ProgressBar.INVISIBLE);



        canceledSavedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.VISIBLE);

                new CancelCard().execute();

            }
        });

        promoCode.addTextChangedListener(new TextWatcher(){

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

                // действия, когда вводится какой то текст
                // s - то, что вводится, для преобразования в строку - s.toString()


                if(count == 8){
                    promo = s.toString();
                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    new ItemTaskPromo().execute();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {



                // действия после того, как что то введено
                // editable - то, что введено. В строку - editable.toString()
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.d(LOG_TAG, "befor = "+ count);

                // действия перед тем, как что то введено
            }
        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sum > 0){

//                    Intent intent = new Intent(ByActivity.this, PayActivity.class);
//                    intent.putExtra("ammount",  sum);
//                    intent.putExtra("cnt", two);
//                    startActivity(intent);

//                    Intent intent = new Intent(ByActivity.this, PaymentCardBind.class);
//                    startActivity(intent);


                    amount = new BigDecimal(sum);

//
                    timeToStartCheckout();

                }else {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Укажите количество жетонов для совершения покупки.",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }
        });



        plusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (two >= 99){
                    two = 99;

                    numTwo.setText(two.toString());


                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Превышен лимит количества жетонов",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }else {

                    two = two +1;
                    mTwo = mTwo + 50;
                    tTime = tTime + 2;
                    time = tTime + fTime;
                    sum = Float.valueOf(mFour + mTwo);

                    sum =(sum - ((sum * discount)/100));

                    numTwo.setText(two.toString());
                    getTextNumending(Float.valueOf(time),0);
                    infoSumm.setText(sum.toString()+" рублей");
                    getTextNumending((sum * percentCachBack)/100 , 1);
                }


            }
        });





        minusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.d(LOG_TAG, "two minus =  " + two);

                if (two == 0 ){

                    two = 0;
                    numTwo.setText(two.toString());

                }else  {

                    two = two -1;
                    mTwo = mTwo - 50;
                    tTime = tTime -2;
                    time = tTime + fTime;
                    sum = Float.valueOf(mFour + mTwo);
                    sum =(sum - ((sum * discount)/100));
                    numTwo.setText(two.toString());
                    infoSumm.setText(sum.toString()+ " рублей");
                    getTextNumending(Float.valueOf(time),0);
                    getTextNumending((sum * percentCachBack)/100 , 1);

                }

            }
        });



        progressBar.setVisibility(ProgressBar.VISIBLE);

        new ItemTaskPercent().execute();

        new SavedCards().execute();



    }



    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }

        return super.dispatchTouchEvent(event);
    }



    void getTextNumending (Float _time, Integer index){


        if (index == 0){

            String[] array  = new String[] {"минута", "минуты", "минут"};


        }else if (index == 1){

            String[] array  = new String[] {"балл", "балла", "баллов"};

            infoBalls.setText((sum * percentCachBack)/100 + " " + getNumending(array, _time));

        }





    }


    String getNumending (String[] _array , Float _item) {
        String str;
        _item = _item % 100;

        if (_item>=11 && _item<=19) {
          str = _array[2];
        }
        else {
            int i = (int) (_item % 10);
            switch (i)
            {
                case (1): str = _array[0]; break;
                case (2): str = _array[1]; break;
                case (3): str = _array[1]; break;
                case (4): str = _array[1]; break;
                default: str=_array[2];
            }
        }

        return str;


    }




    public  class ItemTaskPromo extends AsyncTask<Void, Void, List<PromoItem>> {


        @Override
        protected List<PromoItem> doInBackground(Void... voids) {


            return  new PromoAPI().promoItems(tokenUser, promo);
        }

        @Override
        protected void onPostExecute(List<PromoItem > promoItems) {
            super.onPostExecute(promoItems);

            View v = getCurrentFocus();
            v.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            promoCode.setText("");
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (promoItems.get(0).getMessages() == null || promoItems.get(0).getMessages().equals("")){

                if (promoItems.get(0).getTypesPromo() == 1){

                    promoDiscount = promo;


                    discount = Float.valueOf(promoItems.get(0).getPercent());

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Вам начисленна скидка "+discount+" % на текущую покупку!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    sum =(sum - ((sum * discount)/100));
                    infoSumm.setText(sum.toString()+ " рублей");
                    getTextNumending((sum * percentCachBack)/100 , 1);


                }else if (promoItems.get(0).getTypesPromo() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Бесплатный QR-Code доступен в разделе Мои покупки!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }else {


                Toast toast = Toast.makeText(getApplicationContext(),
                        promoItems.get(0).getMessages(),
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }


        }
    }


    public  class ItemTaskPercent extends AsyncTask<Void, Void, List<PromoItem>> {


        @Override
        protected List<PromoItem> doInBackground(Void... voids) {


            return new PercentApi().percent();
        }

        @Override
        protected void onPostExecute(List<PromoItem> promoItems) {
            super.onPostExecute(promoItems);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            Log.d(LOG_TAG, "percent = " + promoItems);
            percentCachBack = promoItems.get(0).getPercentBy();

        }
    }



    //TODO payment methods

    void timeToStartCheckout() {



        final Set<PaymentMethodType> paymentMethodTypes = new HashSet<>();
        paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY);
        paymentMethodTypes.add(PaymentMethodType.BANK_CARD);

        final Set<GooglePayCardNetwork> allowedCardNetworks = new HashSet<>();
        allowedCardNetworks.add(GooglePayCardNetwork.MASTERCARD);
        allowedCardNetworks.add(GooglePayCardNetwork.VISA);
        allowedCardNetworks.add(GooglePayCardNetwork.OTHER);


        if (!paiment_id.equals("")){

            SavedBankCardPaymentParameters parameters = new SavedBankCardPaymentParameters(
                    new Amount(amount, Currency.getInstance("RUB")),
                    "Услуга автомойки",
                    "",
                    "live_NjI5ODE47cquPSD7939_gasmXhbT0ccjlnABYEq9rHg",
                    "629818",
                     paiment_id
            );
            Intent intent = Checkout.createSavedCardTokenizeIntent(this, parameters);
            startActivityForResult(intent, REQUEST_CODE_TOKENIZE);



        }else {





            PaymentParameters paymentParameters = new PaymentParameters(
                    new Amount(amount, Currency.getInstance("RUB")),
                    "Услуга автомойки",
                    "",
                    "live_NjI5ODE47cquPSD7939_gasmXhbT0ccjlnABYEq9rHg",
                    "629818",
                    paymentMethodTypes,
                    null,
                    null,
                    null,
                    new GooglePayParameters(allowedCardNetworks)

            );


            /*TestParameters testParameters = new TestParameters(true, true,
                    new MockConfiguration(false, true, 0, new Amount(new BigDecimal(0), Currency.getInstance("RUB"))));*/



            UiParameters uiParameters = new UiParameters(true, new ColorScheme(Color.rgb(255, 0, 0)));
            Intent intent = Checkout.createTokenizeIntent(this, paymentParameters,new TestParameters(), uiParameters);

            startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
        }




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d(LOG_TAG, "request code = " + requestCode + "result  = " + resultCode + "data = " + data);

        if (requestCode == 1) {
            switch (resultCode) {
                case RESULT_OK:

                    Intent intent = new Intent(ByActivity.this, PaymentGood.class);

                    String[] array  = new String[] {"минута", "минуты", "минут"};


                    String s =  time + " " + getNumending(array,Float.valueOf(time));

                    intent.putExtra("sizecnt",  String.valueOf(two));
                    intent.putExtra("minuts", s);

                    startActivity(intent);

                    break;
                case RESULT_CANCELED:
                    show3dsAlertDialog("Вы не подтвердили платеж");
                    break;
                case Checkout.RESULT_ERROR:

                    show3dsAlertDialog("Ошибка оплаты , попробуйте повторить операцию!");

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

                    tokenApp = result.component1();
                    Log.d("LOG_TAG", "getPaymentToken = " + getPaymentToken +"\ngetPaymentMethodType = " + getPaymentMethodType + "\n component1 = " + component1 +
                            "\n component2 = " +component2);



                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    new ItemTaskPyment().execute();



                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization

                    show3dsAlertDialog("Вы не подтвердили платеж");


                    break;

                case Checkout.RESULT_ERROR:

                    show3dsAlertDialog("Ошибка оплаты , попробуйте повторить операцию!");

                    // во время 3ds произошла какая-то ошибка (нет соединения или что-то еще)
                    // более подробную информацию можно посмотреть в data
                    // data.getIntExtra(Checkout.EXTRA_ERROR_CODE) - код ошибки из WebViewClient.ERROR_* или Checkout.ERROR_NOT_HTTPS_URL
                    // data.getStringExtra(Checkout.EXTRA_ERROR_DESCRIPTION) - описание ошибки (может отсутствовать)
                    // data.getStringExtra(Checkout.EXTRA_ERROR_FAILING_URL) - url по которому произошла ошибка (может отсутствовать)
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


    public  class ItemTaskPyment extends AsyncTask<Void, Void, PaymentItem> {


        @Override
        protected PaymentItem doInBackground(Void... voids) {



            return  new PaymentApi().paymentItem(tokenApp, tokenUser);
        }

        @Override
        protected void onPostExecute(PaymentItem paymentItem) {
            super.onPostExecute(paymentItem);



            Log.d(LOG_TAG,"13213 = " + paymentItem.getStatusCodePayment());


            progressBar.setVisibility(ProgressBar.INVISIBLE);


            if (paymentItem.getStatusCodePayment().equals("500")) {



                Toast toast = Toast.makeText(getApplicationContext(),
                        "Во время операции произошла ошибка.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


//                Intent intent = new Intent(ByActivity.this, PaymentGood.class);
//
//                String[] array = new String[]{"минута", "минуты", "минут"};
//
//
//                String s = time + " " + getNumending(array, Float.valueOf(time));
//
//                intent.putExtra("sizecnt", String.valueOf(two));
//                intent.putExtra("minuts", s);
//
//                startActivity(intent);



            } else {


                if (paymentItem.getMessage() == null || paymentItem.getMessage().equals("")) {


                    if (paymentItem.getConfUrl().equals("")) {

                        Intent intent = new Intent(ByActivity.this, PaymentGood.class);

                        String[] array = new String[]{"минута", "минуты", "минут"};


                        String s = time + " " + getNumending(array, Float.valueOf(time));

                        intent.putExtra("sizecnt", String.valueOf(two));
                        intent.putExtra("minuts", s);

                        startActivity(intent);


                    } else if (paymentItem.getConfUrl().equals("error")) {


                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Во время операции произошла ошибка.",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else {
                        timeToStart3DS(paymentItem.getConfUrl());

                    }


                } else {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            paymentItem.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }

        }
    }







    public  class SavedCards extends AsyncTask<Void, Void, PaymentItem> {


        @Override
        protected PaymentItem doInBackground(Void... voids) {



            return  new SaveCard().paymentItem(tokenUser);
        }

        @Override
        protected void onPostExecute(PaymentItem paymentItem) {
            super.onPostExecute(paymentItem);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (paymentItem.getErrorRequest() == 200){

                canceledSavedCard.setEnabled(true);
                canceledSavedCard.setAlpha(1.f);
                checkbox.setChecked(true);
                textSaveCard ="Покупка будет произведена с \n" + paymentItem.getCard_text();
                checkbox.setText(textSaveCard);
                paiment_id = paymentItem.getPayment_id();
                statusCard = "1";

            }else {

                canceledSavedCard.setEnabled(false);
                canceledSavedCard.setAlpha(0.f);
                checkbox.setChecked(false);
                textSaveCard ="Сохранить платежные данные для последующих покупок";
                checkbox.setText(textSaveCard);
                paiment_id = "";
                statusCard = "0";

            }





        }
    }




    public  class CancelCard extends AsyncTask<Void, Void, PaymentItem> {


        @Override
        protected PaymentItem doInBackground(Void... voids) {



            return  new CanceledSaveCard().paymentItem(tokenUser);
        }

        @Override
        protected void onPostExecute(PaymentItem paymentItem) {
            super.onPostExecute(paymentItem);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (paymentItem.getStutusCanceldCard() == 200){



                Toast toast = Toast.makeText(getApplicationContext(),
                        "Вы отвязали карту",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                new SavedCards().execute();

            }else {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "При выполнение операции произошла ошибка",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            }



        }
    }



    public class SaveCard {

        public static final String LOG_TAG = "PAYMENT_LOG";
        private OkHttpClient client = new OkHttpClient();

        String tokenUser;

        public String getJSONString (String UrlSpec) throws IOException {


            Request request = new Request.Builder()
                    .url(UrlSpec)
                    .addHeader("Authorization", "Token " + tokenUser)
                    .build();

            Response response = client.newCall(request).execute();

            String result = null;
            if (response.code() == 200){
               result = response.body().string();

            }else if (response.code() == 400) {
                result = "400";
            }



            return result;

        }


        public PaymentItem paymentItem(String _tokenUser){

            PaymentItem paymentItem = new PaymentItem();
            tokenUser = _tokenUser;

            try {
                String url = Uri.parse("https://app.pomoysam.ru/api/v0/checkSavedCard/")
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

            if (jsonString.equals("400")){

                items.setErrorRequest(400);

            }else {
                JSONObject jsonBody = new JSONObject(jsonString);
                items.setErrorRequest(200);
                items.setCard_text(jsonBody.getString("card_text"));
                items.setPayment_id(jsonBody.getString("payment_id"));
            }


        }

    }





    public class PaymentApi {

        public static final String LOG_TAG = "PAYMENT_LOG";
        private OkHttpClient client = new OkHttpClient();

        String tokenApp;
        String tokenUser;

        public String getJSONString (String UrlSpec) throws IOException {

            String save = null;
            if (checkbox.isChecked()){
                save= "1";

            }else {
                save = "0";
            }

            FormBody body = new FormBody.Builder()
                    .add("app_token", tokenApp)
                    .add("user_token", "Token " + tokenUser)
                    .add("coins_cnt", String.valueOf(two))
                    .add("summ", String.valueOf(amount))
                    .add("promo_code",promoDiscount)
                    .add("save_card",save)


                    .build();


            Request request = new Request.Builder()
                    .url(UrlSpec)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();



            String result = null;
            if (response.code() == 200){
                result = response.body().string();

            }else if (response.code() == 500) {
                result = "500";
            }


            return result;

        }


        public PaymentItem paymentItem(String _token, String _tokenUser){

            PaymentItem paymentItem = new PaymentItem();
            tokenApp = _token;
            tokenUser = _tokenUser;

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

            if (jsonString.equals("500")){

                items.setStatusCodePayment("500");

            }else {

                items.setStatusCodePayment("hellow");


                JSONObject jsonBody = new JSONObject(jsonString);


                if (jsonBody.has("message")){


                    items.setMessage(jsonBody.getString("message"));


                }else {
                    items.setConfUrl(jsonBody.getString("confirmation_url"));

                }

            }




        }

    }


    //TODO canceled saved card i ne ebet


    public class CanceledSaveCard {

        public static final String LOG_TAG = "PAYMENT_LOG";
        private OkHttpClient client = new OkHttpClient();

        String tokenUser;

        public String getJSONString (String UrlSpec) throws IOException {



            FormBody body = new FormBody.Builder()
                    .build();


            Request request = new Request.Builder()
                    .url(UrlSpec)
                    .addHeader("Authorization", "Token " + tokenUser)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String result = null;
            if (response.code() == 200){
                result = response.body().string();

            }else if (response.code() == 400) {
                result = "400";
            }



            return result;

        }


        public PaymentItem paymentItem(String _tokenUser){

            PaymentItem paymentItem = new PaymentItem();
            tokenUser = _tokenUser;

            try {
                String url = Uri.parse("https://app.pomoysam.ru/api/v0/cancelSavedCard/")
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

            if (jsonString == null|| jsonString.equals("")){

                items.setStutusCanceldCard(200);
            }else {
                items.setStutusCanceldCard(400);
            }

        }
    }

}
