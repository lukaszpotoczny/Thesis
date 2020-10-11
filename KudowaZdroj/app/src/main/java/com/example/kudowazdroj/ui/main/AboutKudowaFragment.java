package com.example.kudowazdroj.ui.main;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class AboutKudowaFragment extends Fragment {

    TextView textView1;
    TextView textView2;
    TextView textView3;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_1);
        View root = inflater.inflate(R.layout.about_kudowa_fragment, container, false);

       // textView1 = root.findViewById(R.id.about_kudowa_text_1);

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }*/

        return root;

    }
}
