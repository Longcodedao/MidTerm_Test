package com.midterm.volong.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.midterm.volong.models.Questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends ViewModel {

    private static QuestionViewModel instance;
    private List<String> questions;
    private List<Boolean> markedQuestions;
    private List<Boolean> resultQuestions;

//    public List<Questions> questionsAnswers;

    private boolean trueButtonClick = false;
    private boolean falseButtonClick = false;

    public QuestionViewModel(Context context){
        questions = readQuestionsFromTextFile(context,"questions.txt");
        int questions_length = questions.size();
        markedQuestions = new ArrayList<Boolean>(questions_length); // Initialize markedQuestions with initial size
        resultQuestions = new ArrayList<Boolean>(questions_length);

        for (int i = 0; i < questions_length; i ++){
            markedQuestions.add(false);
            resultQuestions.add(null);
        }
        Log.d("DEBUG", "Length of the markedQuestions " + markedQuestions.size());
    }

    public static synchronized QuestionViewModel getInstance(Context context){
        if (instance == null){
            instance = new QuestionViewModel(context);
        }
        return instance;
    }

    public String getQuestionIndex(int currentIndex){
        String currentQuestion = questions.get(currentIndex);
        return currentQuestion;
    }

    public Boolean getActionQuestion(int currentIndex){
        Boolean stateQuestion = markedQuestions.get(currentIndex);
        return stateQuestion;
    }

    public void setActionQuestion(int currentIndex, Boolean action){
        resultQuestions.set(currentIndex, action);
    }

    public void setMarkedQuestions(int currentIndex, boolean action){
        markedQuestions.set(currentIndex, action);
    }

    public int getTotalQuestions(){
        return questions.size();
    }

    private List<String> readQuestionsFromTextFile(Context context, String fileName){
        List<String> questions = new ArrayList<>();

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading the questions", Toast.LENGTH_SHORT);
        }

        return questions;
    }

    public boolean isButtonTrueClicked(){
        return trueButtonClick;
    }

    public boolean isFalseButtonClick(){
        return falseButtonClick;
    }

    public void setTrueButtonClick(boolean buttonClick){
        trueButtonClick = buttonClick;
    }

    public void setFalseButtonClick(boolean buttonClick){
        falseButtonClick = buttonClick;
    }


    // Save Answers
    public List<Questions> saveAll(){
        List<Questions> questionsAnswers = new ArrayList<Questions>();

        for (int i = 0; i < questions.size(); i++){
            String question = questions.get(i);
            Boolean answer = resultQuestions.get(i);

            Questions question_answer = new Questions(question, answer);
            questionsAnswers.add(question_answer);

        }
        return questionsAnswers;
    }
}
