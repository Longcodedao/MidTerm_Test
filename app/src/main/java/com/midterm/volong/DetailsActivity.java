package com.midterm.volong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.midterm.volong.databinding.ActivityDetailsBinding;
import com.midterm.volong.models.Questions;
import com.midterm.volong.viewmodels.QuestionAdapter;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    public QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvQuestions.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        if (intent != null){
            List<Questions> questionsAnswers = (List<Questions>) intent.getSerializableExtra("questionArrays");
            questionAdapter = new QuestionAdapter(questionsAnswers);
            binding.rvQuestions.setAdapter(questionAdapter);
        }


    }
}