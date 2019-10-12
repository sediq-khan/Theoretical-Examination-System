package home;

import dbUtil.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.stage.Stage;

public class examManagementController implements Initializable {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int numberOfQuestionInDB = 0;
    String sqlQuery = "";
    private TableView<examManagementModel> table = null;;
    private List<examManagementModel> data = null;
    private final static int rowsPerPage = 200;
    @FXML
    private Pagination pagination;
    @FXML
    private Button btnOverview, btnExamMngt, btnAccountMngt, btnNetworkMngt, btnQuestionMngt;

    private TableView<examManagementModel> createTable(){
        TableView<examManagementModel> table = new TableView<>();

        TableColumn<examManagementModel, String> testID = new TableColumn<>("Test ID");
        testID.setCellValueFactory(param -> param.getValue().applicantTestIDProperty());
        testID.setPrefWidth(100);

        TableColumn<examManagementModel, String> userID = new TableColumn<>("User ID");
        userID.setCellValueFactory(param -> param.getValue().applicantUserIDProperty());
        userID.setPrefWidth(100);

        TableColumn<examManagementModel, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(param -> param.getValue().applicantNameProperty());
        name.setPrefWidth(100);

        TableColumn<examManagementModel, String> examDate = new TableColumn<>("Exam Date");
        examDate.setCellValueFactory(param -> param.getValue().applicantExamDateProperty());
        examDate.setPrefWidth(100);

        TableColumn<examManagementModel, String> examResults = new TableColumn<>("Results");
        examResults.setCellValueFactory(param -> param.getValue().applicantExamResultsProperty());
        examResults.setPrefWidth(100);

        TableColumn<examManagementModel, String> noOfTries = new TableColumn<>("Tries");
        noOfTries.setCellValueFactory(param -> param.getValue().applicantNoOfTriesProperty());
        noOfTries.setPrefWidth(100);

        TableColumn<examManagementModel, String> isDisabled = new TableColumn<>("Is Disabled");
        isDisabled.setCellValueFactory(param -> param.getValue().applicantIsDisabledProperty());
        isDisabled.setPrefWidth(100);

        TableColumn<examManagementModel, String> enable = new TableColumn<>("Enable");
        enable.setCellValueFactory(param -> param.getValue().applicantEnableProperty());
        enable.setPrefWidth(100);

        table.getColumns().addAll(testID, userID, name, examDate, examResults, noOfTries, isDisabled, enable);
        return table;
    }

    private List<examManagementModel> createData(){
        List<examManagementModel> data = new ArrayList<>(numberOfQuestionInDB);
        sqlQuery = "SELECT exams.test_id, exams.user_id, applicants.name, exams.examdate, exams.result,exams.nooftries, exams.flag FROM exam AS exams, " +
                "applicants WHERE exams.user_id = applicants.user_id;";
        try {
            resultSet = connection.createStatement().executeQuery(sqlQuery);
            while (resultSet.next()){
                String disabled = "No";
                String isButton = "-";
                if (resultSet.getInt(6) >= 3){
                    disabled = "Yes";
                    isButton = "Yes";
                }
                data.add(new examManagementModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), disabled,isButton));
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
        try {
            connection = dbConnection.getConnection("tes", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(isDatabaseConnected()) {
            sqlQuery = "SELECT count(test_id) FROM exam;";
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
}
