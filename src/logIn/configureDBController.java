package logIn;

import dbUtil.configureDB;
import dbUtil.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class configureDBController implements Initializable {
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
    private TextField databasePort;
    @FXML
    private TextField databaseUsername;
    @FXML
    private PasswordField databasePassword;
    @FXML
    private Button btnSignout;


    private configureDB configureDBObj = new configureDB();

    public void close_app(MouseEvent event) {
        Stage stage = (Stage) ipURL.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int flag = 0;
        if (flag == 0 ){
            flag = 1;
            return;
        }
        if(configureDBObj.getDbConfigurationDbPort() != null && configureDBObj.getDbConfigurationIP() != null && configureDBObj.getDbConfigurationUsername() != null){
            this.ipURL.setText(configureDBObj.getDbConfigurationIP());
            this.databasePort.setText(configureDBObj.getDbConfigurationDbPort());
            this.databaseUsername.setText(configureDBObj.getDbConfigurationUsername());
            this.databasePassword.setText(configureDBObj.getDbConfigurationPassword());
        }
    }

//    @FXML
//    public void setDBConfiguration(){
//        if(ipURL.getText().isEmpty() | databaseUsername.getText().isEmpty() | databasePort.getText().isEmpty()){
//            accountMngtErrorLabel.setText("Please enter all required fields.");
//
//        }else {
//            try {
//                if(!configureDBObj.saveDbConfiguration(ipURL.getText(), databasePort.getText(), databaseUsername.getText(), databasePassword.getText())) {
//                    accountMngtErrorLabel.setText("There was a problem storing the configuration");
//                }else {
//                    accountMngtErrorLabel.setText("The database configuration was successfully saved.");
//                }
//            }catch (FileNotFoundException e){
//                e.getMessage();
//                e.getStackTrace();
//                e.getCause();
//            }
//        }
//    }
    @FXML
    public void testConnection(){
        boolean results = false;
        //if(configureDBObj.getDbConfigurationDbPort() == null | configureDBObj.getDbConfigurationIP() == null | configureDBObj.getDbConfigurationUsername() == null){
        if(ipURL.getText().isEmpty() | databasePort.getText().isEmpty() | databaseUsername.getText().isEmpty()){
            accountMngtErrorLabel.setText("One or more of the required fields are empty.");
            return;
        }
        try {
            //getConnection(String dbName, String dbPort, String dbURL, String dbUsername, String dbPassword)
            configureDBObj.saveDbConfiguration(ipURL.getText(), databasePort.getText(), databaseUsername.getText(), databasePassword.getText());
            connection = dbConnection.getConnection("tes",
                    configureDBObj.getDbConfigurationDbPort(),
                    configureDBObj.getDbConfigurationIP(),
                    configureDBObj.getDbConfigurationUsername(),
                    configureDBObj.getDbConfigurationPassword());
            results = true;
        }catch (Exception ex){
            ex.getMessage();
        }

        if (results) {
            accountMngtErrorLabel.setText("Test Successfull.");
            Stage stage = (Stage) ipURL.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The one time database parameter setup is completed. please re-launch the application");
            alert.setTitle("Success!");
            alert.setHeaderText("Setting up the database connection parameters were successful.");
            alert.setContentText("Please press Ok and launch the application again.");
            alert.showAndWait();
            stage.close();
        }else {
            accountMngtErrorLabel.setText("Test Not Successfull.");
            try {
                configureDBObj.saveDbConfiguration("", "", "", "");
            }catch (Exception ex){
                ex.getMessage();
            }
        }
    }

    @FXML
    public void setDBConfiguration(){
        if(!ipURL.getText().isEmpty() && !databaseUsername.getText().isEmpty() && !databasePort.getText().isEmpty()){
            try {
                if(!configureDBObj.saveDbConfiguration(ipURL.getText(), databasePort.getText(), databaseUsername.getText(), databasePassword.getText())) {
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
}
