package com.android.personalityquiz;

public class Quiz {
    private String Question;
    private String OptionA;
    private String OptionB;
    private String OptionC;
    private String Answer;

    public Quiz(String question, String optionA, String optionB, String optionC, String answer) {
        Question = question;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        Answer = answer;
    }
    public Quiz() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
