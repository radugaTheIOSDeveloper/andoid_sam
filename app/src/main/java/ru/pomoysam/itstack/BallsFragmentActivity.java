package ru.pomoysam.itstack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class BallsFragmentActivity extends Fragment {

    public BallsFragmentActivity() {
    }

    public static BallsFragmentActivity newInstance() {


        return new BallsFragmentActivity();

    }

    public static final String LOG_TAG = "BALLS - FRAGMENT = ";

    Button btnnextballs;
    Context thiscontext;
    TextView inforText;
    TextView coins;
    String token;
    TextView titleInfo;
    Double sizeCoins;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_balls, container, false);
        thiscontext = container.getContext();

        titleInfo = view.findViewById(R.id.titleInfo);
        btnnextballs = view.findViewById(R.id.btnbal);
        inforText = view.findViewById(R.id.ballsInfo);
        coins = view.findViewById(R.id.ballsCoin);

        progressBar = view.findViewById(R.id.progressBar);
        final Animation animationAlpha = AnimationUtils.loadAnimation(thiscontext, R.anim.alpha);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        SharedPreferences pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");
        Log.d(LOG_TAG, "token = " + token);









        btnnextballs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sizeCoins < 50){

                    Toast toast = Toast.makeText(thiscontext,
                            "Недостаточно баллов для совершения покупки",
                            Toast.LENGTH_SHORT);
                    toast.show();


                }else {


                    BallsByFragment yfc = new BallsByFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("sizeBallls", sizeCoins);
                    yfc.setArguments(bundle);




//
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.replace(R.id.ballsFragmentGroup, BallsByFragment.newInstance());
//                    ft.commit();

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.ballsFragmentGroup,  yfc).commit();



                    //loadFragment(BallsByFragment.newInstance());

                }


            }
        });


        new ItemTaskBallsInfo().execute();


        return view;
    }



    public  class ItemTaskBallsInfo extends AsyncTask<Void, Void, List<BallsItem>> {


        @Override
        protected List<BallsItem> doInBackground(Void... voids) {

            return  new BallsApi().ballsItems(token);

        }

        @Override
        protected void onPostExecute(List<BallsItem > ballsItems) {
            super.onPostExecute(ballsItems);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            String textinfo = ("C каждой покупкой Вам начисляется " + ballsItems.get(0).getPercent() + "% \nот оплаченной суммы в виде баллов");
            titleInfo.setText(textinfo);
            sizeCoins = ballsItems.get(0).getBalance();
            int s = sizeCoins.intValue();


            Log.d("LOG_TAG", "s = " + s);
            int sb = s/50;
            int m = sb*2;

            getText(m, 0);

            getText(sb, 1);

            getTextNumending(s);

        }
    }




    void getText (Integer _time, Integer index){


        if (index == 0){

            String[] array  = new String[] {"минута", "минуты", "минут"};


        }else if (index == 1){

            String[] array  = new String[] {"жетон", "жетона", "жетонов"};

            coins.setText(_time +" " + getNum(array, _time));

        }





    }


    String getNum (String[] _array , Integer _item) {
        String str;
        _item = _item % 100;

        if (_item>=11 && _item<=19) {
            str = _array[2];
        }
        else {
            int i = (int) (_item % 10);
            switch (i)
            {
                case (1): str = _array[0]; break;
                case (2): str = _array[1]; break;
                case (3): str = _array[1]; break;
                case (4): str = _array[1]; break;
                default: str=_array[2];
            }
        }

        return str;


    }



    void getTextNumending (Integer _time){

        String[] array  = new String[] {"балл", "балла", "баллов"};


        inforText.setText(("На вашем счете \n" + sizeCoins.floatValue() + " " + getNumending(array, _time)));


    }


    String getNumending (String[] _array , Integer _item) {
        String str;
        _item = _item % 100;

        if (_item>=11 && _item<=19) {
            str = _array[2];
        }
        else {
            int i = _item % 10;
            switch (i)
            {
                case (1): str = _array[0]; break;
                case (2): str = _array[1]; break;
                case (3): str = _array[1]; break;
                case (4): str = _array[1]; break;
                default: str=_array[2];
            }
        }

        return str;


    }






    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.ballsFragmentGroup, fragment);
        ft.commit();
    }

}
