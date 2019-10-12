package admin;

import dbUtil.dbConnection;
import helperLib.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import dbUtil.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class firstTimeLogInController implements Initializable {
    @FXML
    private TextField newPassword, repeatNewPassword;
    @FXML
    private Button btnChangePassword;
    @FXML
    private Label firstTimeLogInErrorMsg;
    credentialManagement credentialManagementObj = new credentialManagement();

    Connection connection;
    String sqlQuery;
    PreparedStatement preparedStatement;
    @FXML
    public void changePassword(){
        if(!isDatabaseConnected()){
            this.firstTimeLogInErrorMsg.setText("Database not connected!");
        }
        if (
                this.newPassword.getText() == null ||
                this.newPassword.getText().trim().isEmpty() ||
                this.repeatNewPassword.getText() == null ||
                this.repeatNewPassword.getText().trim().isEmpty() ||
                !(this.newPassword.getText().equals(this.repeatNewPassword.getText()))
        ){
            firstTimeLogInErrorMsg.setText("Either a mandatory field is left blank of the passwords do not match.");
        }else {
            int updateStatus = 0;
            try {
                connection = dbConnection.getConnection("tes", "root", "");
                String encryptedNewPassword = "";
                String algorithm = "SHA-256";
                try{
                    encryptedNewPassword = hashGenerator.generateHash(newPassword.getText(), algorithm);
                }catch (NoSuchAlgorithmException ex){
                    ex.getMessage();
                    return;
                }

                if(credentialManagementObj.getPassword() == null | credentialManagementObj.getUsername() == null){
                    firstTimeLogInErrorMsg.setText("The current user's credentials are not stored in the temp file.");
                    return;
                }else {
                    sqlQuery = "UPDATE users SET password = ?, firsttimeloginforadmin = ?   WHERE username = ? AND usertype = ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, encryptedNewPassword);
                    preparedStatement.setString(2, "1");
                    preparedStatement.setString(3, credentialManagementObj.getUsername());
                    preparedStatement.setString(4, credentialManagementObj.getUsertype());
                    updateStatus = preparedStatement.executeUpdate();
                    //updateStatus = preparedStatement.getUpdateCount();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (updateStatus > 0){
                this.firstTimeLogInErrorMsg.setText("Records Updated.");
                Stage stage = (Stage) btnChangePassword.getScene().getWindow();
                try {
                    TimeUnit.SECONDS.sleep(3);
                }catch (InterruptedException ex){
                    ex.getMessage();
                }
                stage.close();

            }else{
                this.firstTimeLogInErrorMsg.setText("Your data did not match. Please try again and enter the data carefully.");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public boolean isDatabaseConnected(){ return this.connection != null; }
}
