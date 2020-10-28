package com.example.dictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "word")
public class Word implements Serializable {
    private String mPhrase;
    private String mMeaning;
    @PrimaryKey(autoGenerate = true)
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
