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

public class QuestionApi {

    public static final String LOG_TAG = "QUESTION ";
    ArrayList<ActiveItem> activeItems = new ArrayList<ActiveItem>();

    String url = "https://app.pomoysam.ru/api/v0/";



    public String getJSONString (String UrlSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public List<QuestionItem> questionItemList(){

        List<QuestionItem> questionItems = new ArrayList<>();



        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/faq/")
                    .buildUpon()
                    //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);

            parseItems(questionItems, jsonString);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return questionItems;


    }

    private void parseItems (List<QuestionItem> questionItems , String jsonString) throws IOException, JSONException {


//        JSONObject inactive = jsonBody.getJSONObject("inactive");
//        JSONArray jsonArr = inactive.getJSONArray("inactive");

        JSONArray jsonArray = new JSONArray(jsonString);

//

        for (int i = 0; i < jsonArray.length()  ; i++){
            JSONObject payDateJsonObject = jsonArray.getJSONObject(i);
            QuestionItem item = new QuestionItem();
            item.setQuestion(payDateJsonObject.getString("question"));
            item.setAnswer(payDateJsonObject.getString("answer"));

            questionItems.add(item);



        }

    }



}
