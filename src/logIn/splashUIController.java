package logIn;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class splashUIController{
    @FXML
    public void close_app(MouseEvent event) {
        System.exit(0);
    }
}