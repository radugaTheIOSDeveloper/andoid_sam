package ru.pomoysam.itstack;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BallsApi {

    public static final String LOG_TAG = "BALLS_API ";

    String token;


    public String getJSONString (String UrlSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlSpec)
                .addHeader("Authorization", "Token " + token)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public List<BallsItem> ballsItems( String _token){

        List<BallsItem> ballsItems = new ArrayList<>();

        token = _token;


        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/userBalance/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);

            parseItems(ballsItems, jsonBody);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return ballsItems;


    }

    private void parseItems (List<BallsItem> items , JSONObject jsonBody) throws IOException, JSONException {

        Log.d(LOG_TAG , "json body = " + jsonBody);


        BallsItem item = new BallsItem();
        item.setBalance(jsonBody.getDouble("balance"));
        item.setStrBalance(jsonBody.getString("str_balance"));
        item.setPercent(jsonBody.getInt("cash_back_percent"));
        items.add(item);




    }



}
