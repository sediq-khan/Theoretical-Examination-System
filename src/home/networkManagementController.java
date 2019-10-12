package home;

import dbUtil.dbConnection;
import helperLib.hashGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbUtil.*;
import javafx.stage.Stage;

public class networkManagementController implements Initializable {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @FXML
    private Label accountMngtErrorLabel;
    @FXML
    private Button btnSetConnectionConfiguration;
    @FXML
    private TextField ipURL;
    @FXML
    private TextField dbPort;
    @FXML
    private TextField databaseUsername;
    @FXML
    private PasswordField databasePassword;
    @FXML
    private Button btnOverview, btnExamMngt, btnAccountMngt, btnNetworkMngt, btnQuestionMngt;

    private configureDB configureDBObj = new configureDB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String dbIP, dbUsername, dbPassword, dbPort;
        dbIP = configureDBObj.getDbConfigurationIP();
        System.out.println(dbIP);
        dbUsername = configureDBObj.getDbConfigurationUsername();
        System.out.println(dbUsername);
        dbPassword = configureDBObj.getDbConfigurationPassword();
        System.out.println(dbPassword);
        dbPort = configureDBObj.getDbConfigurationDbPort();
        System.out.println(dbPort);
        if(configureDBObj.getDbConfigurationDbPort() == null | configureDBObj.getDbConfigurationIP() == null | configureDBObj.getDbConfigurationUsername() == null){
            accountMngtErrorLabel.setText("One or more of the required fields are empty.");
        }else {
            this.ipURL.setText(configureDBObj.getDbConfigurationIP());
            this.dbPort.setText(configureDBObj.getDbConfigurationDbPort());
            this.databaseUsername.setText(configureDBObj.getDbConfigurationUsername());
            this.databasePassword.setText(configureDBObj.getDbConfigurationPassword());
        }
    }

    @FXML
    public void setDBConfiguration(){
        if(!ipURL.getText().isEmpty() && !databaseUsername.getText().isEmpty() && !dbPort.getText().isEmpty()){
            try {
                if(!configureDBObj.saveDbConfiguration(ipURL.getText(), dbPort.getText(), databaseUsername.getText(), databasePassword.getText())) {
                    accountMngtErrorLabel.setText("There was a problem storing the configuration");
                }else {
                    accountMngtErrorLabel.setText("The database configuration was successfully saved.");
                }
            }catch (FileNotFoundException e){
                e.getMessage();
                e.getStackTrace();
                e.getCause();
            }
        }else {
            accountMngtErrorLabel.setText("Please enter all required fields.");
        }
    }
    @FXML
    public void testConnection(){
        boolean results = false;
        try {
            connection = dbConnection.getConnection("tes", configureDBObj.getDbConfigurationDbPort(), configureDBObj.getDbConfigurationIP(), configureDBObj.getDbConfigurationUsername(), configureDBObj.getDbConfigurationPassword());
            results = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (results) {
            accountMngtErrorLabel.setText("Test Successfull.");
        }else {
            accountMngtErrorLabel.setText("Test Not Successfull.");
        }
    }
    public boolean isDatabaseConnected(){ return this.connection != null; }
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
    public void invokeOverviewMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("Home.fxml"));
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
}