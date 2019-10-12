package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import dbUtil.dbOperations;
import helperLib.*;

import javax.imageio.ImageIO;

public class questionManagementController implements Initializable{
    @FXML
    private TextField Question, Answer1, Answer2, Answer3, Answer4;
    @FXML
    private ComboBox<correctAnswer> correntAnswerComboBox;
    @FXML
    private ComboBox<categories> categoriesComboBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pagination pagination;
    @FXML
    private Label lblQuestion, lblAnswerOne, lblAnswerTwo, lblAnswerThree, lblAnswerFour, lblCorrectAnswer, lblCategory, addQuestionErrorLabel;
    @FXML
    private Button btnOverview, btnExamMngt, btnAccountMngt, btnNetworkMngt, btnQuestionMngt, btnAddQuestion;
    @FXML
    private ImageView imageView;
    Image image;

    BufferedImage bufferedImage = null;
    File file = null;
    FileChooser fileChooser = null;
    String path = "";

    FileChooser fileChooser2 = null;
    File file2 = null;
    String path2 = null;
    MediaView mv = null;
    MediaPlayer mp = null;
    Media me = null;


    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int numberOfQuestionInDB = 0;
    String sqlQuery = "";


    private TableView<questionManagementModel> table = null;
    private List<questionManagementModel> data = null;
    private final static int rowsPerPage = 200;

    ObservableList<displayAdmins> oList;
    dbOperations dbOperations = new dbOperations();
    Integer questionsCount = 0;

    private TableView<questionManagementModel> createTable(){
        TableView<questionManagementModel> table = new TableView<>();

        TableColumn<questionManagementModel, String> question_id = new TableColumn<>("Question ID");
        question_id.setCellValueFactory(param -> param.getValue().questionIDProperty());
        question_id.setPrefWidth(100);

        TableColumn<questionManagementModel, String> question = new TableColumn<>("Question");
        question.setCellValueFactory(param -> param.getValue().questionProperty());
        question.setPrefWidth(100);

        TableColumn<questionManagementModel, String> category = new TableColumn<>("Category");
        category.setCellValueFactory(param -> param.getValue().categoryProperty());
        category.setPrefWidth(100);

        TableColumn<questionManagementModel, String> answer_one = new TableColumn<>("Answer One");
        answer_one.setCellValueFactory(param -> param.getValue().answerOneProperty());
        answer_one.setPrefWidth(100);

        TableColumn<questionManagementModel, String> answer_two = new TableColumn<>("Answer Two");
        answer_two.setCellValueFactory(param -> param.getValue().answerTwoProperty());
        answer_two.setPrefWidth(100);

        TableColumn<questionManagementModel, String> answer_three = new TableColumn<>("Answer Three");
        answer_three.setCellValueFactory(param -> param.getValue().answerThreeProperty());
        answer_three.setPrefWidth(100);

        TableColumn<questionManagementModel, String> answer_four = new TableColumn<>("Answer Four");
        answer_four.setCellValueFactory(param -> param.getValue().answerFourProperty());
        answer_four.setPrefWidth(100);

        TableColumn<questionManagementModel, String> righ_answer = new TableColumn<>("Righ Answer");
        righ_answer.setCellValueFactory(param -> param.getValue().rightAnswerProperty());
        righ_answer.setPrefWidth(100);

        TableColumn<questionManagementModel, String> hasImage = new TableColumn<>("Image");
        hasImage.setCellValueFactory(param -> param.getValue().hasImageProperty());
        hasImage.setPrefWidth(100);

        TableColumn<questionManagementModel, String> hasMedia = new TableColumn<>("Media");
        hasMedia.setCellValueFactory(param -> param.getValue().hasMediaProperty());
        hasMedia.setPrefWidth(100);

        table.getColumns().addAll(question_id, question, category, answer_one, answer_two, answer_three, answer_four, righ_answer, hasImage, hasMedia);
        return table;
    }

    private List<questionManagementModel> createData(){
        List<questionManagementModel> data = new ArrayList<>(numberOfQuestionInDB);
        sqlQuery = "SELECT * FROM questions;";
        try {
            resultSet = connection.createStatement().executeQuery(sqlQuery);
            int counter = 1;
            Blob hasImg = null;
            Blob hasMed = null;

            while (resultSet.next()){
                hasImg = resultSet.getBlob("image");
                hasMed = resultSet.getBlob("media");

                if (hasImg == null && hasMed == null) {
                    data.add(new questionManagementModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                            "No", "No"));
                }else if (hasImg == null && hasMed != null){
                    data.add(new questionManagementModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                            "No", "Yes"));
                }else if (hasImg != null && hasMed == null){
                    data.add(new questionManagementModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                            "Yes", "No"));
                }else{
                    data.add(new questionManagementModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                            "Yes", "Yes"));
                }
                hasImg = hasMed = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex,toIndex)));
        return table;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.categoriesComboBox.setItems(FXCollections.observableArrayList(categories.values()));
        this.correntAnswerComboBox.setItems(FXCollections.observableArrayList(correctAnswer.values()));
        try {
            this.connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!isDatabaseConnected()) {
            this.addQuestionErrorLabel.setText("The Database Connection Failed.");
        }

