package home;

import dbUtil.*;
import helperLib.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<displayAdmins> adminListTable;
    @FXML
    private TableColumn<displayAdmins, String> adminIDCol;
    @FXML
    private TableColumn<displayAdmins, String> adminUsernameCol;
    @FXML
    private TableColumn<displayAdmins, String> adminUserTypeCol;
    @FXML
    private TextField addAdminUsername;
    @FXML
    private PasswordField addAdminPassword;
    @FXML
    private PasswordField addAdminRepeatPassword;
    @FXML
    private Label addAdminErrorMessageLabel;
    @FXML
    private TextField deleteUserTextField;
    @FXML
    private Label deleteUserErrorLable;
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

    ObservableList<displayAdmins> oList;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addAdminErrorMessageLabel.setText("ASDF");
        this.deleteUserErrorLable.setText("ASDF");
            try {
                connection = dbConnection.getConnection("tes", "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(isDatabaseConnected()) {

                String sqlQuery = "SELECT id, username, usertype, firsttimeloginforadmin FROM users WHERE usertype = 'Admin'";
                try {
                    this.oList = FXCollections.observableArrayList();
                    resultSet = connection.createStatement().executeQuery(sqlQuery);

                    while (resultSet.next()){
                        this.oList.add(new displayAdmins(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),""));
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
            encryptedPassword = hashGenerator.generateHash(this.addAdminPassword.getText(), algorithm);
            String sqlQuery = "INSERT INTO users (username, password, usertype) VALUES (?,?,?)";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, this.addAdminUsername.getText());
            preparedStatement.setString(2, encryptedPassword);
            System.out.println(addAdminPassword.getText());
            System.out.println(encryptedPassword);
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

