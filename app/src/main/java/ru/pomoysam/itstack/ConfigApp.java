package ru.pomoysam.itstack;


import java.util.ArrayList;
import java.util.List;

public class ConfigApp {

    public static final List<MapItem> mapItems = new ArrayList<MapItem>() {
        {
            add(new MapItem("г.Новомосковск, ул.Космонавтов, 35Б", 0.0F, R.drawable.kosmonavtov, 38.252156, 54.007482));
            add(new MapItem("р.п. Первомайский, ул. Пролетарская дом 19(Рядом с газовой заправкой)", 0.0F, R.drawable.proletarskay, 37.489695, 54.033654));
            add(new MapItem("г. Тула, Одоевское ш.(напроти Пив.Завода Балтика)", 0.0F, R.drawable.odoevskoe, 37.529558, 54.187362));
            add(new MapItem("г. Тула, Ряжская 1А", 0.0F, R.drawable.ryajskoe, 37.623024, 54.207003));
            add(new MapItem("г. Тула, Веневское ш. 2Б", 0.0F, R.drawable.karakazov, 37.645939, 54.209238));
            add(new MapItem("г. Тула, Рязанская 46А", 0.0F, R.drawable.ryazanskaya, 37.631806, 54.167056));
            add(new MapItem("г. Тула, Вильямса (напротив дома 46)", 0.0F, R.drawable.uilms, 37.696394, 54.211388));
        }
    };




}
