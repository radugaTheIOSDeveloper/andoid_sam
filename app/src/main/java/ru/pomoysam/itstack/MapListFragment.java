package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

public class MapListFragment extends Fragment {

    public MapListFragment() {
    }

    public static MapListFragment newInstance() {
        return new MapListFragment();
    }


    ArrayList<MapListItem> mapListItems = new ArrayList<MapListItem>();
    MapListAdapter mapListAdapter;

    Context thiscontext;
    View mView;
    ArrayList<Integer> myImageList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();


    ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_map_list, container, false);
        thiscontext = container.getContext();

        myImageList.add(R.drawable.ryajskoe);
        myImageList.add(R.drawable.ryazanskaya);
        myImageList.add(R.drawable.proletarskay);
        myImageList.add(R.drawable.karakazov);
        myImageList.add(R.drawable.kosmonavtov);
        myImageList.add(R.drawable.odoevskoe);
        myImageList.add(R.drawable.uilms);

        nameList.add("г. Тула, Ряжская 1А");
        nameList.add("г. Тула, Рязанская 46А");
        nameList.add("р.п. Первомайский, ул. Пролетарская дом 19(Рядом с газовой заправкой)");
        nameList.add("г. Тула, Веневское ш. 2Б");
        nameList.add("г.Новомосковск, ул.Космонавтов, 35Б");
        nameList.add("г. Тула, Одоевское ш.(напроти Пив.Завода Балтика)");
        nameList.add("г. Тула, Вильямса (напротив дома 46)");



        lv = (ListView)mView.findViewById(R.id.mapList);



        fillData();


        return mView;
    }


    void fillData() {



        for (int i = 0; i< myImageList.size(); i++){

            mapListItems.add(new MapListItem(nameList.get(i),R.drawable.logo_geo, myImageList.get(i)));

        }
        mapListAdapter = new MapListAdapter(thiscontext, mapListItems);


        lv.setAdapter(mapListAdapter);
    }



}
