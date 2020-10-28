package com.example.dictionary.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dictionary.model.Word;

@Database(entities = Word.class, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();
}
