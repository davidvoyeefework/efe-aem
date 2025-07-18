package com.efe.core.models;

public interface Quiz {

    // Intro Pane

    public String getIntroHeadline();

    public String getIntroSubheadline();

    public String getIntroCta();    

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

    // Answers

        // 0 Selections
        public String getIcon();

        public String getAnswerCtaLink();

        public String getAnswerCtaText();

        public String getHeadline();

        public String getBodyCopy();

        // 1-2 Selections
        public String getIcon12();

        public String getAnswerCtaLink12();

        public String getAnswerCtaText12();

        public String getHeadline12();

        public String getBodyCopy12();

        // 3-4 Selections
        public String getIcon34();

        public String getAnswerCtaLink34();

        public String getAnswerCtaText34();

        public String getHeadline34();

        public String getBodyCopy34();        

        //  5 - 6 selections
        public String getIcon56();

        public String getAnswerCtaLink56();

        public String getAnswerCtaText56();

        public String getHeadline56();

        public String getBodyCopy56();    
        
        //  7 - 8 selections
        public String getIcon78();

        public String getAnswerCtaLink78();

        public String getAnswerCtaText78();

        public String getHeadline78();

        public String getBodyCopy78();        

        // 9 - 10 selections
        public String getIcon910();

        public String getAnswerCtaLink910();

        public String getAnswerCtaText910();

        public String getHeadline910();

        public String getBodyCopy910();    
        
        public String getTheme();



    
}
