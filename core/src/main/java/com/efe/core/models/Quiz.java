package com.efe.core.models;

import java.util.List;

import com.efe.core.models.multifield.QuizAnswer;

public interface Quiz {

    public String getQuizLength();

    // Retrive anwer strings
    public String getQuestion1();

    public String getQuestion2();

    public String getQuestion3();

    public String getQuestion4();

    public String getQuestion5();

    public String getQuestion6();

    public String getQuestion7();

    public String getQuestion8();

    public String getQuestion9();

    public String getQuestion10();

    public String getResultsCta();

    public List<QuizAnswer> getQuizAnswer();

    
}
