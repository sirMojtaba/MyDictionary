package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initRecyclerView();
        updateUi();
        setClickListeners();
        showSubtitle();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
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

    public void updateUi() {
        if (mWordRecyclerViewAdapter == null) {
            mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(mWordList, getActivity());
            mRecyclerView.setAdapter(mWordRecyclerViewAdapter);
        } else {
            mWordRecyclerViewAdapter.setWordList(mWordList);
            mWordRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public String numberOfWords() {
        String text = null;
        if (mWordList.size() == 1)
            text = "1 word";
        else if (mWordList.size() > 1)
            text = mWordList.size() + " words";
        return text;
    }

    private void showSubtitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(numberOfWords());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}