package com.example.dictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.dictionary.controller.fragment.MainFragment;
import com.example.dictionary.R;
import com.example.dictionary.controller.fragment.WordDetailDialogFragment;

public class MainActivity extends AppCompatActivity implements WordDetailDialogFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onWordClicked() {
        for (int i = 0; i <getSupportFragmentManager().getFragments().size() ; i++) {
            if (getSupportFragmentManager().getFragments().get(i) instanceof MainFragment)
                ((MainFragment) getSupportFragmentManager().getFragments().get(i)).updateUi();
        }
    }
}