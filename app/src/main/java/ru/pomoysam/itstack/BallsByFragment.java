package ru.pomoysam.itstack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class BallsByFragment extends Fragment {

    public BallsByFragment() {
    }

    public static BallsByFragment newInstance() {

        return new BallsByFragment();
    }

    public static final String LOG_TAG = "BALLS - FRAGMENT = ";

    Context thiscontext;
    Button plusBalls;
    Button minusBalls;
    Button btnballact;
    TextView infoTextTitle;
    TextView numballs;
    TextView infoSizeBalls;
    TextView infotextdetail;


    Integer cizeBalls;
    Integer twoMin;
    Integer timeBalls;
    Double balls;
    String token;
    Integer coins;

    String strSizeMin;

    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.balls_buy_fragment, container, false);
        thiscontext = container.getContext();


        infotextdetail = view.findViewById(R.id.infotextdetail);

        btnballact = view.findViewById(R.id.btnballspol);
        plusBalls  = view.findViewById(R.id.plusballs);
        minusBalls =  view.findViewById(R.id.minusballs);

        infoTextTitle   =  view.findViewById(R.id.btnballspol);

        numballs   =  view.findViewById(R.id.numballs);

        infoSizeBalls  =  view.findViewById(R.id.infoSizeBalls);

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(ProgressBar.INVISIBLE);


        Bundle bundle = getArguments();
        if (bundle != null) {

            balls = bundle.getDouble("sizeBallls");
            Double c = bundle.getDouble("sizeBallls")/50;
            coins = c.intValue();

            String[] array  = new String[] {"жетон", "жетоны", "жетонов"};


            infotextdetail.setText("На вашем счете " + bundle.getDouble("sizeBallls") + " баллов.\nВы можете приобрести максимум " +coins +" "+ getNumending(array,coins));
            getTextNumending(coins/50,2);


        }



        twoMin = 0;
        cizeBalls = 0;
        numballs.setText("0");
        timeBalls = 0;


        timeBalls = 0;

        getTextNumending(timeBalls,0);
        getTextNumending(balls.intValue(),1);

        SharedPreferences pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");
        Log.d(LOG_TAG, "token = " + token);


        plusBalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (balls < 50){

                    Toast toast = Toast.makeText(thiscontext,
                            "Недостаточно баллов",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {

                    if (twoMin >= 99){
                        twoMin = 99;

                        numballs.setText(twoMin.toString());


                        Toast toast = Toast.makeText(thiscontext,
                                "Превышен лимит количества жетонов",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }else {
                        timeBalls = timeBalls + 2;

                        balls = balls - 50;

                        twoMin = twoMin +1;
                        getTextNumending(timeBalls,0);
                        getTextNumending(balls.intValue(),1);

                        numballs.setText(twoMin.toString());
                    }
                }





            }
        });



        minusBalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (twoMin == 0 ){

                    twoMin = 0;
                    numballs.setText(twoMin.toString());

                }else  {

                    twoMin = twoMin -1;
                    timeBalls = timeBalls - 2;
                    balls = balls + 50;


                    getTextNumending(timeBalls,0);
                    getTextNumending(balls.intValue(),1);

                    numballs.setText(twoMin.toString());



                }


            }
        });


        btnballact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (twoMin > 0){
                    //Log.d(LOG_TAG , "two min  = "+twoMin);

                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    new ItemTaskBalls().execute();




                }else {

                    Toast toast = Toast.makeText(thiscontext,
                            "Баллы нет",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }
        });



        return view;
    }






    void getTextNumending (Integer _time, Integer index){


        if (index == 0){

            String[] array  = new String[] {"минута", "минуты", "минут"};



            strSizeMin = timeBalls.toString() + " " + getNumending(array, _time);

        }else if (index == 1){

            String[] array  = new String[] {"балл", "балла", "баллов"};


            infoSizeBalls.setText(balls + " " + getNumending(array, _time));

        } else if (index == 2){

            String[] array  = new String[] {"жетон", "жетоны", "жетонов"};


            infoSizeBalls.setText(coins/50 + " " + getNumending(array, _time));

        }





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




    public  class ItemTaskBalls extends AsyncTask<Void, Void, List<BallsItem>> {


        @Override
        protected List<BallsItem> doInBackground(Void... voids) {


            return  new BallsBuyAPI().ballsItems(token, twoMin.toString());
        }

        @Override
        protected void onPostExecute(List<BallsItem > ballsItems) {
            super.onPostExecute(ballsItems);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            BallsFragmentGood yfc = new BallsFragmentGood();
            Bundle bundle = new Bundle();
            bundle.putInt("sizeBallls", twoMin);
            bundle.putString("time", strSizeMin);
            yfc.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ballsFragmentGroup,  yfc).commit();

        }
    }



}
