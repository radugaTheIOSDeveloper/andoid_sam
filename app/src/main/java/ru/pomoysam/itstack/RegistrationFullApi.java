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

public class RegistrationFullApi {

    public static final String LOG_TAG = "REGISTR_FULL ";
    private OkHttpClient client = new OkHttpClient();


    String phones;
    String password;



    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("phone", phones)
                .add("password", phones)
                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<RegistrationFullItem> registrationFullItems(String phone , String passwords){

        List<RegistrationFullItem> token = new ArrayList<>();


        phones = phone;
        password = passwords;



        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/register/")
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

    private void parseItems (List<RegistrationFullItem> items , JSONObject jsonBody) throws IOException, JSONException {





        if (jsonBody.getString("status").equals("error")){
            RegistrationFullItem item = new RegistrationFullItem();
            item.setMessages("bads");
            items.add(item);
        }else {
            RegistrationFullItem item = new RegistrationFullItem();
            String srt = jsonBody.getString("token");
            item.setToken(srt);
            item.setMessages("good");

            items.add(item);
        }

    }

}
