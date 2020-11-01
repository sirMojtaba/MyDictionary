package com.example.dictionary.controller.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.controller.fragment.WordDetailDialogFragment;
import com.example.dictionary.model.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder>
        implements Filterable {

    public static final String TAG_WORD_DETAIL = "word_detail";
    private List<Word> mWordList;
    private List<Word> mWordListAll;
    private Activity mActivity;


    public void setWordList(List<Word> wordList) {
        mWordList = wordList;
    }

    public WordRecyclerViewAdapter(List<Word> wordList, Activity activity) {
        mWordList = wordList;
        mWordListAll = new ArrayList<>(wordList);
        mActivity = activity;
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

        if (position % 2 != 0)
            holder.mLayoutRow.setBackgroundResource(R.drawable.odd_row_background);
        else {
            holder.mLayoutRow.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Word> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
//                notifyDataSetChanged();
                filteredList.addAll(mWordListAll);
            }
            else {
                for (Word word : mWordListAll) {
                    if (word.getWord().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || word.getMeaning().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    filteredList.add(word);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mWordList.clear();
            mWordList.addAll((Collection<? extends Word>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewPhrase;
        private TextView mTextViewMeaning;
        private RelativeLayout mLayoutRow;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewPhrase = itemView.findViewById(R.id.text_view_word);
            mTextViewMeaning = itemView.findViewById(R.id.text_view_meaning);
            mLayoutRow = itemView.findViewById(R.id.layout_row);

            mLayoutRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WordDetailDialogFragment wordDetailDialogFragment = WordDetailDialogFragment.newInstance(mWordList.get(getAdapterPosition()));
                    wordDetailDialogFragment.show(((AppCompatActivity) mActivity).getSupportFragmentManager(), TAG_WORD_DETAIL);
                }
            });
        }

        public void bindWord(Word word) {
            mTextViewPhrase.setText(word.getWord());
            mTextViewMeaning.setText(word.getMeaning());
        }
    }
}
