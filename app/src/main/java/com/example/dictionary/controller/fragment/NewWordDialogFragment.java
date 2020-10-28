package com.example.dictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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


public class NewWordDialogFragment extends DialogFragment {
    private EditText mEditTextWord;
    private EditText mEditTextMeaning;
    private Context mContext;





    public NewWordDialogFragment() {
        // Required empty public constructor
    }

    public static NewWordDialogFragment newInstance() {
        NewWordDialogFragment fragment = new NewWordDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_word_dialog, null);
        AppDatabase db = Room.databaseBuilder(view.getContext(), AppDatabase.class, "dictionary_database")
                .allowMainThreadQueries()
                .build();

        mEditTextWord = view.findViewById(R.id.edit_text_word);
        mEditTextMeaning = view.findViewById(R.id.edit_text_meaning);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Word word = new Word(mEditTextWord.getText().toString(), mEditTextMeaning.getText().toString());
                db.appDao().insertWord(word);
            }
        })
                .setTitle("New word")
                .setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}