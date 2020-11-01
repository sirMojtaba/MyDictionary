package com.example.dictionary.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dictionary.model.Word;

import java.util.List;

@Dao
public interface AppDao {
    @Query("select * from word")
    List<Word> getWordList();

    @Query("SELECT * FROM word WHERE word LIKE :search " + "OR meaning LIKE :search")
    List<Word> findWord(String search);

    @Insert
    void insertWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Update
    void updateWord(Word word);
}
