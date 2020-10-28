package com.example.dictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word {
    private String mPhrase;
    private String mMeaning;
    @PrimaryKey
    private int mId;

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Word(String phrase, String meaning) {
        mPhrase = phrase;
        mMeaning = meaning;
    }
}
