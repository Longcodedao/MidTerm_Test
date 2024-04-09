package com.midterm.volong.models;

import androidx.room.Dao;
import androidx.room.Insert;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface QuestionDao {
    @Insert
    Completable insert(Questions questions);
}
