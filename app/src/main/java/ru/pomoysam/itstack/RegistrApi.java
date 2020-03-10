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

public class RegistrApi {

    public static final String LOG_TAG = "REGISTR_API ";
    private OkHttpClient client = new OkHttpClient();


    String phones;


    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("phone", phones)
                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<RegistrItem> registrItems(String phone){

        List<RegistrItem> token = new ArrayList<>();


        phones = phone;

        RegistrItem item = new RegistrItem();


        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/prepareForRegister/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);

            parseItems(token, jsonBody);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);

        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);


        }
        return token;

    }

    private void parseItems (List<RegistrItem> items , JSONObject jsonBody) throws IOException, JSONException {





        if (jsonBody.getString("status").equals("error")){
            RegistrItem item = new RegistrItem();
            item.setMessages(jsonBody.getString("message"));
            items.add(item);
        }else {
            RegistrItem item = new RegistrItem();
            String srt = jsonBody.getString("status");
            item.setMessages(srt);
            items.add(item);
        }

    }

}
