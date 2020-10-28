package com.example.dictionary.model;

import androidx.room.Entity;

@Entity(tableName = "word")
public class Word {
    private String mPhrase;
    private String mMeaning;

    public String getPhrase() {
        return mPhrase;
    }

    public void setPhrase(String phrase) {
        mPhrase = phrase;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        mMeaning = meaning;
    }

    public Word(String phrase, String meaning) {
        mPhrase = phrase;
        mMeaning = meaning;
    }
}
