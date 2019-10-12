package superAdmin;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class superAdminMainUIModel {
    private StringProperty userID, username, userType;

    public superAdminMainUIModel(String id, String username, String usertype){
        this.userID = new SimpleStringProperty(id);
        this.username =new SimpleStringProperty(username);
        this.userType =new  SimpleStringProperty(usertype);
    }

    public String getuserID() {
        return userID.get();
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public void setuserID(String id) {
        this.userID.set(id);
    }


    public String getusername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setusername(String id) {
        this.username.set(id);
    }


    public String getuserType() {
        return userType.get();
    }

    public StringProperty userTypeProperty() {
        return userType;
    }

    public void setuserType(String id) {
        this.userType.set(id);
    }








}
