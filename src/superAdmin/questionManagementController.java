package superAdmin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class questionManagementController implements Initializable {
    @FXML
    private AnchorPane questionListAnchorPane = null;
    @FXML
    private Button selectImage;

    @FXML
    private Button selectImageButton;

    @FXML
    public void selectImage() throws FileNotFoundException {
        uploadHandler();
    }
    @FXML
    public void selectVideo(){

    }


    public void uploadHandler() throws FileNotFoundException {
        FileInputStream fis;

        Stage stage = (Stage) selectImageButton.getScene().getWindow();
        stage.setTitle("Save File");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose The File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "jpg"));
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file.getPath());

//        if (file != null){
//
//            fis = new FileInputStream(file);
//            preparedStatement.setBinaryStream(11, (InputStream)fis, (int)file.length());
//        }

//        File selectedFile = fileChooser.showOpenDialog(stage);
//        String path = selectedFile.getAbsolutePath();
//        File selectedImage = new File(path);
//
//        BufferedImage bimage = new BufferedImage(selectedFile.);
//
//        selectedImage.di
//                selectedImage = fileChooser.showSaveDialog(stage);
//        System.out.println(selectedImage.getName());
//        if (selectedImage != null){
//            System.out.println("File selected");
//        }else{
//            System.out.println("no file selected");
//        }
//
//        if (selectedFile != null){
//            if (!selectedFile.getPath().endsWith(".png") | !selectedFile.getPath().endsWith(".jpg") | !selectedFile.getPath().endsWith(".gif")){
//                selectedFile = new File(selectedFile.getPath() + ".xml");
//            }
//        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle){
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++){
            System.out.println("for started");
            try {
                System.out.println("try started");
                nodes[i] = (Node) FXMLLoader.load(getClass().getClassLoader().getResource("\\superAdmin\\item.fxml"));

                System.out.println("fxml loader started");
                questionListAnchorPane.getChildren().add(nodes[i]);
                System.out.println("done");
            }catch (IOException ex){
                System.out.println("FXML Item file Not found." + ex.getMessage());
            }
        }
    }


}
