package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.R;
import com.example.dictionary.controller.adapter.WordRecyclerViewAdapter;
import com.example.dictionary.database.AppDatabase;
import com.example.dictionary.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainFragment extends Fragment {

    public static final String TAG_NEW_WORD_DIALOG = "new_word_dialog";
    public static final int REQUEST_CODE_MAIN_FRAGMENT = 0;
    private RecyclerView mRecyclerView;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;
    private List<Word> mWordList;
    private FloatingActionButton mFloatingActionButton;
    private AppDatabase mAppDatabase;


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
        mAppDatabase = Room.databaseBuilder(getActivity(), AppDatabase.class, "dictionary_database")
                .allowMainThreadQueries()
                .build();
        mWordList = mAppDatabase.appDao().getWordList();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initRecyclerView();
        setClickListeners();
        showSubtitle();
        Log.d("tag", "onCreateView");
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
                newWordDialogFragment.setTargetFragment(MainFragment.this, REQUEST_CODE_MAIN_FRAGMENT);
                newWordDialogFragment.show(getFragmentManager(), TAG_NEW_WORD_DIALOG);

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(mAppDatabase.appDao().getWordList(), getActivity());
        mRecyclerView.setAdapter(mWordRecyclerViewAdapter);
    }

    public void updateUi() {
        if (mWordRecyclerViewAdapter == null) {
            mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(mAppDatabase.appDao().getWordList(), getActivity());
            mRecyclerView.setAdapter(mWordRecyclerViewAdapter);
        } else {
            mWordRecyclerViewAdapter.setWordList(mAppDatabase.appDao().getWordList());
            mWordRecyclerViewAdapter.notifyDataSetChanged();
        }
        showSubtitle();
    }

    public String numberOfWords() {
        String text = null;
        if (mAppDatabase.appDao().getWordList().size() == 1)
            text = "1 word";
        else if (mAppDatabase.appDao().getWordList().size() > 1)
            text = mAppDatabase.appDao().getWordList().size() + " words";
        return text;
    }

    private void showSubtitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(numberOfWords());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_MAIN_FRAGMENT) {
            updateUi();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.item_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mWordRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}