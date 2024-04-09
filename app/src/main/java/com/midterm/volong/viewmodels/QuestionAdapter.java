package com.midterm.volong.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.volong.R;
import com.midterm.volong.models.Questions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{

    private static List<Questions> listQuestions;

    public QuestionAdapter(List<Questions> questions){
        this.listQuestions = questions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvQuestion;
        public TextView tvAnswer;
        public ViewHolder(View view) {
            super(view);
            tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            tvAnswer = (TextView) view.findViewById(R.id.tvAnswers);

        }
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        holder.tvQuestion.setText(listQuestions.get(position).getQuestion());

        Boolean answer = listQuestions.get(position).getAnswers();
        if (answer == null){
            holder.tvAnswer.setText("NO ANSWERS");

        } else if (answer == false) {
            holder.tvAnswer.setText("FALSE");
        } else if (answer == true){
            holder.tvAnswer.setText("TRUE");
        }
    }

    @Override
    public int getItemCount() {
        return listQuestions.size();
    }
}
