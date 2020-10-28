package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.controller.adapter.WordRecyclerViewAdapter;
import com.example.dictionary.database.AppDatabase;
import com.example.dictionary.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    public static final String TAG_NEW_WORD_DIALOG = "new_word_dialog";
    private RecyclerView mRecyclerView;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;
    private List<Word> mWordList;
    private FloatingActionButton mFloatingActionButton;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "dictionary_database")
                .allowMainThreadQueries()
                .build();
        mWordList = db.appDao().getWordList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initRecyclerView();

        setClickListeners();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mFloatingActionButton = view.findViewById(R.id.floating_action_button);
    }

    private void setClickListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewWordDialogFragment newWordDialogFragment = NewWordDialogFragment.newInstance();
                newWordDialogFragment.show(getFragmentManager(), TAG_NEW_WORD_DIALOG);

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(mWordList, getActivity());
        mRecyclerView.setAdapter(mWordRecyclerViewAdapter);
    }
}