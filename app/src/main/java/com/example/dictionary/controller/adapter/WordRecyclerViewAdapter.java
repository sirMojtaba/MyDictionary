package com.example.dictionary.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;

import java.util.List;

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

        public void bindWord(Word word) {
            mTextViewPhrase.setText(word.getPhrase());
            mTextViewMeaning.setText(word.getMeaning());

        }
    }
}
