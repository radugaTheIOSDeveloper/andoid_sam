package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class InstructionFragment extends Fragment {

    Context thiscontext;
    DotsIndicator dotsIndicator;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_instruction,container, false);
        // Setting ViewPager for each Tabs

        thiscontext = container.getContext();


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPagerInstruction);
        if (viewPager != null) {
            viewPager.setAdapter(new SimplePagerAdapterHelp(thiscontext));
        }


//        dotsIndicator = (DotsIndicator) view.findViewById(R.id.dots_indicator);
//
//        dotsIndicator.setViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);


        return view;
    }

}
