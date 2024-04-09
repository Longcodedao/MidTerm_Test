package com.midterm.volong.viewmodels;

import com.midterm.volong.models.QuestionDao;
import com.midterm.volong.models.Questions;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuestionsRepository {
    private final QuestionDao questionsDao;

    public QuestionsRepository(QuestionDao questionsDao) {
        this.questionsDao = questionsDao;
    }

    public Completable saveQuestion(Questions question) {
        return questionsDao.insert(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

