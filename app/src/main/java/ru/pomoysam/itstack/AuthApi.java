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

public class AuthApi {

    public static final String LOG_TAG = "AUTH_API ";
    private OkHttpClient client = new OkHttpClient();

    String url = "https://app.pomoysam.ru/api/v0/login/";
    String resp = "";

    String phones;
    String passs;


    public String getJSONString (String UrlSpec) throws IOException {

        AuthItem item = new AuthItem();




        FormBody body = new FormBody.Builder()
                .add("username", phones)
                .add("password", passs)
                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<AuthItem> authItems(String phone , String password){

        List<AuthItem> token = new ArrayList<>();

        Log.d(LOG_TAG, phone + "phone" + password);

            phones = phone;
            passs = password;

        AuthItem item = new AuthItem();


        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/login/")
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

    private void parseItems (List<AuthItem> items , JSONObject jsonBody) throws IOException, JSONException {



            Log.d(LOG_TAG, "json body  = " + jsonBody.toString());


                if (jsonBody.has("non_field_errors")){
                    AuthItem item = new AuthItem();
                    item.setToken("bads");
                    items.add(item);
                }else {
                    AuthItem item = new AuthItem();
                    String srt = jsonBody.getString("token");
                    item.setToken(srt);
                    items.add(item);
                }

    }



}


