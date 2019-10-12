package logIn;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        System.setProperty("javafx.scene.nodeOrientation.RTL", "true");
        System.setProperty("prism.txt", "t2k");
        System.setProperty("prism.lcdtext", "false");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader splashUILoader = new FXMLLoader();
        try {
            Parent root = (Parent) splashUILoader.load(getClass().getClassLoader().getResource("\\logIn\\splashUI.fxml").openStream());
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("خوش امدید");
            stage.show();
            fadeIn.setOnFinished((e) ->{
                FXMLLoader loadlogInUIFXML = new FXMLLoader();
                try {
                    Parent loadlogInUI = (AnchorPane) loadlogInUIFXML.load(getClass().getClassLoader().getResource("\\logIn\\logInUI.fxml").openStream());
                    Scene logInScene = new Scene(loadlogInUI);
                    stage.setScene(logInScene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }catch (NullPointerException ex){
            System.out.println("fucked up");
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
