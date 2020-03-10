package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MyByFragment extends Fragment {

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_myby,container, false);
//
//
//    }


    public MyByFragment() {
    }

    public static MyByFragment newInstance() {

        return new MyByFragment();
    }

    Context thiscontext;
    public static final String LOG_TAG = "myby = ";
    TableLayout  tableLayout;
    TableRow   tr;
    TabHost tabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myby,container, false);
        // Setting ViewPager for each Tabs

        thiscontext = container.getContext();

        tabHost  = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("АКТИВНЫЕ");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("ИСПОЛЬЗОВАННЫЕ");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("БАЛЛЫ");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);


        if (tabHost.getCurrentTab()== 0){
            loadFragment(ActiveFragment.newInstance());

        }
        //обработчик переключения табов
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {

                if (tabId == "tag1") {

                    Log.d(LOG_TAG, "tag1");
                    loadFragment(ActiveFragment.newInstance());


                } else if (tabId == "tag2"){
                    loadFragment2(InactiveFragment.newInstance());

                    Log.d(LOG_TAG, "tag2");

                }else if (tabId == "tag3"){
                    loadFragment3(BallsFragment.newInstance());

                    Log.d(LOG_TAG, "tag3");

                }



            }
        });


        Tabs();


        return view;

    }

    public void Tabs(){

        TabWidget tw = (TabWidget)tabHost.findViewById(android.R.id.tabs);
        View tabView = tw.getChildTabViewAt(0);
        TextView tv = (TextView)tabView.findViewById(android.R.id.title);
        tv.setText("АКТИВНЫЕ");
        tv.setTextSize(8);


        View tabView2 = tw.getChildTabViewAt(1);
        TextView tv2 = (TextView)tabView2.findViewById(android.R.id.title);
        tv2.setText("ИСПОЛЬЗОВАННЫЕ");
        tv2.setTextSize(8);


        View tabView3 = tw.getChildTabViewAt(2);
        TextView tv3 = (TextView)tabView3.findViewById(android.R.id.title);
        tv3.setText("БАЛЛЫ");
        tv3.setTextSize(8);


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tab1, fragment);
        ft.commit();
    }



    private void loadFragment2(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tab2, fragment);
        ft.commit();
    }

    private void loadFragment3(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tab3, fragment);
        ft.commit();
    }


}




