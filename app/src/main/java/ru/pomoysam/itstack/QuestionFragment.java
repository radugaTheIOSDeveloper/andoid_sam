package ru.pomoysam.itstack;

import android.content.Context;
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
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {


    public QuestionFragment() {
    }

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }


    Context thiscontext;


    final String LOG_TAG = "myLogs";

    ExpandableListView elvMain;

    TextView tvInfo;
    TextView question;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_questions,container, false);
        thiscontext = container.getContext();

        tvInfo = (TextView)view.findViewById(R.id.tvInfo);

        tvInfo.setText("Возможно на Ваш вопрос уже есть ответ");
        question = view.findViewById(R.id.questionBtn);
        elvMain = view.findViewById(R.id.elvMain);

        progressBar = view.findViewById(R.id.progressBarQuestion);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        elvMain.setGroupIndicator(getResources().getDrawable(R.drawable.indicator));

        new ItemTask().execute();

        question.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Log.d(LOG_TAG, "question btn " +
                        "wafdasf");
                loadFragment(QuestionNext.newInstance());


            }
        });


        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.questionFragmentgroup, fragment);
        ft.commit();
    }


    public  class ItemTask extends AsyncTask<Void, Void, List<QuestionItem>> {


        @Override
        protected List<QuestionItem> doInBackground(Void... voids) {

            return  new QuestionApi().questionItemList();

        }

        @Override
        protected void onPostExecute(List<QuestionItem> questionItems) {
            super.onPostExecute(questionItems);


            Log.d(LOG_TAG, "question items = " + questionItems.get(0).getAnswer());

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            ArrayList<String> groups = new ArrayList<String>();
            ArrayList<ArrayList<String>> child = new ArrayList<ArrayList<String>>();


            String question = null;


            for (int i = 0; i<questionItems.size(); i++){

                ArrayList<String> answer = new ArrayList<String>();

                question = (questionItems.get(i).getQuestion());
                answer.add(questionItems.get(i).getAnswer());
                groups.add(question);
                child.add(answer);

            }


            //groups.add(question);

            QuestionAdapter adapter = new QuestionAdapter(thiscontext, groups, child);
            elvMain.setAdapter(adapter);




        }
    }



}
