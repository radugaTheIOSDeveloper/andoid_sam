package ru.pomoysam.itstack;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BallsBuyAPI {

    public static final String LOG_TAG = "REGISTR_FULL ";
    private OkHttpClient client = new OkHttpClient();


    String token;
    String codes;



    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("cnt", codes)


                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .addHeader("Authorization", "Token " + token)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<BallsItem> ballsItems(String _token, String code){

        List<BallsItem> ballsItems = new ArrayList<>();
        token = _token;
        codes = code;


        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/cashBackPay/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);

            parseItems(ballsItems, jsonString);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);

        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);


        }
        return ballsItems;

    }

    private void parseItems (List<BallsItem> items , String jsonString) throws IOException, JSONException {



        Log.d(LOG_TAG, "json body  = " +  jsonString);



    }


}
