package home;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class questionManagementModel {
    private StringProperty questionID, question, category, answerOne, answerTwo, answerThree, answerFour, rightAnswer, hasImage, hasMedia;
//    private ImageView photo;

    public questionManagementModel(String questionID, String question, String category, String answerOne, String answerTwo, String answerThree, String answerFour, String rightAnswer, String hasImage, String hasMedia
//            , ImageView photo
    ){
        this.questionID = new SimpleStringProperty(questionID);
        this.question =new SimpleStringProperty(question);
        this.category = new  SimpleStringProperty(category);
        this.answerOne = new  SimpleStringProperty(answerOne);
        this.answerTwo = new  SimpleStringProperty(answerTwo);
        this.answerThree = new  SimpleStringProperty(answerThree);
        this.answerFour = new  SimpleStringProperty(answerFour);
        this.rightAnswer = new  SimpleStringProperty(rightAnswer);
//        this.photo = photo;
        this.hasImage = new SimpleStringProperty(hasImage);
        this.hasMedia = new SimpleStringProperty(hasMedia);
    }

//    public ImageView getPhoto(){
//        return photo;
//    }
//
//    public void setPhoto(ImageView photo)
//    {
//        this.photo =  photo;
//
//    }

    public String getQuestionID() {
        return questionID.get();
    }

    public StringProperty questionIDProperty() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID.set(questionID);
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

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getRightAnswer() {
        return rightAnswer.get();
    }

    public StringProperty rightAnswerProperty() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer.set(rightAnswer);
    }

    public String getHasImage() {
        return hasImage.get();
    }

    public StringProperty hasImageProperty() {
        return hasImage;
    }

    public void setHasImage(String hasImage) {
        this.hasImage.set(hasImage);
    }

    public String getHasMedia() {
        return hasMedia.get();
    }

    public StringProperty hasMediaProperty() {
        return hasMedia;
    }

    public void setHasMedia(String hasMedia) {
        this.hasMedia.set(hasMedia);
    }
}
