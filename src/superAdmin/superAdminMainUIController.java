package superAdmin;

import helperLib.hashGenerator;
import dbUtil.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class superAdminMainUIController implements Initializable {
    @FXML
    private Button questionMngtButtonQuestionUI;
    @FXML
    private Button examMngtButtonQuestionUI;
    @FXML
    private Button accountMngtButtonQuestionUI;
    @FXML
    private Button networkMngtButtonQuestionUI;
    @FXML
    private Button addAdmin;
    @FXML
    private TextField addAdminUsername;
    @FXML
    private TextField addAdminPassword;
    @FXML
    private TextField addAdminRepeatPassword;
    @FXML
    private Label addAdminErrorMessageLabel;
    @FXML
    private Button deleteUserButton;
    @FXML
    private TextField deleteUserTextField;
    @FXML
    private Label deleteUserErrorLable;


    @FXML
    private TableView<superAdminMainUIModel> adminListTable;
    @FXML
    private TableColumn<superAdminMainUIModel, String> adminIDCol;
    @FXML
    private TableColumn<superAdminMainUIModel, String> adminUsernameCol;
    @FXML
    private TableColumn<superAdminMainUIModel, String> adminUserTypeCol;

    ObservableList<superAdminMainUIModel> oList;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(isDatabaseConnected()) {

            String sqlQuery = "SELECT id, username, usertype FROM users";
            try {
                this.oList = FXCollections.observableArrayList();
                resultSet = connection.createStatement().executeQuery(sqlQuery);

                    while (resultSet.next()){
                        this.oList.add(new superAdminMainUIModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                    }
                    adminListTable.setItems(oList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            adminIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            adminUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            adminUserTypeCol.setCellValueFactory(new PropertyValueFactory<>("userType"));
        }
    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    public boolean addAdmin() throws SQLException, NoSuchAlgorithmException {
        int rowCount = 0;
        if (
                this.addAdminUsername.getText() == null ||
                        this.addAdminUsername.getText().trim().isEmpty() ||
                        this.addAdminPassword.getText() == null ||
                        this.addAdminPassword.getText().trim().isEmpty() ||
                        this.addAdminRepeatPassword.getText() == null ||
                        this.addAdminRepeatPassword.getText().trim().isEmpty() ||
                        !(this.addAdminPassword.getText().equals(this.addAdminRepeatPassword.getText()))
        ){
            this.addAdminErrorMessageLabel.setText("Either a mandatory field is left blank of the passwords do not match.");
        }else {
            connection = dbConnection.getConnection("tes", "root", "");
            dbOperations dbOperations = new dbOperations();

            if(!isDatabaseConnected()){
                this.addAdminErrorMessageLabel.setText("Database not connected!");
                return false;
            }
            String encryptedPassword;
            String algorithm = "SHA-256";
            encryptedPassword = hashGenerator.generateHash(this.addAdminPassword.toString(), algorithm);
            String sqlQuery = "INSERT INTO users (username, password, usertype) VALUES (?,?,?)";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, this.addAdminUsername.getText());
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.setString(3, "Admin");

            rowCount = dbOperations.dbOperations(this.connection, this.preparedStatement, "insert");
        }
        if (rowCount > 0){
            this.addAdminErrorMessageLabel.setText("Data Added.");
            return true;
        }else{
            this.addAdminErrorMessageLabel.setText("Data Not Added.");
            return false;
        }
    }

    public boolean deleteUser() throws SQLException {
        int rowCount = 0;
        if (
                this.deleteUserTextField.getText() == null ||
                        this.deleteUserTextField.getText().trim().isEmpty()
        ){
            this.deleteUserErrorLable.setText("Please enter the admin username.");
            return false;
        }else {
            connection = dbConnection.getConnection("tes", "root", "");
            dbOperations dbOperations = new dbOperations();

            if(!isDatabaseConnected()){
                this.deleteUserErrorLable.setText("Database not connected!");
                return false;
            }

            String sqlQuery = "DELETE FROM users WHERE username = ? ";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, this.deleteUserTextField.getText());
            rowCount = dbOperations.dbOperations(this.connection, this.preparedStatement, "delete");
        }
        if (rowCount > 0){
            this.deleteUserErrorLable.setText("User Deleted: " + this.deleteUserTextField.getText());
            return true;
        }else{
            this.deleteUserErrorLable.setText("Username does not exist");
            return false;
        }
    }

    public void questionMngtButtonQuestionUI(ActionEvent event){

    }
    public void examMngtButtonQuestionUI(ActionEvent event) {
        System.out.println("1");
        Stage stage = (Stage) examMngtButtonQuestionUI.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        System.out.println("2");
        Parent anchorPane = null;
        Parent examRoot = null;
        System.out.println("3");
        FXMLLoader fxmlLoader = new FXMLLoader();
        System.out.println("4");
        try {
            //anchorPane = (Parent) fxmlLoader.load(getClass().getClassLoader().getResource("\\examManagement.fxml").openStream());
            examRoot = (Parent) fxmlLoader.load(getClass().getClassLoader().getResource("\\superAdmin\\superAdminMainUI.fxml").openStream());
            System.out.println("5");
        }catch (Exception ex){
            System.out.println(ex.getMessage() + "asdfas" + " -- " + ex.getCause());
            System.out.println("6");
        }
        System.out.println("7");
        //examManagementController examManagementControllerObj = (examManagementController) fxmlLoader.getController();
        System.out.println("8");
        Scene scene = new Scene(examRoot);
        System.out.println("9");
        newStage.setScene(scene);
        System.out.println("10");
        newStage.show();
        System.out.println("11");
    }
    public void accountMngtButtonQuestionUI(ActionEvent event){
    }
    public void networkMngtButtonQuestionUI(ActionEvent event){
    }

}
