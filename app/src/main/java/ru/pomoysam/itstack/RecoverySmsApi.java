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

public class RecoverySmsApi {

    public static final String LOG_TAG = "REGISTR_FULL ";
    private OkHttpClient client = new OkHttpClient();


    String phones;
    String codes;



    public String getJSONString (String UrlSpec) throws IOException {


        FormBody body = new FormBody.Builder()
                .add("user_phone", phones)
                .add("confirm_code", codes)


                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;

    }


    public List<RecoverySmsItem> recoverySmsItems(String phone, String code){

        List<RecoverySmsItem> token = new ArrayList<>();

        phones = phone;
        codes = code;

        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/confirmResetPassword/")
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

    private void parseItems (List<RecoverySmsItem> items , JSONObject jsonBody) throws IOException, JSONException {



        Log.d(LOG_TAG, jsonBody.getString("status"));

        if (jsonBody.getString("status").equals("error")){
            RecoverySmsItem item = new RecoverySmsItem();
            item.setMessages(jsonBody.getString("message"));
            items.add(item);
        }else {
            RecoverySmsItem item = new RecoverySmsItem();
            String srt = jsonBody.getString("status");
            item.setMessages(srt);
            items.add(item);
        }

    }

}
