package com.example.dictionary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "word")
public class Word implements Serializable {
    @ColumnInfo (name = "word")
    private String mWord;
    @ColumnInfo(name = "meaning")
    private String mMeaning;
    @PrimaryKey(autoGenerate = true)
    private int mId;

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
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

    public Word(String word, String meaning) {
        mWord = word;
        mMeaning = meaning;
    }
}
