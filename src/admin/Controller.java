package admin;

import dbUtil.dbConnection;
import dbUtil.dbOperations;
import helperLib.hashGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import helperLib.randomString;

public class Controller implements Initializable {

    @FXML
    private TableView<displayApplicants> applicantListTable;
    @FXML
    private TableColumn<displayApplicants, String> applicantIDCol;
    @FXML
    private TableColumn<displayApplicants, String> applicantUsernameCol;
    @FXML
    private TableColumn<displayApplicants, String> applicantTazkiraNoCol;
    @FXML
    private TableColumn<displayApplicants, String> applicantPasswordCol;
    @FXML
    private TextField addApplicantUsername, addApplicanttazkiraNo, addApplicantPassword, deleteApplicantTazNo;
    @FXML
    private Label addApplicantErrorMessageLabel;
    @FXML
    private Label deleteApplicantErrorLable;
    @FXML
    private Button deleteApplicantButton;



    @FXML
    private Button btnExamMngt;
    @FXML
    private Button btnAccountMngt;
    @FXML
    private Button btnNetworkMngt;
    @FXML
    private Button btnQuestionMngt;
    @FXML
    private Button btnSignout;

    ObservableList<displayApplicants> oList;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = null;

    String encryptedPassword ="";
    randomString randomStringObj = new randomString();
    String password = randomStringObj.randomString(4);
    String algorithm = "SHA-256";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
                connection = dbConnection.getConnection("tes", "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(isDatabaseConnected()) {

                String sqlQuery = "SELECT user_id, name, tazkirano, password FROM applicants";
                try {
                    this.oList = FXCollections.observableArrayList();
                    resultSet = connection.createStatement().executeQuery(sqlQuery);

                    while (resultSet.next()){
                        this.oList.add(new displayApplicants(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4)));
                    }
                    applicantListTable.setItems(oList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                applicantIDCol.setCellValueFactory(new PropertyValueFactory<>("applicantID"));
                applicantUsernameCol.setCellValueFactory(new PropertyValueFactory<>("applicantName"));
                applicantTazkiraNoCol.setCellValueFactory(new PropertyValueFactory<>("applicantTazkiraNo"));
                applicantPasswordCol.setCellValueFactory(new PropertyValueFactory<>("applicantPassword"));
            }
        try{
            encryptedPassword = hashGenerator.generateHash(password, algorithm);
            this.addApplicantPassword.setText(password);
        }catch (NoSuchAlgorithmException ex){
            ex.getMessage();
        }

    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    public boolean addApplicant() throws SQLException {
        int rowCount = 0;
        if (
                this.addApplicantUsername.getText() == null ||
                        this.addApplicantUsername.getText().trim().isEmpty() ||
                        this.addApplicanttazkiraNo.getText() == null ||
                        this.addApplicanttazkiraNo.getText().trim().isEmpty()
        ){
            this.addApplicantErrorMessageLabel.setText("Either a mandatory field is left blank of the passwords do not match.");
        }else {
            connection = dbConnection.getConnection("tes", "root", "");
            dbOperations dbOperations = new dbOperations();

            if(!isDatabaseConnected()){
                this.addApplicantErrorMessageLabel.setText("Database not connected!");
                return false;
            }
            String sqlQuery = "INSERT INTO applicants (password, name, tazkirano) VALUES (?,?,?)";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, encryptedPassword);
            preparedStatement.setString(2, addApplicantUsername.getText());
            preparedStatement.setString(3, addApplicanttazkiraNo.getText());
            rowCount = dbOperations.dbOperations(this.connection, this.preparedStatement, "insert");
        }
        if (rowCount == 111){
            this.addApplicantErrorMessageLabel.setText("The Tazkira number already exists.");
            return false;
        }
        if (rowCount > 0 && rowCount != 111){
            this.addApplicantErrorMessageLabel.setText("Data Added.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Applicant Created.");
            alert.setTitle("Success!");
            alert.setHeaderText("Applicant Created.");
            alert.setContentText("Please note down following information." + "\n" + "Applicant Username: " + addApplicantUsername.getText() + "\n" +
                    "Applicant Tazkira No: " + addApplicanttazkiraNo.getText() + "\n" + "Applicant Password: " + password);
            alert.showAndWait();
            return true;
        }else{
            this.addApplicantErrorMessageLabel.setText("Data Not Added.");
            return false;
        }
    }
//
    public boolean deleteApplicant() throws SQLException {
        int rowCount = 0;
        if (this.deleteApplicantTazNo.getText() == null || this.deleteApplicantTazNo.getText().trim().isEmpty()){
            this.deleteApplicantErrorLable.setText("Please enter the admin username.");
            return false;
        }else {
            connection = dbConnection.getConnection("tes", "root", "");
            dbOperations dbOperations = new dbOperations();

            if(!isDatabaseConnected()){
                this.deleteApplicantErrorLable.setText("Database not connected!");
                return false;
            }
            String sqlQuery = "DELETE FROM applicants WHERE tazkirano = ? ";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, this.deleteApplicantTazNo.getText());
            rowCount = dbOperations.dbOperations(this.connection, this.preparedStatement, "delete");
        }
        if (rowCount > 0 && rowCount != 111){
            this.deleteApplicantErrorLable.setText("Applicant Deleted: " + this.deleteApplicantTazNo.getText());
            return true;
        }else if(rowCount == 111){
            this.deleteApplicantErrorLable.setText("This applicant has exam records, therefore it cannot be omitted");
            return false;
        }
        else{
            this.deleteApplicantErrorLable.setText("Applicant with given Tazkira number does not exist");
            return false;
        }
    }
    @FXML
    public void close_app(MouseEvent event) {
        Stage stage = (Stage) this.btnSignout.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void invokeQuestoinMngtUI() throws IOException {
        Stage stage = (Stage) this.btnQuestionMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("questionManagement.fxml"));
        Scene superAdminMainUIScene = new Scene(root);
        stage.setScene(superAdminMainUIScene);
        stage.setTitle("Super Admin Main UI");
        stage.show();
    }
    @FXML
    public void invokeExamMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("examManagement.fxml"));
        Scene superAdminMainUIScene = new Scene(root);
        stage.setScene(superAdminMainUIScene);
        stage.setTitle("Super Admin Main UI");
        stage.show();
    }
    @FXML
    public void invokeAccountMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("accountManagement.fxml"));
        Scene superAdminMainUIScene = new Scene(root);
        stage.setScene(superAdminMainUIScene);
        stage.setTitle("Super Admin Main UI");
        stage.show();
    }
    @FXML
    public void invokeNetworkMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("networkManagement.fxml"));
        Scene superAdminMainUIScene = new Scene(root);
        stage.setScene(superAdminMainUIScene);
        stage.setTitle("Super Admin Main UI");
        stage.show();
    }

}

