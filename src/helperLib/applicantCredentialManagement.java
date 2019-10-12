package helperLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class applicantCredentialManagement {
    public static Properties credentialManagement = new Properties();
    private String username = "username", password = "password", tazkira = "tazkira", id = "id";
    boolean PropertiesSaved = false;

    public boolean saveDbConfiguration(String username, String password, String tazkira, String id) throws FileNotFoundException {
        if (username.isEmpty() || password.isEmpty() || tazkira.isEmpty() || id.isEmpty() ){
            return false;
        }
    credentialManagement.setProperty(this.username, username);
    credentialManagement.setProperty(this.password, password);
    credentialManagement.setProperty(this.tazkira, tazkira);
    credentialManagement.setProperty(this.id, id);
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

    public String getTazkira(){
        String TazkiraLocal = "";
        try {
            credentialManagement.load(new FileInputStream("credentialManagement.txt"));
            TazkiraLocal = credentialManagement.getProperty(this.tazkira);
        }catch (IOException e){
            e.getMessage();
        }
        return TazkiraLocal;
    }

    public String getId(){
        String IdLocal = "";
        try {
            credentialManagement.load(new FileInputStream("credentialManagement.txt"));
            IdLocal = credentialManagement.getProperty(this.id);
        }catch (IOException e){
            e.getMessage();
        }
        return IdLocal;
    }
}
