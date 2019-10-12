package logIn;

import dbUtil.configureDB;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import helperLib.userTypes;
import javafx.stage.Stage;

public class logInUIController implements Initializable {
    logInModel logInModel = new logInModel();

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<userTypes> selectionCombo;
    @FXML
    private Button signIn;
    @FXML
    private Label loginError;
    @FXML
    private VBox vboxLogin;
    @FXML
    private VBox signInControlsVBox;
    @FXML
    private VBox signInVBox;
    @FXML
    private Button btnConfigureDB;

    Connection connection = null;
    private configureDB configureDBObj = new configureDB();


    @FXML
    public void signIn (ActionEvent event) {
        try {
            if(this.logInModel.isLoggedIn(this.username.getText().toString().trim(), this.password.getText().toString().trim(), ((userTypes) this.selectionCombo.getValue()).toString().trim())){
                Stage stage = (Stage) this.signIn.getScene().getWindow();
                stage.close();
                switch (((userTypes)this.selectionCombo.getValue())){
                    case Admin:
                        adminLogin();
                        break;
                    case Applicant:
                        applicantLogIn();
                        break;
                    case SuperAdmin:
                        superAdminLogin();
                        break;
                }
            }else {
                System.out.println("User or Password Wrong.");
            }
        }catch (SQLException ex){
            System.out.println("Error 1 is: " + ex.getMessage());
        }catch (NoSuchAlgorithmException ex1){
            System.out.println("Error 2 is: " + ex1.getMessage());
        }
    }

    @FXML
    public void close_app(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //To generate encrypted password
//        try {
//            String password = "laghmanjankhanbanbanjankamkai";
//            String encryptedPassword;
//            String algorithm = "SHA-256";
//            encryptedPassword = hashGenerator.generateHash(password, algorithm);
//            System.out.println(encryptedPassword);
//        }catch (NoSuchAlgorithmException e){
//            e.getStackTrace();
//        }
//        System.exit(11);
        boolean results = false;
        if(
//                !configureDBObj.getDbConfigurationDbPort().isEmpty() &&
//                !configureDBObj.getDbConfigurationIP().isEmpty() &&
//                !configureDBObj.getDbConfigurationUsername().isEmpty()
                    !configureDBObj.getDbConfigurationDbPort().equals("") ||
                    !configureDBObj.getDbConfigurationIP().equals("") ||
                    !configureDBObj.getDbConfigurationUsername().equals("")
        ){
            try {
                connection = dbConnection.getConnection("tes", configureDBObj.getDbConfigurationDbPort(), configureDBObj.getDbConfigurationIP(), configureDBObj.getDbConfigurationUsername(), configureDBObj.getDbConfigurationPassword());
                results = true;
            } catch (SQLException e) {
                e.printStackTrace() ;
            }
        }else {
            loginError.setText("One or more of the required fields are empty.");
            if (!results) {
                signInControlsVBox.setVisible(false);
                signInVBox.setVisible(false);
                loginError.setText("Database Not Configured.");
                vboxLogin.setVisible(true);
            }
        }
        this.selectionCombo.setItems(FXCollections.observableArrayList(userTypes.values()));
    }

