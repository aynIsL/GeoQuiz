package ua.kyselov.geoquiz;

/**
 * Created by Ivan on 05.11.2015.
 */
public class TrueFalse {
    private int question;
    private boolean trueQuestion;

    public TrueFalse(int question, boolean trueQuestion) {
        this.question = question;
        this.trueQuestion = trueQuestion;
    }

    public boolean isTrueQuestion() {
        return trueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }
}
