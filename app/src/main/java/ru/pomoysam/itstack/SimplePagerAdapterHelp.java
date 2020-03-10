package ru.pomoysam.itstack;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SimplePagerAdapterHelp extends PagerAdapter {


    private Context mContext;

    public SimplePagerAdapterHelp(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        // получаем ресурсы (название и макет) соответствующий позиции в адаптере
        ResourceHelp resources = ResourceHelp.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // инициализируем экран ViewPager'а в соответствии с позицией
        ViewGroup layout = (ViewGroup) inflater.inflate(
                resources.getLayoutResourceId(), viewGroup, false);
        viewGroup.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object view) {
        viewGroup.removeView((View) view);
    }

    @Override
    public int getCount() {
        // кличество элементов в адаптере соответствует
        // количеству значений в enum классе
        return ResourceHelp.values().length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // получаем название соответствующее позиции в адаптере
        ResourceHelp customPagerEnum = ResourceHelp.values()[position];

        return mContext.getString(customPagerEnum.getTitleResourceId());
    }


}
