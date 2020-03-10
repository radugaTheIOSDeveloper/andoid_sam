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


public class QuestionGroup  extends Fragment {


    public QuestionGroup() {
    }

    public static QuestionGroup newInstance() {
        return new QuestionGroup();
    }

    final String LOG_TAG = "myLogs";
    Context thiscontext;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment_group,container, false);
        thiscontext = container.getContext();


        loadFragment(QuestionFragment.newInstance());

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.questionFragmentgroup, fragment);
        ft.commit();
    }
}
