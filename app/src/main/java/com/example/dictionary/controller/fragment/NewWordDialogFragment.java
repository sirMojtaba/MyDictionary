package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.database.AppDatabase;
import com.example.dictionary.model.Word;
import com.google.android.material.snackbar.Snackbar;


public class NewWordDialogFragment extends DialogFragment {
    public static final String EXTRA_NEW_WORD = "new_word";
    private EditText mEditTextWord;
    private EditText mEditTextMeaning;
    private AppDatabase mAppDatabase;

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
        mAppDatabase = Room.databaseBuilder(view.getContext(), AppDatabase.class, "dictionary_database")
                .allowMainThreadQueries()
                .build();

        findViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mEditTextWord.getText().length() == 0 || mEditTextMeaning.getText().length() == 0)
                            Toast.makeText(getActivity(), "Fill both of the blanks.", Toast.LENGTH_SHORT).show();
                        else {
                            Word word = new Word(mEditTextWord.getText().toString(), mEditTextMeaning.getText().toString());
                            mAppDatabase.appDao().insertWord(word);
                            Fragment fragment = getTargetFragment();
                            Intent intent = new Intent();
//                            intent.putExtra(EXTRA_NEW_WORD, word);
                            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }
                    }
                })
                .setTitle("New word")
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view) {
        mEditTextWord = view.findViewById(R.id.edit_text_word);
        mEditTextMeaning = view.findViewById(R.id.edit_text_meaning);
    }
}