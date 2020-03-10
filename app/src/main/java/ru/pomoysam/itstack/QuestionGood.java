package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class QuestionGood extends Fragment {

    public QuestionGood() {
    }

    public static QuestionGood newInstance() {
        return new QuestionGood();
    }
    Context thiscontext;
    Button btnGood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_good, container, false);
        thiscontext = container.getContext();

        btnGood = view.findViewById(R.id.button_good_quest);


        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(QuestionFragment.newInstance());

            }
        });


        return view;
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.questionFragmentgroup, fragment);
        ft.commit();
    }



}
