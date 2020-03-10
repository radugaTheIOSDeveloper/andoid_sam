package ru.pomoysam.itstack;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionNextApi {


    public static final String LOG_TAG = "QUESTION_NEXT ";
    ArrayList<ActiveItem> activeItems = new ArrayList<ActiveItem>();

    String url = "https://app.pomoysam.ru/api/v0/";


    String numPost;
    String message;
    String token;

    public String getJSONString (String UrlSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public List<QuestionNextItem> questionNextItems(){

        List<QuestionNextItem> questionNextItems = new ArrayList<>();



        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/car-wash/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);

            parseItems(questionNextItems, jsonString);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return questionNextItems;


    }

    private void parseItems (List<QuestionNextItem> questionNextItems , String jsonString) throws IOException, JSONException {




        JSONArray jsonArray = new JSONArray(jsonString);

//

        for (int i = 0; i < jsonArray.length()  ; i++){
            JSONObject payDateJsonObject = jsonArray.getJSONObject(i);
            QuestionNextItem item = new QuestionNextItem();
            item.setId_car_wash(payDateJsonObject.getString("id"));
            item.setAddr(payDateJsonObject.getString("car_wash_addr"));

            questionNextItems.add(item);



        }

    }




    public String getJSONStringPost (String UrlSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("problem", message)
                .add("car_wash_id", numPost)
                .build();


        Request request = new Request.Builder()
                .url(UrlSpec)
                .post(body)
                .addHeader("Authorization", "Token " + token)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }


    public List<QuestionNextItem> questionNextItemsPost(String num, String mess, String _token){

        List<QuestionNextItem> questionNextItems = new ArrayList<>();

        message = mess;
        numPost = num;
        token = _token;

        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/user-request/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONStringPost(url);

            parseItemsPost(questionNextItems, jsonString);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return questionNextItems;


    }

    private void parseItemsPost (List<QuestionNextItem> questionNextItems , String jsonString) throws IOException, JSONException {


        Log.d(LOG_TAG, "NExt = " +  jsonString);

    }



}
