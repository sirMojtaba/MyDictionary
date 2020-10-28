package com.example.dictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dictionary.R;
import com.example.dictionary.database.AppDatabase;
import com.example.dictionary.model.Word;

public class WordDetailDialogFragment extends DialogFragment {
    public static final String ARGS_WORD = "word";
    private EditText mEditTextWord;
    private EditText mEditTextMeaning;
    private AppDatabase mAppDatabase;
    private Word mWord;

    public WordDetailDialogFragment() {
        // Required empty public constructor
    }

    public static WordDetailDialogFragment newInstance(Word word) {
        WordDetailDialogFragment fragment = new WordDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWord = (Word) getArguments().getSerializable(ARGS_WORD);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_word_detail_dialog, null);

        mAppDatabase = Room.databaseBuilder(view.getContext(), AppDatabase.class, "dictionary_database")
                .allowMainThreadQueries()
                .build();

        findViews(view);

        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mWord.setPhrase(mEditTextWord.getText().toString());
                        mWord.setMeaning(mEditTextMeaning.getText().toString());
                        mAppDatabase.appDao().updateWord(mWord);
                    }
                })

                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAppDatabase.appDao().deleteWord(mWord);
                    }
                })

                .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText());
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    }
                })

                .setTitle("Word Details")
                .setView(view);

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void initView() {
        mEditTextWord.setText(mWord.getPhrase());
        mEditTextMeaning.setText(mWord.getMeaning());
    }

    private void findViews(View view) {
        mEditTextWord = view.findViewById(R.id.edit_text_word_detail);
        mEditTextMeaning = view.findViewById(R.id.edit_text_meaning_detail);
    }

    private String shareText() {
        String text = "Word: " + mWord.getPhrase() + "\n" + "Meaning: " + mWord.getMeaning();
        return text;
    }
}