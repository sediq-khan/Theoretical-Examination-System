package applicant;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class questionModel {
    private StringProperty question, questionCategory, answerOne, answerTwo, answerThree, answerFour, correctAnswer, userAnswer;
    private IntegerProperty questionID;
    private boolean answerLater;

    public questionModel(int questionID, String question, String questionCategory, String answerOne, String answerTwo, String answerThree, String answerFour, String correctAnswer){
        this.questionID =  new SimpleIntegerProperty(questionID);
        this.question = new SimpleStringProperty(question);
        this.questionCategory = new SimpleStringProperty(questionCategory);
        this.answerOne = new SimpleStringProperty(answerOne);
        this.answerTwo = new SimpleStringProperty(answerTwo);
        this.answerThree = new SimpleStringProperty(answerThree);
        this.answerFour = new SimpleStringProperty(answerFour);
        this.correctAnswer = new SimpleStringProperty(correctAnswer);
        this.userAnswer = new SimpleStringProperty("");
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getQuestionCategory() {
        return questionCategory.get();
    }

    public StringProperty questionCategoryProperty() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory.set(questionCategory);
    }

    public String getAnswerOne() {
        return answerOne.get();
    }

    public StringProperty answerOneProperty() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne.set(answerOne);
    }

    public String getAnswerTwo() {
        return answerTwo.get();
    }

    public StringProperty answerTwoProperty() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo.set(answerTwo);
    }

    public String getAnswerThree() {
        return answerThree.get();
    }

    public StringProperty answerThreeProperty() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree.set(answerThree);
    }

    public String getAnswerFour() {
        return answerFour.get();
    }

    public StringProperty answerFourProperty() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour.set(answerFour);
    }

    public String getCorrectAnswer() {
        return correctAnswer.get();
    }

    public StringProperty correctAnswerProperty() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer.set(correctAnswer);
    }

    public int getQuestionID() {
        return questionID.get();
    }

    public IntegerProperty questionIDProperty() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID.set(questionID);
    }

    public String getUserAnswer() {
        return userAnswer.get();
    }

    public StringProperty userAnswerProperty() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = new SimpleStringProperty("");
        this.userAnswer.set(userAnswer);
    }

    public boolean isAnswerLater() {
        return answerLater;
    }

    public void setAnswerLater(boolean answerLater) {
        this.answerLater = answerLater;
    }
}