    public void superAdminLogin() {
        try {
            //Stage superAdminMainUIStage = new Stage();
            Stage mainUI = new Stage();

            FXMLLoader superAdminMainUILoader = new FXMLLoader();
            //Parent superAdminRoot = (Parent) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\superAdmin\\superAdminMainUI.fxml").openStream());
            ////superAdminMainUIController superAdminMainUIController = (superAdminMainUIController) superAdminMainUILoader.getController();
            //Scene superAdminMainUIScene = new Scene(superAdminRoot);
            AnchorPane root = (AnchorPane) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\home\\Home.fxml").openStream());
            ////Controller controller = (Controller) superAdminMainUILoader.getController();

            Scene superAdminMainUIScene = new Scene(root);

//            superAdminMainUIStage.setScene(superAdminMainUIScene);
//            superAdminMainUIStage.setTitle("Super Admin Main UI");
//            superAdminMainUIStage.initStyle(StageStyle.UNDECORATED);
//            superAdminMainUIStage.show();
            mainUI.setScene(superAdminMainUIScene);
            mainUI.setTitle("Super Admin Main UI");
            mainUI.initStyle(StageStyle.UNDECORATED);
            mainUI.show();
        }catch (IOException ex){
            System.out.println("FXML file not found. -- " + ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    public void configureDBParameters() throws IOException{
            Stage currentStage = (Stage) btnConfigureDB.getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("configureDB.fxml"));
            Scene superAdminMainUIScene = new Scene(root);
            stage.setScene(superAdminMainUIScene);
            stage.setTitle("Super Admin Main UI");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
    }
    public void adminLogin(){
        try {
            //Stage superAdminMainUIStage = new Stage();
            Stage mainUI = new Stage();
            FXMLLoader superAdminMainUILoader = new FXMLLoader();
            //Parent superAdminRoot = (Parent) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\superAdmin\\superAdminMainUI.fxml").openStream());
            ////superAdminMainUIController superAdminMainUIController = (superAdminMainUIController) superAdminMainUILoader.getController();
            //Scene superAdminMainUIScene = new Scene(superAdminRoot);
            AnchorPane root = (AnchorPane) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\admin\\Home.fxml").openStream());
            ////Controller controller = (Controller) superAdminMainUILoader.getController();

            Scene superAdminMainUIScene = new Scene(root);

//            superAdminMainUIStage.setScene(superAdminMainUIScene);
//            superAdminMainUIStage.setTitle("Super Admin Main UI");
//            superAdminMainUIStage.initStyle(StageStyle.UNDECORATED);
//            superAdminMainUIStage.show();
            mainUI.setScene(superAdminMainUIScene);
            mainUI.setTitle("Super Admin Main UI");
            mainUI.initStyle(StageStyle.UNDECORATED);
            mainUI.show();
        }catch (IOException ex){
            System.out.println("FXML file not found. --" + ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }


    }

    public void applicantLogIn(){
        try {
            //Stage superAdminMainUIStage = new Stage();
            Stage mainUI = new Stage();
            FXMLLoader superAdminMainUILoader = new FXMLLoader();
            //Parent superAdminRoot = (Parent) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\superAdmin\\superAdminMainUI.fxml").openStream());
            ////superAdminMainUIController superAdminMainUIController = (superAdminMainUIController) superAdminMainUILoader.getController();
            //Scene superAdminMainUIScene = new Scene(superAdminRoot);
            AnchorPane root = (AnchorPane) superAdminMainUILoader.load(getClass().getClassLoader().getResource("\\applicant\\landingPage.fxml").openStream());
            ////Controller controller = (Controller) superAdminMainUILoader.getController();

            Scene superAdminMainUIScene = new Scene(root);

//            superAdminMainUIStage.setScene(superAdminMainUIScene);
//            superAdminMainUIStage.setTitle("Super Admin Main UI");
//            superAdminMainUIStage.initStyle(StageStyle.UNDECORATED);
//            superAdminMainUIStage.show();
            mainUI.setScene(superAdminMainUIScene);
            mainUI.setTitle("Super Admin Main UI");
            mainUI.initStyle(StageStyle.UNDECORATED);
            mainUI.show();
        }catch (IOException ex){
            System.out.println("FXML file not found. --" + ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
        }
    }
//    public void loadDBConfigWindow(){
//        Stage stage = new Stage();
//        try{
//            Parent root = (Parent) FXMLLoader.load(getClass().getResource("\\logIn\\configureDB.fxml"));
//            Scene superAdminMainUIScene = new Scene(root);
//            stage.setScene(superAdminMainUIScene);
//            stage.setTitle("Super Admin Main UI");
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.show();
//        }catch (IOException ex){
//            ex.getCause();
//        }
//    }
}