package helperLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class credentialManagement {
    public static Properties credentialManagement = new Properties();
    private String username = "username", password = "password", usertype = "usertype";
    boolean PropertiesSaved = false;

    public boolean saveDbConfiguration(String username, String password, String usertype) throws FileNotFoundException {
        if (username.isEmpty() && password.isEmpty() && usertype.isEmpty()){
            return false;
        }
    credentialManagement.setProperty(this.username, username);
    credentialManagement.setProperty(this.password, password);
    credentialManagement.setProperty(this.usertype, usertype);
    try {
        credentialManagement.store(new FileOutputStream("credentialManagement.txt"), null);
        PropertiesSaved = true;
        }catch (IOException e){
            e.getMessage();
        }

        if (!PropertiesSaved ){
            credentialManagement.clear();
        }
        return PropertiesSaved;
    }

    public String getUsername(){
        String UsernameLocal = "";
        try {
            credentialManagement.load(new FileInputStream("credentialManagement.txt"));
            UsernameLocal = credentialManagement.getProperty(this.username);
        }catch (IOException e){
            e.getMessage();
        }
        return UsernameLocal;
    }

    public String getPassword(){
        String PasswordLocal = "";
        try {
            credentialManagement.load(new FileInputStream("credentialManagement.txt"));
            PasswordLocal = credentialManagement.getProperty(this.password);
        }catch (IOException e){
            e.getMessage();
        }
        return PasswordLocal;
    }

    public String getUsertype(){
        String usertypeLocal = "";
        try {
            credentialManagement.load(new FileInputStream("credentialManagement.txt"));
            usertypeLocal = credentialManagement.getProperty(this.usertype);
        }catch (IOException e){
            e.getMessage();
        }catch (Exception ex){
            ex.getMessage();
            ex.getStackTrace();
        }
        return usertypeLocal;
    }
}