//        if(isDatabaseConnected()) {
//            sqlQuery = "SELECT COUNT(id) FROM questions";
//            try {
//                resultSet = connection.createStatement().executeQuery(sqlQuery);
//                while (resultSet.next()) {
//                    questionsCount = resultSet.getInt(1);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }else {
//            System.out.println("not questions");
//        }

        if(isDatabaseConnected()) {
            sqlQuery = "SELECT count(id) FROM questions;";
            try {
                resultSet = connection.createStatement().executeQuery(sqlQuery);
                resultSet.next();
                numberOfQuestionInDB = resultSet.getInt(1);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        table = createTable();
        data = createData();
        pagination.setPageFactory(this::createPage);
    }

    public boolean isDatabaseConnected(){ return this.connection != null; }

    public int itemsPerPage(){
        return 5;
    }

    @FXML
    public void addQuestion() throws SQLException {
        int rowCount = 0;
        if (
                this.Question.getText() == null ||
                this.Answer1.getText().trim().isEmpty() ||
                this.Answer2.getText() == null ||
                this.Answer3.getText() == null ||
                this.Answer4.getText() == null ||
                this.categoriesComboBox.getValue().toString() == null ||
                this.correntAnswerComboBox.getValue().toString() == null
        ){
            this.addQuestionErrorLabel.setText("One or more mandatory field(s) are left blank.");
            return;
        }else {
            if(!isDatabaseConnected()){
                this.addQuestionErrorLabel.setText("Database not connected!");
                return;
            }
            sqlQuery = "INSERT INTO questions (question, answerone, answertwo, answerthree, answerfour, rightanswer, cat, urlvoice, urlimg, urlvid, image, media) " +
                    "           VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement =  this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, this.Question.getText());
            preparedStatement.setString(2, this.Answer1.getText());
            preparedStatement.setString(3, this.Answer2.getText());
            preparedStatement.setString(4, this.Answer3.getText());
            preparedStatement.setString(5, this.Answer4.getText());
            preparedStatement.setString(6, this.correntAnswerComboBox.getValue().toString());
            preparedStatement.setString(7, this.categoriesComboBox.getValue().toString());
            preparedStatement.setString(8, "--");
            preparedStatement.setString(9, "--" );
            preparedStatement.setString(10, "--" );
            if (image != null) {
                try {
                    System.out.println("image != null 1");
                    InputStream inputStream = new FileInputStream(new File(path));

                    System.out.println("image != null 5");
                    preparedStatement.setBlob(11, inputStream);
                    System.out.println("image != null 2");
                }catch (FileNotFoundException ex){
                    System.out.println(ex.getMessage() + "here");

                    System.out.println("image != null 3");
                }
            }else{
                preparedStatement.setString(11, null);
                System.out.println("image != null 4");
            }
            if (file2 != null) {
                try {
                    System.out.println("file2 != null 1");
                    InputStream inputStream = new FileInputStream(new File(path2));

                    System.out.println("file2 != null 5");
                    preparedStatement.setBlob(12, inputStream);
                    System.out.println("file2 != null 2");
                }catch (FileNotFoundException ex){
                    System.out.println(ex.getMessage() + "here");

                    System.out.println("file2 != null 3");
                }
            }else{
                preparedStatement.setString(12, null);
                System.out.println("file2 != null 4");
            }
            rowCount = dbOperations.dbOperations(this.connection, this.preparedStatement, "insert");
        }
        if (rowCount > 0){
            this.addQuestionErrorLabel.setText("Data Added.");
            return;
        }else{
            this.addQuestionErrorLabel.setText("Data Not Added.");
            return;
        }
    }
    @FXML
    public void invokeExamMngtUI() throws IOException {
        Stage stage = (Stage) this.btnQuestionMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("examManagement.fxml"));
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
    public void invokeAccountMngtUI() throws IOException {
        Stage stage = (Stage) this.btnExamMngt.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("accountManagement.fxml"));
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
    @FXML
    public void selectImage(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg" , "*.jpeg");
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(extensionFilterPNG);
        file = fileChooser.showOpenDialog(null);
        if(file != null){
            path = file.getAbsolutePath();
            try{
                bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }catch (IOException ex){
                ex.getMessage();
            }
        }else{
            addQuestionErrorLabel.setText("Selection Picture was not successful.");

        }
    }
    @FXML
    public void selectMedia(){
        fileChooser2 = new FileChooser();
        FileChooser.ExtensionFilter extensionFilterPNG2 = new FileChooser.ExtensionFilter("Media", "*.mp4", "*.wmv" , "*.flv");
        fileChooser2.setTitle("Save Image");
        fileChooser2.getExtensionFilters().addAll(extensionFilterPNG2);
        try {
            file2 = fileChooser2.showOpenDialog(null);
        }catch (Exception ex){
            System.out.println("No image selected.");
        }
        if (file2 != null) {
            path2 = file2.getAbsolutePath();
        }
//        try {
//            path2 = file2.toURI().toURL().toExternalForm();
//            me = new Media(file2.toURI().toURL().toExternalForm());
//            mp = new MediaPlayer(me);
//            mv = new MediaView();
////            mv.setMediaPlayer(mp);
//            mp.play();
//            Stage newStage = (Stage) this.imageView.getScene().getWindow();
//            newStage.setScene(new Scene(new Group(mv)));
//        }catch (MalformedURLException ex){
//            ex.getMessage();
//        }
    }
}

