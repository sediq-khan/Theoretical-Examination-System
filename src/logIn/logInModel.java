package logIn;

import dbUtil.*;
import helperLib.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logInModel {
    String databaseName = "tes";
    String dbUsername = "root";
    String dbPassword = "";

    Connection connection;

    public logInModel(){
        try {
            this.connection = dbConnection.getConnection(databaseName, dbUsername, dbPassword);
        }catch (SQLException ex){
            System.out.println(ex.getStackTrace());
        }

        if (this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    public boolean isLoggedIn(String usernameVal, String userPasswordVal, String userTypeVal) throws NoSuchAlgorithmException, SQLException {
        if(!isDatabaseConnected()){
            System.out.println("Database not connected!");
            return false;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String algorithm = "SHA-256";
        String encryptedPassword = null;
        String sqlQuery = null;


        switch (userTypeVal){
            case "Admin":
            case "SuperAdmin":
                encryptedPassword = hashGenerator.generateHash(userPasswordVal, algorithm);
                sqlQuery = "SELECT * FROM users WHERE username = ? AND password = ? AND usertype = ?";
                preparedStatement =  this.connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, usernameVal);
                preparedStatement.setString(2, encryptedPassword);
                System.out.println(encryptedPassword);
                preparedStatement.setString(3, userTypeVal);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    credentialManagement credentialManagementObj = new credentialManagement();
                    try {
                        credentialManagementObj.saveDbConfiguration(usernameVal, encryptedPassword, userTypeVal);
                    }catch (IOException ex){
                        ex.getMessage();
                    }
                    if (userTypeVal == "Admin" && resultSet.getInt(5) == 0){
                        try {
                            Stage mainUI = new Stage();
                            FXMLLoader superAdminMainUILoader = new FXMLLoader();
                            AnchorPane root = (AnchorPane) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\admin\\firstTimeLogIn.fxml").openStream());
                            Scene superAdminMainUIScene = new Scene(root);
                            mainUI.setScene(superAdminMainUIScene);
                            mainUI.setTitle("Super Admin Main UI");
                            mainUI.initStyle(StageStyle.UNDECORATED);
                            mainUI.setAlwaysOnTop(true);
                            mainUI.show();
                        }catch (IOException ex){
                            System.out.println("FXML file not found. -- " + ex.getMessage());
                            System.out.println(ex.getCause());
                            System.out.println(ex.getMessage());
                        }
                    }
                    return true;
                }
            break;
            case "Applicant":
                preparedStatement = null;
                resultSet = null;
                algorithm = "SHA-256";
                encryptedPassword = hashGenerator.generateHash(userPasswordVal, algorithm);
                sqlQuery = "SELECT * FROM applicants WHERE name = ? AND password = ?";
                preparedStatement =  this.connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, usernameVal);
                preparedStatement.setString(2, encryptedPassword);
                System.out.println(encryptedPassword);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    applicantCredentialManagement applicantCredentialManagement =  new applicantCredentialManagement();
                    try {
                        applicantCredentialManagement.saveDbConfiguration(usernameVal, encryptedPassword, resultSet.getString("tazkiraNo"), resultSet.getString("user_id") );
                    }catch (IOException ex){
                        ex.getMessage();
                    }
                    if (userTypeVal == "Admin" && resultSet.getInt(5) == 0){
                        try {
                            Stage mainUI = new Stage();
                            FXMLLoader superAdminMainUILoader = new FXMLLoader();
                            AnchorPane root = (AnchorPane) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\admin\\firstTimeLogIn.fxml").openStream());
                            Scene superAdminMainUIScene = new Scene(root);
                            mainUI.setScene(superAdminMainUIScene);
                            mainUI.setTitle("Super Admin Main UI");
                            mainUI.initStyle(StageStyle.UNDECORATED);
                            mainUI.setAlwaysOnTop(true);
                            mainUI.show();
                        }catch (IOException ex){
                            System.out.println("FXML file not found. -- " + ex.getMessage());
                            System.out.println(ex.getCause());
                            System.out.println(ex.getMessage());
                        }
                    }
                    return true;
                }
            break;
        }
return false;
    }

}
