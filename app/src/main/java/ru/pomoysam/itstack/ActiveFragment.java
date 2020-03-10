package ru.pomoysam.itstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.List;

public class ActiveFragment extends Fragment {
    public ActiveFragment() {
    }

    public static ActiveFragment newInstance() {
        return new ActiveFragment();
    }

    ArrayList<ActiveItem> activeItems = new ArrayList<ActiveItem>();
    ArrayList<ItemActiveFalse> itemActiveFalses = new ArrayList<ItemActiveFalse>();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    List<BuyItem> mItemsList = new ArrayList<>();

    ActiveAdapter activeAdapter;
    ActiveAdapterFalse activeAdapterFalse;
    Context thiscontext;
    ListView lv;
    SharedPreferences sharedPreferencesInfo;
    String token;

    public static final String LOG_TAG = "active = ";

    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_active,container, false);
        thiscontext = container.getContext();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//
//
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_red_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemActiveFalses.clear();
                activeItems.clear();
                new ItemTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        progressBar = view.findViewById(R.id.activeProgressBar);
        final Animation animationAlpha = AnimationUtils.loadAnimation(thiscontext, R.anim.alpha);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        Button buyBtn = view.findViewById(R.id.buyBtn);

        lv = view.findViewById(R.id.activeList);

        SharedPreferences pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");
        Log.d(LOG_TAG, "token = " + token);


        new ItemTask().execute();


        buyBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {


                buyBtn.startAnimation(animationAlpha);
                Intent intent = new Intent(thiscontext, ByActivity.class);
                startActivity(intent);

            }
        });




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(thiscontext, QRCodeActivity.class);
                intent.putExtra("qr",  mItemsList.get(position).getQrCode());
                startActivity(intent);

                Log.d(LOG_TAG, "log = " +  mItemsList.get(position).getQrCode());

            }
        });





        return view;


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }






    public  class ItemTask extends AsyncTask<Void, Void, List<BuyItem>> {


        @Override
        protected List<BuyItem> doInBackground(Void... voids) {

            return  new WashSamApi().buyItems("active",token);

        }

        @Override
        protected void onPostExecute(List<BuyItem> buyItems) {
            super.onPostExecute(buyItems);

            mItemsList = buyItems;

            if (buyItems.size() == 0){

                activeAdapterFalse = new ActiveAdapterFalse(thiscontext, itemActiveFalses);
                itemActiveFalses.add(new ItemActiveFalse("У Вас нет активных покупок"));
                lv.setEnabled(false);
                lv.setAdapter(activeAdapterFalse);

            }else {

                activeAdapter = new ActiveAdapter(thiscontext, activeItems);

                for (int i = 0; i <= buyItems.size() -1; i++) {
                    activeItems.add(new ActiveItem(buyItems.get(i).getMinuts(), buyItems.get(i).getInfoMinuts(), buyItems.get(i).getDate(), buyItems.get(i).getQrCode(),
                            buyItems.get(i).getIdBuy(),
                            R.drawable.order1));

                }


                lv.setAdapter(activeAdapter);

            }




            progressBar.setVisibility(ProgressBar.INVISIBLE);



        }
    }



        void fillData() {



        for (int i = 1; i <= 1; i++) {
            activeItems.add(new ActiveItem("123"+  i, "123","123","123","123",
                    R.drawable.order1));
        }
    }


}
