package ru.pomoysam.itstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ru.pomoysam.itstack.NPagerAdapter.NesFragment;


import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {



    ViewPager viewPager;
    Context thiscontext;
    PagerAdapter pagerAdapter;

    String[] images;
    TableRow tableRow;
    String token;
    ProgressBar progressBar;
    List<NewsItem> mItemsList = new ArrayList<>();

    MyFragmentPagerAdapter catPagerAdapter;

    TableRow tr;
    View v;
    Boolean statusRow;
    String qr;
    String pushToken;

    ArrayList<String> mylist = new ArrayList<String>();

    SharedPreferences pref;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news,container, false);
        // Setting ViewPager for each Tabs

        thiscontext = container.getContext();

        viewPager = view.findViewById(R.id.viewPagerNews);
        tableRow   = view.findViewById(R.id.tableRowNews);
        tr = (TableRow) inflater.inflate(R.layout.tablerow, null);
        v = inflater.inflate(R.layout.active_false, null);


        pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");

//        mylist.add("https://app.pomoysam.ru/media/test2.png");
//        catPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), thiscontext,mylist);
//        viewPager.setAdapter(catPagerAdapter);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        progressBar = view.findViewById(R.id.newspb);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        new ItemTaskBy().execute();
        new ItemTask().execute();
        GetPush();

        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(statusRow == false){

                }else {



                    Intent intent = new Intent(thiscontext, QRCodeActivity.class);
                    intent.putExtra("qr",  qr);
                    startActivity(intent);


                }



            }
        });

        Button buttonNews = view.findViewById(R.id.buttonNews);

       // buttonNews.setTransformationMethod(null);

        final Animation animationAlpha = AnimationUtils.loadAnimation(thiscontext, R.anim.alpha);


        buttonNews.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.startAnimation(animationAlpha);

                Intent intent = new Intent(thiscontext, ByActivity.class);
                startActivity(intent);

            }
        });


        return view;

    }



    public  class ItemTask extends AsyncTask<Void, Void, List<NewsItem>> {


        @Override
        protected List<NewsItem> doInBackground(Void... voids) {

            return new NewsApi().newsItems();

        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            super.onPostExecute(newsItems);


            mylist.clear();

            for (int i= 0; i< newsItems.size() ;i++){

                mylist.add("https://app.pomoysam.ru"+newsItems.get(i).getUrlImage());

            }



            catPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), thiscontext,mylist);
            viewPager.setAdapter(catPagerAdapter);



        }

    }


    public  class ItemTaskBy extends AsyncTask<Void, Void, List<BuyItem>> {


        @Override
        protected List<BuyItem> doInBackground(Void... voids) {

            return  new WashSamApi().buyItems("active",token);

        }

        @Override
        protected void onPostExecute(List<BuyItem> buyItems) {
            super.onPostExecute(buyItems);


            progressBar.setVisibility(ProgressBar.INVISIBLE);


            if (buyItems.size() == 0){

                tableRow.addView(v);
                statusRow = false;

            }else {

                TextView tvd = (TextView) tr.findViewById(R.id.date);
                TextView tvm = (TextView) tr.findViewById(R.id.quantityMinuts);
                TextView tvi = (TextView) tr.findViewById(R.id.infotwnews);

                tvd.setText( buyItems.get(0).getDate());
                tvm.setText(buyItems.get(0).getMinuts());
                tvi.setText(buyItems.get(0).getInfoMinuts());
                qr = buyItems.get(0).getQrCode();
                statusRow = true;

                tableRow.addView(tr);
            }


        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<String> mylist;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context,  ArrayList<String> mylist) {
            super(fm);
            this.mylist = mylist;

        }

        @Override
        public Fragment getItem(int position) {

            Bundle arguments = new Bundle();

            arguments.putString(NesFragment.TOP_IMAGE, mylist.get(position));

            NesFragment newsFragment = new NesFragment();
            newsFragment.setArguments(arguments);

            return newsFragment;

        }

        @Override
        public int getCount()
        {
            return mylist.size();
        }
    }



    public void GetPush() {


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("hellow", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        pushToken = pref.getString("push_token", "");




                        if (token == pushToken){

                            progressBar.setVisibility(ProgressBar.INVISIBLE);

                        }else {

                            new SavePushToken().execute();

                        }


                        pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = pref.edit();
                        ed.putString("push_token", token);
                        ed.commit();






                    }
                });

    }




    public class SavePushToken extends AsyncTask<Void, Void, List<TokenItem>> {


        @Override
        protected List<TokenItem> doInBackground(Void... voids) {

            return new SaveTokenAPI().tokenItems(token, pushToken);

        }


        @Override
        protected void onPostExecute(List<TokenItem> tokenItems) {
            super.onPostExecute(tokenItems);


            Log.d("LOG_TAG", " status = " + tokenItems);

            progressBar.setVisibility(ProgressBar.INVISIBLE);




        }


    }





}
