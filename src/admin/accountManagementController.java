package admin;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class accountManagementController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField repeatNewPassword;
    @FXML
    private Label errorMsg;
    @FXML
    private Button btnOverview, btnExamMngt, btnAccountMngt, btnNetworkMngt, btnQuestionMngt;

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    public void update() throws SQLException, NoSuchAlgorithmException {
        if(!isDatabaseConnected()){
            this.errorMsg.setText("Database not connected!");
        }
        if (
                this.username.getText() == null ||
                        this.username.getText().trim().isEmpty() ||
                        this.password.getText() == null ||
                        this.password.getText().trim().isEmpty() ||
                        this.newPassword.getText() == null ||
                        this.newPassword.getText().trim().isEmpty() ||
                        this.repeatNewPassword.getText() == null ||
                        this.repeatNewPassword.getText().trim().isEmpty() ||
                        !(this.newPassword.getText().equals(this.repeatNewPassword.getText()))
        ){
            errorMsg.setText("Either a mandatory field is left blank of the passwords do not match.");
        }else {
            int updateStatus = 0;
            try {
                connection = dbConnection.getConnection("tes", "root", "");
                String encryptedPassword;
                String algorithm = "SHA-256";
                encryptedPassword = hashGenerator.generateHash(password.getText(), algorithm);
                String encryptedNewPassword = hashGenerator.generateHash(newPassword.getText(), algorithm);
                String sqlQuery = "UPDATE users SET password = ? WHERE username = ? AND password = ?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, encryptedNewPassword);
                preparedStatement.setString(2, username.getText());
                preparedStatement.setString(3, encryptedPassword);
                System.out.println(sqlQuery);
                System.out.println(encryptedNewPassword);
                System.out.println(encryptedPassword);
                System.out.println(username.getText());
                updateStatus = preparedStatement.executeUpdate();
                System.out.println(updateStatus);
                updateStatus = preparedStatement.getUpdateCount();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (updateStatus > 0){
                this.errorMsg.setText("Records Updated.");

            }else{
                this.errorMsg.setText("Your data did not match. Please try again and enter the data carefully.");
            }
        }
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
    public void invokeNetworkMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("networkManagement.fxml"));
        Scene superAdminMainUIScene = new Scene(root);
        stage.setScene(superAdminMainUIScene);
        stage.setTitle("Super Admin Main UI");
        stage.show();
    }
}