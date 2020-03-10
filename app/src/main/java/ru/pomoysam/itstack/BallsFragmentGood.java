package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BallsFragmentGood extends Fragment {


    public BallsFragmentGood() {
    }

    public static BallsFragmentGood newInstance() {


        return new BallsFragmentGood();

    }

    public static final String LOG_TAG = "BALLS - FRAGMENT = ";

    Button btnnextballs;
    Context thiscontext;
    TextView twoInfoGood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.balls_good_fragment, container, false);
        thiscontext = container.getContext();

        btnnextballs = view.findViewById(R.id.backPaymentGood);
        twoInfoGood = view.findViewById(R.id.twopayGood);





//
        Bundle bundle = getArguments();
        if (bundle != null) {

            twoInfoGood.setText("" +bundle.getInt("sizeBallls"));
        }




        btnnextballs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragment(MyByFragment.newInstance());

            }
        });




        return view;
    }



    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_contayner, fragment);
        ft.commit();
    }

}
