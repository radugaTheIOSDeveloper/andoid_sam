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

public class WashSamApi {

    public static final String LOG_TAG = "WASH_SAM_API ";
    ArrayList<ActiveItem> activeItems = new ArrayList<ActiveItem>();

    String url = "https://app.pomoysam.ru/api/v0/";
    String token;
    String idFragment;


    public String getJSONString (String UrlSpec) throws IOException{

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlSpec)
                .addHeader("Authorization", "Token " + token)
                .build();


        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }

    public List<BuyItem> buyItems(String idFragments , String _token){

        List<BuyItem> buyItems = new ArrayList<>();

        idFragment = idFragments;
        token = _token;

        try {
            String url = Uri.parse("https://app.pomoysam.ru/api/v0/getUserQR/")
                    .buildUpon()
            //        .appendQueryParameter("format", "json")
                    .build().toString();

            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);

            parseItems(buyItems, jsonBody);

        } catch (IOException ioe){
            Log.e(LOG_TAG, "Ошибка загрузки данных", ioe);
        }catch (JSONException joe){
            Log.e(LOG_TAG, "Ошибка парсинга JSON", joe);
        }
        return buyItems;


    }

    private void parseItems (List<BuyItem> items , JSONObject jsonBody) throws IOException, JSONException {

        Log.d(LOG_TAG , "parsing arrays");

//        JSONObject inactive = jsonBody.getJSONObject("inactive");
//        JSONArray jsonArr = inactive.getJSONArray("inactive");

        JSONArray jsonArr = null;


        if (idFragment.equals("active")){

             jsonArr = jsonBody.getJSONArray("active");

        }else if (idFragment.equals("inactive")){
            jsonArr = jsonBody.getJSONArray("inactive");

        }



       for (int i = 0; i < jsonArr.length()  ; i++){
            JSONObject payDateJsonObject = jsonArr.getJSONObject(i);
            BuyItem item = new BuyItem();
            item.setDate(payDateJsonObject.getString("pay_date"));
            item.setQrCode(payDateJsonObject.getString("qr_code"));
            item.setIdBuy(payDateJsonObject.getString("qr_code_id"));
            item.setMinuts(payDateJsonObject.getString("minutes_str"));

            Integer twom = payDateJsonObject.getInt("2minutes_str");
            Integer fourm = payDateJsonObject.getInt("4minutes_str");

            twom = (fourm*2) + twom;

            item.setInfoMinuts("Количество жетонов: " + twom);

            items.add(item);



        }

    }



}
