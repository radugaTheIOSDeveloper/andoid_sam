package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BallsFragment extends Fragment {

    public BallsFragment() {
    }

    public static BallsFragment newInstance() {




        return new BallsFragment();
    }


    Context thiscontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.balls_fragment_group, container, false);
        thiscontext = container.getContext();

        loadFragment(BallsFragmentActivity.newInstance());


        return view;
    }



    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.ballsFragmentGroup, fragment);
        ft.commit();
    }

}
