package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;
    private List<Word> mWordList = new ArrayList<>();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        initRecyclerView();

        setClickListeners();

        mWordList.add(new Word("car", "ماشین"));
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

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(mWordList);
        mRecyclerView.setAdapter(mWordRecyclerViewAdapter);
    }




    public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder> {

        private List<Word> mWordList;

        public WordRecyclerViewAdapter(List<Word> wordList) {
            mWordList = wordList;
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            return new WordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            Word word = mWordList.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }

        public class WordViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextViewPhrase;
            private TextView mTextViewMeaning;

            public WordViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewPhrase = itemView.findViewById(R.id.text_view_word);
                mTextViewMeaning = itemView.findViewById(R.id.text_view_meaning);
            }
            public void bindWord(Word word){
                mTextViewPhrase.setText(word.getPhrase());
                mTextViewMeaning.setText(word.getMeaning());

            }
        }
    }
}