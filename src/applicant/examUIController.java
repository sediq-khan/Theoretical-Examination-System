package applicant;

import dbUtil.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class examUIController implements Initializable {

    @FXML
    private Label examUIErrorLabel;

    @FXML
    private Button nextQuestion;
    @FXML
    private TextArea questionLanguage;
    @FXML
    private RadioButton answerOneLanguage, answerTwoLanguage, answerThreeLanguage, answerFourLanguage;
//    @FXML
//    private CheckBox answerLater;

    private Connection connection = null;
    private String sqlQuery = null;
    private ResultSet resultSet = null;
    private int i = 0;
    private int[] answerLaterQuestions;
    private int temp = 0;
    private boolean flag = false;
    private int answerLaterQuestionsCounter = 0;
    private ToggleGroup group = new ToggleGroup();

    List<questionModel> questions = new ArrayList<questionModel>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        answerOneLanguage.setToggleGroup(group);
        answerTwoLanguage.setToggleGroup(group);
        answerThreeLanguage.setToggleGroup(group);
        answerFourLanguage.setToggleGroup(group);

        try {
            connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isDatabaseConnected()){
            sqlQuery = " (select * from questions where `cat` = 'Hazard awareness' order by RAND() LIMIT 2) UNION ALL (select * from questions where `cat` = 'Driving behaviors' order by RAND() LIMIT 2) UNION ALL (select * from questions where `cat` = 'Priority' order by RAND() LIMIT 2)";
            try {
                resultSet = connection.createStatement().executeQuery(sqlQuery);
                while (resultSet.next()){
                    questions.add(new questionModel(resultSet.getInt("id"), resultSet.getString("question"), resultSet.getString("cat"), resultSet.getString("answerone"), resultSet.getString("answertwo"), resultSet.getString("answerthree"), resultSet.getString("answerfour"), resultSet.getString("rightanswer")));
                }
                this.questionLanguage.setText(questions.get(i).getQuestion());
                this.answerOneLanguage.setText(questions.get(i).getAnswerOne());
                this.answerTwoLanguage.setText(questions.get(i).getAnswerTwo());
                this.answerThreeLanguage.setText(questions.get(i).getAnswerThree());
                this.answerFourLanguage.setText(questions.get(i).getAnswerFour());
            }catch (SQLException ex){
                System.out.println(ex.getMessage() + " problem in retrieving the questions from the database for the exam.");
            }
        }
    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    @FXML
    public void nextQuestion(){
        if (group.getSelectedToggle() == null){
            examUIErrorLabel.setText("Please select at least one answer.");
            return;
        }

        answerOneLanguage.setSelected(false);
        answerTwoLanguage.setSelected(false);
        answerThreeLanguage.setSelected(false);
        answerFourLanguage.setSelected(false);

        i++;
        if ((i + 1 ) >= questions.size()){
            this.nextQuestion.setText("End Exam");
        }
        if ((i+ 1) > questions.size()){
            i--;
            loadResultsPage();
        }else {
            this.questionLanguage.setText(questions.get(i).getQuestion());
            this.answerOneLanguage.setText(questions.get(i).getAnswerOne());
            this.answerTwoLanguage.setText(questions.get(i).getAnswerTwo());
            this.answerThreeLanguage.setText(questions.get(i).getAnswerThree());
            this.answerFourLanguage.setText(questions.get(i).getAnswerFour());
            //this.answerLater.setSelected(false);
        }
    }
    public void loadResultsPage(){
        String temp = Integer.toString(examResultCalculation());


        try {
            connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isDatabaseConnected()) {
            sqlQuery = " ";
            try {
                resultSet = connection.createStatement().executeQuery(sqlQuery);
                while (resultSet.next()) {
                    questions.add(new questionModel(resultSet.getInt("id"), resultSet.getString("question"), resultSet.getString("cat"), resultSet.getString("answerone"), resultSet.getString("answertwo"), resultSet.getString("answerthree"), resultSet.getString("answerfour"), resultSet.getString("rightanswer")));
                }
                this.questionLanguage.setText(questions.get(i).getQuestion());
                this.answerOneLanguage.setText(questions.get(i).getAnswerOne());
                this.answerTwoLanguage.setText(questions.get(i).getAnswerTwo());
                this.answerThreeLanguage.setText(questions.get(i).getAnswerThree());
                this.answerFourLanguage.setText(questions.get(i).getAnswerFour());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " problem in retrieving the questions from the database for the exam.");
            }

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The exam has been completed.");
        alert.setTitle("Exam Over.");
        alert.setHeaderText(temp);
        alert.setContentText("Please note down your scores and press ok. Please consult the examinar too for further instruction.");
        alert.showAndWait();
        System.exit(333);



//        try {
//            Stage stage = (Stage) this.nextQuestion.getScene().getWindow();
//            Parent root = (Parent) FXMLLoader.load(getClass().getResource("resultPage.fxml"));
//            Scene superAdminMainUIScene = new Scene(root);
//            stage.setScene(superAdminMainUIScene);
//            stage.setTitle(temp);
//
//            stage.show();
//        }catch (Exception e){
//            e.getMessage();
//            e.printStackTrace();
//            e.getCause();
//        }
    }
    public int examResultCalculation(){
        int tempResults = 0;
        String tempA,tempB = "";
        for (int p = 0; p < questions.size(); p++){
            tempA = questions.get(p).getCorrectAnswer().trim().toString().toLowerCase();
            tempB = questions.get(p).getUserAnswer().trim().toString().toLowerCase();

            if (
                    //questions.get(p).getCorrectAnswer().trim().toString().toLowerCase() == questions.get(p).getUserAnswer().trim().toString().toLowerCase()
            tempA.equals(tempB)){
                tempResults++;
            }else{
                System.out.println("in else: " + tempA + "--" + tempB);
            }
        }
        return tempResults;
    }
    @FXML
    public void userAnswer(){
        if (group.getSelectedToggle() != null){
            RadioButton tempButton = (RadioButton) group.getSelectedToggle();

            switch (tempButton.getId()){
                case "answerOneLanguage":
                    questions.get(i).setUserAnswer("A");
                break;
                case "answerTwoLanguage":
                    questions.get(i).setUserAnswer("B");
                break;
                case "answerThreeLanguage":
                    questions.get(i).setUserAnswer("C");
                break;
                case "answerFourLanguage":
                    questions.get(i).setUserAnswer("D");

                break;
            }
        }
    }


//    public void answerLaterQuestions(){
//        System.out.println("in answerLaterQuestion functioun");
//        if (flag == false){
//            howManyAnswerLaters();
//        }
//        if (temp != 0 && answerLaterQuestionsCounter < answerLaterQuestions.length) {
//            this.answerLater.setDisable(true);
//            if (answerLaterQuestions.length > 0) {
//                System.out.println(this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getQuestionID());
//                this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getQuestion();
//                this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getAnswerOne();
//                this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getAnswerTwo();
//                this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getAnswerThree();
//                this.questions.get(answerLaterQuestions[answerLaterQuestionsCounter]).getAnswerFour();
//                answerLaterQuestionsCounter++;
//
//            }
//        }else {
//            System.out.println("no answer later questions. the exam should end now");
//        }
//        //if (this.questions.get(i).isAnswerLater()) this.answerLater.setSelected(true);
//    }

//    public void howManyAnswerLaters(){
//        flag = true;
//        System.out.println("In howManyAnswerLaters function");
//        temp = 0;
//        for (int j = 0; j  < i; j++){
//            if (this.questions.get(j).isAnswerLater()){
//                answerLaterQuestions[temp] = j;
//                temp++;
//            }
//        }
//    }

//    @FXML
//    public void answerLater(){
//        if (this.answerLater.isSelected()) {
//            this.questions.get(i).setAnswerLater(true);
//            System.out.println(this.questions.get(i).getQuestionID());
//            for (int j = 0; j <= i; j++) {
//                if (this.questions.get(j).isAnswerLater()) {
//                    System.out.println("Question ID: " + this.questions.get(j).getQuestionID());
//                }
//            }
//        }else {
//            this.questions.get(i).setAnswerLater(false);
//            for (int j = 0; j <= i; j++) {
//                if (this.questions.get(j).isAnswerLater()) {
//                    System.out.println("Question ID: " + this.questions.get(j).getQuestionID());
//                }
//            }
//        }
//    }
}