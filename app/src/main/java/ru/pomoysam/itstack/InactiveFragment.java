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

public class InactiveFragment extends Fragment {


    public InactiveFragment() {
    }

    public static InactiveFragment newInstance() {
        return new InactiveFragment();
    }

    ArrayList<ActiveItem> activeItems = new ArrayList<ActiveItem>();
    InactiveAdapter inactiveAdapter;
    Context thiscontext;
    ListView lv;
    ProgressBar progressBar;
    String token;
    ArrayList<ItemActiveFalse> itemActiveFalses = new ArrayList<ItemActiveFalse>();
    ActiveAdapterFalse activeAdapterFalse;


    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static final String LOG_TAG = "inactive = ";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_inactive,container, false);
        thiscontext = container.getContext();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutInactive);
//
//
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_red_light,
                android.R.color.holo_red_light,
                android.R.color.holo_red_light);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                itemActiveFalses.clear();
                activeItems.clear();
                new ItemTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        Button inactivebtn = view.findViewById(R.id.inactiveBy);



        lv = view.findViewById(R.id.inactiveList);

        SharedPreferences pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");
        new ItemTask().execute();


        progressBar = view.findViewById(R.id.progressbarInactive);
        final Animation animationAlpha = AnimationUtils.loadAnimation(thiscontext, R.anim.alpha);

        progressBar.setVisibility(ProgressBar.VISIBLE);


        inactivebtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                inactivebtn.startAnimation(animationAlpha);


                Intent intent = new Intent(thiscontext, ByActivity.class);
                startActivity(intent);

            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
            }
        });



        return view;


    }


    public  class ItemTask extends AsyncTask<Void, Void, List<BuyItem>> {


        @Override
        protected List<BuyItem> doInBackground(Void... voids) {

            return  new WashSamApi().buyItems("inactive", token);

        }

        @Override
        protected void onPostExecute(List<BuyItem> buyItems) {
            super.onPostExecute(buyItems);

            Log.d(LOG_TAG," oleg = " + buyItems.size());

            if (buyItems.size() == 0){

                activeAdapterFalse = new ActiveAdapterFalse(thiscontext, itemActiveFalses);
                itemActiveFalses.add(new ItemActiveFalse("У Вас нет неактивных покупок"));
                lv.setEnabled(false);
                lv.setAdapter(activeAdapterFalse);

            }else {



                inactiveAdapter = new InactiveAdapter(thiscontext, activeItems);

                for (int i = 0; i <= buyItems.size() - 1; i++) {
                    activeItems.add(new ActiveItem(buyItems.get(i).getMinuts(),
                            buyItems.get(i).getInfoMinuts(), buyItems.get(i).getDate(), buyItems.get(i).getQrCode(), buyItems.get(i).getIdBuy(),
                            R.drawable.orderinactive));
                }



                lv.setAdapter(inactiveAdapter);

            }


            progressBar.setVisibility(ProgressBar.INVISIBLE);




        }
    }

}
