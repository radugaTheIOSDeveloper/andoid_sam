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

public class RegistrVirificationApi {

    public static final String LOG_TAG = "REGISTRVerificationAPI ";
    private OkHttpClient client = new OkHttpClient();


    String phones;
    String code;


    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("phone", phones)
                .add("confirm_code", code)
                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<RegistrVerificationItem> registrVerificationItems(String phone, String _code){

        List<RegistrVerificationItem> token = new ArrayList<>();


        phones = phone;
        code = _code;



        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/confirmRegister/")
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

    private void parseItems (List<RegistrVerificationItem> items , JSONObject jsonBody) throws IOException, JSONException {





        if (jsonBody.getString("status").equals("error")){
            RegistrVerificationItem item = new RegistrVerificationItem();
            item.setMessages(jsonBody.getString("message"));
            items.add(item);
        }else {
            RegistrVerificationItem item = new RegistrVerificationItem();
            String srt = jsonBody.getString("status");
            item.setMessages(srt);
            items.add(item);
        }

    }

}
