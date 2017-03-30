package dev.vedroiders.huaweisupport.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import dev.vedroiders.huaweisupport.Main2Activity;
import dev.vedroiders.huaweisupport.R;

/**
 * Created by mike on 29.03.17.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    Main2Activity main2Activity;

    public SettingsFragment() {
    }


    @SuppressLint("ValidFragment")
    public SettingsFragment(Main2Activity main2Activity) {
        this.main2Activity = main2Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater
                .inflate(R.layout.fragment_settings, null);

        linearLayout.findViewById(R.id.language).setOnClickListener(this);
        linearLayout.findViewById(R.id.cache).setOnClickListener(this);
        linearLayout.findViewById(R.id.theme).setOnClickListener(this);


        return linearLayout;
    }

    @Override
    public void onClick(View view) {
//
//        switch (view.getId()){
//
//
//
//        }

        new AlertDialog.Builder(main2Activity)
                .setTitle("Sorry!:(")
                .setMessage("This is DEMO version")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
}
