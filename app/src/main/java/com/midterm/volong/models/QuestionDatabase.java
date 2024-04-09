package com.midterm.volong.models;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    public abstract QuestionDao questionsDao();

    private static volatile QuestionDatabase INSTANCE;

    public static QuestionDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,
                    QuestionDatabase.class, "questions_lol").build();
        }

        return INSTANCE;
    }
}
