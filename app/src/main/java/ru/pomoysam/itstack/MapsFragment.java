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


public class MapsFragment extends Fragment {



    protected static final String LOG_TAG = "my_tag";

    Context thiscontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        // Setting ViewPager for each Tabs


        thiscontext = container.getContext();


        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHostMap);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.tabMap1);
        tabSpec.setIndicator("СПИСОК");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tabMap2);
        tabSpec.setIndicator("КАРТА");
        tabHost.addTab(tabSpec);



        tabHost.setCurrentTab(0);

        if (tabHost.getCurrentTab() == 0){
            loadFragment(MapListFragment.newInstance());

        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);

                if (tabId == "tag1") {


                    loadFragment(MapListFragment.newInstance());

                } else if (tabId == "tag2"){

                    loadFragment2(FirstFragment.newInstance());

                }



            }
        });




        return view;
    }




    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tabMap1, fragment);
        ft.commit();
    }

    private void loadFragment2(Fragment fragment) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tabMap2, fragment);
        ft.commit();
    }


}
