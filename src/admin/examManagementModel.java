package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class examManagementModel {
    private StringProperty applicantTestID, applicantUserID, applicantName, applicantExamDate, applicantExamResults, applicantNoOfTries, applicantIsDisabled, applicantEnable;

    public examManagementModel(String testID, String userID, String name, String examDate, String examResults, String noOfTries, String isDisabled, String enable){
        this.applicantTestID = new SimpleStringProperty(testID);
        this.applicantUserID =new SimpleStringProperty(userID);
        this.applicantName = new  SimpleStringProperty(name);
        this.applicantExamDate = new  SimpleStringProperty(examDate);
        this.applicantExamResults = new  SimpleStringProperty(examResults);
        this.applicantNoOfTries = new  SimpleStringProperty(noOfTries);
        this.applicantIsDisabled = new  SimpleStringProperty(isDisabled);
        this.applicantEnable = new  SimpleStringProperty(enable);
    }
    public String getApplicantTestID() {
        return applicantTestID.get();
    }

    public StringProperty applicantTestIDProperty() {
        return applicantTestID;
    }

    public void setApplicantTestID(String applicantTestID) {
        this.applicantTestID.set(applicantTestID);
    }

    public String getApplicantUserID() {
        return applicantUserID.get();
    }

    public StringProperty applicantUserIDProperty() {
        return applicantUserID;
    }

    public void setApplicantUserID(String applicantUserID) {
        this.applicantUserID.set(applicantUserID);
    }

    public String getApplicantName() {
        return applicantName.get();
    }

    public StringProperty applicantNameProperty() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName.set(applicantName);
    }

    public String getApplicantExamDate() {
        return applicantExamDate.get();
    }

    public StringProperty applicantExamDateProperty() {
        return applicantExamDate;
    }

    public void setApplicantExamDate(String applicantExamDate) {
        this.applicantExamDate.set(applicantExamDate);
    }

    public String getApplicantExamResults() {
        return applicantExamResults.get();
    }

    public StringProperty applicantExamResultsProperty() {
        return applicantExamResults;
    }

    public void setApplicantExamResults(String applicantExamResults) {
        this.applicantExamResults.set(applicantExamResults);
    }

    public String getApplicantNoOfTries() {
        return applicantNoOfTries.get();
    }

    public StringProperty applicantNoOfTriesProperty() {
        return applicantNoOfTries;
    }

    public void setApplicantNoOfTries(String applicantNoOfTries) {
        this.applicantNoOfTries.set(applicantNoOfTries);
    }

    public String getApplicantIsDisabled() {
        return applicantIsDisabled.get();
    }

    public StringProperty applicantIsDisabledProperty() {
        return applicantIsDisabled;
    }

    public void setApplicantIsDisabled(String applicantIsDisabled) {
        this.applicantIsDisabled.set(applicantIsDisabled);
    }

    public String getApplicantEnable() {
        return applicantEnable.get();
    }

    public StringProperty applicantEnableProperty() {
        return applicantEnable;
    }

    public void setApplicantEnable(String applicantEnable) {
        this.applicantEnable.set(applicantEnable);
    }
}
