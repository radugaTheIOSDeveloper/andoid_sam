package ru.pomoysam.itstack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PageFragmentNews extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    final static String TOP_IMAGE = "top image";

    int pageNumber;
    Context thiscontext;
    ImageView tvPage;

    public PageFragmentNews() {
    }

    static PageFragmentNews newInstance(int page) {

        PageFragmentNews pageFragment = new PageFragmentNews();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);


        return pageFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsone, null);
        thiscontext = container.getContext();

         tvPage = (ImageView) view.findViewById(R.id.imgPage);

         Picasso.with(thiscontext).load("https://app.pomoysam.ru/media/test2.png").into(tvPage);


        return view;
    }









}
