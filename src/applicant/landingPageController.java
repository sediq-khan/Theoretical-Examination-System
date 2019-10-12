package applicant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class landingPageController implements Initializable {

    @FXML
    private Button beginTestExam, beginActualExam;
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
    @FXML
    public void startTestExam(){
        try {
            Stage stage = (Stage) this.beginTestExam.getScene().getWindow();
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("examUI.fxml"));
            Scene superAdminMainUIScene = new Scene(root);
            stage.setScene(superAdminMainUIScene);
            stage.setTitle("Super Admin Main UI");
            stage.show();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
            e.getCause();
        }
    }
    @FXML
    public void startActualExam(){

    }
}
