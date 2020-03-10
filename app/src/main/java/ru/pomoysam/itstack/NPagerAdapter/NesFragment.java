package ru.pomoysam.itstack.NPagerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.pomoysam.itstack.R;

public class NesFragment extends Fragment {

    public static final String TOP_IMAGE = "top image";

    Context thiscontext;

    public NesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.newsone, container, false);
        thiscontext = container.getContext();

        Bundle arguments = getArguments();

        if (arguments != null) {
            String topCardResourceId = arguments.getString(TOP_IMAGE);

            ImageView cardImageView = view.findViewById(R.id.imgPage);

            Picasso.with(getContext()).load(topCardResourceId).into(cardImageView);

            //displayValues(view, topCardResourceId);
        }


        return view;

    }


    private void displayValues(View v, String topCardResourceId) {




       // cardImageView.setImageResource(topCardResourceId);
    }

}
