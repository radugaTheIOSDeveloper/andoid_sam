package ru.pomoysam.itstack;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PromoAPI {

    public static final String LOG_TAG = "REGISTR_FULL ";
    private OkHttpClient client = new OkHttpClient();


    String token;
    String codes;



    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("code", codes)


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


    public List<PromoItem> promoItems(String _token, String code){

        List<PromoItem> promoItems = new ArrayList<>();
        token = _token;
        codes = code;


        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/tryPromo/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);

            parseItems(promoItems, jsonBody);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);

        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);


        }
        return promoItems;

    }

    private void parseItems (List<PromoItem> items , JSONObject jsonBody) throws IOException, JSONException {

        Log.d(LOG_TAG , "jsonBody - " + jsonBody);

        PromoItem item = new PromoItem();

        if (jsonBody.has("message")){
            item.setMessages(jsonBody.getString("message"));
            items.add(item);


        }else {
            item.setTypesPromo(jsonBody.getInt("type"));
            item.setPercent(jsonBody.getInt("discount_percent"));
            items.add(item);
        }


    }


}
