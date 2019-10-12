package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class displayApplicants {
    private StringProperty applicantID, applicantName, applicantTazkiraNo, applicantPassword;

    public displayApplicants(String applicantId, String applicantUsername, String applicantTazkiraNo, String applicantPassword){
        this.applicantID = new SimpleStringProperty(applicantId);
        this.applicantName =new SimpleStringProperty(applicantUsername);
        this.applicantTazkiraNo =new  SimpleStringProperty(applicantTazkiraNo);
        this.applicantPassword = new SimpleStringProperty(applicantPassword);
    }

    public String getApplicantID() {
        return applicantID.get();
    }

    public StringProperty applicantIDProperty() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID.set(applicantID);
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

    public String getApplicantTazkiraNo() {
        return applicantTazkiraNo.get();
    }

    public StringProperty applicantTazkiraNoProperty() {
        return applicantTazkiraNo;
    }

    public void setApplicantTazkiraNo(String applicantTazkiraNo) {
        this.applicantTazkiraNo.set(applicantTazkiraNo);
    }

    public String getApplicantPassword() {
        return applicantPassword.get();
    }

    public StringProperty applicantPasswordProperty() {
        return applicantPassword;
    }

    public void setApplicantPassword(String applicantPassword) {
        this.applicantPassword.set(applicantPassword);
    }
}
