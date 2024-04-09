package com.midterm.volong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.midterm.volong.databinding.ActivityMainBinding;
import com.midterm.volong.models.QuestionDatabase;
import com.midterm.volong.models.Questions;
import com.midterm.volong.viewmodels.QuestionViewModel;
import com.midterm.volong.viewmodels.QuestionsRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private QuestionViewModel questionViewModel;
    private int currentQuestionIndex;
    private QuestionsRepository questionsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        QuestionDatabase questionDatabase = QuestionDatabase.getDatabase(this);
        questionsRepository = new QuestionsRepository(questionDatabase.questionsDao());

        questionViewModel = QuestionViewModel.getInstance(this);


        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
        }

        displayQuestion();

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNext();
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPrev();
            }
        });

        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean stateTrue = questionViewModel.isButtonTrueClicked();
                boolean stateFalse = questionViewModel.isFalseButtonClick();
                if (!stateTrue){
                    binding.buttonTrue.setBackgroundColor(Color.BLUE);
                    questionViewModel.setMarkedQuestions(currentQuestionIndex, true);

                    if (stateFalse){
                        binding.buttonFalse.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.button_color));
                        questionViewModel.setFalseButtonClick(!stateFalse);
                    }

                } else {
                    binding.buttonTrue.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.button_color));
                    questionViewModel.setMarkedQuestions(currentQuestionIndex, false);
                }

                stateTrue = !stateTrue;
                questionViewModel.setTrueButtonClick(stateTrue);

                if (stateTrue)
                    questionViewModel.setActionQuestion(currentQuestionIndex, true);
                else
                    questionViewModel.setActionQuestion(currentQuestionIndex, null);
            }
        });

        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean stateTrue = questionViewModel.isButtonTrueClicked();
                boolean stateFalse = questionViewModel.isFalseButtonClick();
                if (!stateFalse){
                    binding.buttonFalse.setBackgroundColor(Color.RED);
                    questionViewModel.setMarkedQuestions(currentQuestionIndex, true);

                    if (stateTrue){
                        binding.buttonTrue.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.button_color));
                        questionViewModel.setTrueButtonClick(!stateTrue);
                    }

                } else {
                    binding.buttonFalse.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.button_color));
                    questionViewModel.setMarkedQuestions(currentQuestionIndex, false);
                }
                stateFalse = !stateFalse;
                questionViewModel.setFalseButtonClick(stateFalse);

                if (stateFalse)
                    questionViewModel.setActionQuestion(currentQuestionIndex, false);
                else
                    questionViewModel.setActionQuestion(currentQuestionIndex, null);
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Questions> questionAnswers = questionViewModel.saveAll();

                for (Questions questions: questionAnswers){
                    questionsRepository.saveQuestion(questions)
                            .subscribe(() -> {
                                // Question saved successfully
                                Toast.makeText(MainActivity.this, "Question saved", Toast.LENGTH_SHORT).show();
                            }, throwable -> {
                                Log.e("MainActivity", "Error saving question: " + throwable.getMessage());
                                Toast.makeText(MainActivity.this, "Error saving question", Toast.LENGTH_SHORT).show();
                            });

                }
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                intent.putExtra("questionArrays", (Serializable) questionAnswers);

                startActivity(intent);
            }
        });
    }

    private void displayQuestion(){
        String question = questionViewModel.getQuestionIndex(currentQuestionIndex);
        binding.mainQuestion.setText(question);
        binding.buttonTrue.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                R.color.button_color));
        binding.buttonFalse.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                R.color.button_color));
    }

    private void displayNext(){
        int size = questionViewModel.getTotalQuestions();
        if (currentQuestionIndex < size - 1){
            currentQuestionIndex++;
            displayQuestion();
        }
        else{
            Toast.makeText(this, "You've reached at the end", Toast.LENGTH_SHORT);
        }
    }

    private void displayPrev(){
        if (currentQuestionIndex > 0){
            currentQuestionIndex--;
            displayQuestion();
        }
        else{
            Toast.makeText(this, "You've reached at the beginning", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
//        outState.putBoolean("");
    }

    private void toggleButtonBackground(Button button, boolean isFirstClick) {
        if (isFirstClick) {
            button.setBackgroundColor(Color.BLUE);
        } else {
            button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }
}