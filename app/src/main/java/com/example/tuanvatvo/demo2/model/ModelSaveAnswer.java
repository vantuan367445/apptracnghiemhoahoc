package com.example.tuanvatvo.demo2.model;

import java.io.Serializable;

public class ModelSaveAnswer implements Serializable {
    // isAnswer  // câu hỏi này đã được người thi trả lời hay chưa  nếu trả lời rồi
    // thì tô đậm nên
    boolean isAnswer;
    int number_answer; // đây là đáp án nào được chọn 1,2,3,4

    public ModelSaveAnswer(boolean isAnswer, int number_answer) {
        this.isAnswer = isAnswer;
        this.number_answer = number_answer;
    }

    public ModelSaveAnswer() {
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public int getNumber_answer() {
        return number_answer;
    }

    public void setNumber_answer(int number_answer) {
        this.number_answer = number_answer;
    }
}
