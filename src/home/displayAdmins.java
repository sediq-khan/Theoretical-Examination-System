package home;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class displayAdmins {
    private StringProperty userID, username, userType, firstTimeLogIn;

    public displayAdmins(String id, String username, String usertype, String firstTimeLogIn){
        this.userID = new SimpleStringProperty(id);
        this.username =new SimpleStringProperty(username);
        this.userType =new  SimpleStringProperty(usertype);
        this.firstTimeLogIn = new SimpleStringProperty(firstTimeLogIn);
    }

    public String getUserID() {
        return userID.get();
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getUserType() {
        return userType.get();
    }

    public StringProperty userTypeProperty() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType.set(userType);
    }

    public String getFirstTimeLogIn() {
        return firstTimeLogIn.get();
    }

    public StringProperty firstTimeLogInProperty() {
        return firstTimeLogIn;
    }

    public void setFirstTimeLogIn(String firstTimeLogIn) {
        this.firstTimeLogIn.set(firstTimeLogIn);
    }
}
