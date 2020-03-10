package ru.pomoysam.itstack;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class QuestionNext extends Fragment {


    public QuestionNext() {
    }

    public static QuestionNext newInstance() {
        return new QuestionNext();
    }

    Context thiscontext;
    Spinner faqSpiner;

    ArrayList<String> addr = new ArrayList<>();
    ArrayList<String> idNext = new ArrayList<>();
    private ProgressBar progressBar;

    String index;
    EditText editTextFaq;

    Button btnfq;
    final String LOG_TAG = "myLogs";
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_next_question, container, false);
        thiscontext = container.getContext();

        faqSpiner = view.findViewById(R.id.spinnerfaq);
        btnfq = view.findViewById(R.id.buttonfqnext);
       // faqSpiner.setPrompt("Выберите мойку*");
        addr.add("Выберите мойку*");
        editTextFaq = view.findViewById(R.id.editTextFaq);
        progressBar = view.findViewById(R.id.progressBarfqnext);


        btnfq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, "index = " + index);

                if(index == null){

                    Toast toast = Toast.makeText(thiscontext, "Выберите мойку"
                            ,
                            Toast.LENGTH_SHORT);
                    toast.show();

                }else if (editTextFaq.getText().toString().equals("")){
                    Toast toast = Toast.makeText(thiscontext, "Опишите вашу проблему или ваше пожелание"
                            ,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else {

                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    new ItemTaskPost().execute();

                }





            }
        });


        progressBar.setVisibility(ProgressBar.VISIBLE);

        new ItemTask().execute();


        faqSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    index = null;

                }else {
                    index = idNext.get(position - 1);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        SharedPreferences pref = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        token = pref.getString("token","");

        return view;
    }



    public  class ItemTask extends AsyncTask<Void, Void, List<QuestionNextItem>> {


        @Override
        protected List<QuestionNextItem> doInBackground(Void... voids) {

            return  new QuestionNextApi().questionNextItems();

        }

        @Override
        protected void onPostExecute(List<QuestionNextItem> questionNextItems) {
            super.onPostExecute(questionNextItems);


            Log.d(LOG_TAG, "question items = " + questionNextItems.get(0).getAddr());
            progressBar.setVisibility(ProgressBar.INVISIBLE);


            for (int i = 0; i < questionNextItems.size(); i++){

                addr.add(questionNextItems.get(i).getAddr());
                idNext.add(questionNextItems.get(i).getId_car_wash());



            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(thiscontext, R.layout.spiner_file,addr);


         //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(thiscontext, android.R.layout.spiner_file,addr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            faqSpiner.setAdapter(adapter);


        }
    }




    public  class ItemTaskPost extends AsyncTask<Void, Void, List<QuestionNextItem>> {


        @Override
        protected List<QuestionNextItem> doInBackground(Void... voids) {

            return  new QuestionNextApi().questionNextItemsPost(index,editTextFaq.getText().toString(), token);

        }

        @Override
        protected void onPostExecute(List<QuestionNextItem> questionNextItems) {
            super.onPostExecute(questionNextItems);

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            loadFragment(QuestionGood.newInstance());

        }
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.questionFragmentgroup, fragment);
        ft.commit();
    }


}

