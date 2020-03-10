package ru.pomoysam.itstack;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {

    public static final String LOG_TAG = "News";



    public String getJSONString (String UrlSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public List<NewsItem> newsItems(){

        List<NewsItem> newsItems = new ArrayList<>();



        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/news/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);

            parseItems(newsItems, jsonString);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return newsItems;


    }

    private void parseItems (List<NewsItem> newsItems , String jsonString) throws IOException, JSONException {


        Log.d(LOG_TAG, "json string news = " + jsonString);
        JSONArray jsonArray = new JSONArray(jsonString);


        for (int i = 0; i < jsonArray.length()  ; i++){
            JSONObject payDateJsonObject = jsonArray.getJSONObject(i);
            NewsItem item = new NewsItem();
            item.setUrlImage(payDateJsonObject.getString("image"));

            newsItems.add(item);

        }


    }


}
