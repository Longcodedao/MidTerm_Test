package com.midterm.volong.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "questions")
public class Questions implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String question;

    @ColumnInfo
    private Boolean answers;

    public Questions (String question, Boolean answers){
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnswers() {
        return answers;
    }

    public void setAnswers(Boolean answers) {
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
