package dbUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class configureDB {
    public static Properties dbConfiguration = new Properties();
    private String ip = "ip", dbPort = "dbPort", username = "username", password = "password";
    boolean PropertiesSaved = false;
    boolean dbConnectionSuccess = false;
    Connection connection;

    public boolean saveDbConfiguration(String dbIP, String dbPort,String dbUsername, String dbPassword) throws FileNotFoundException {
        if (dbIP.isEmpty() && dbPort.isEmpty() && dbUsername.isEmpty()){
            return false;
        }
        try{
            connection = dbConnection.getConnection("tes",
                    dbPort,
                    dbIP,
                    dbUsername,
                    dbPassword);
            dbConnectionSuccess = true;
        }catch (SQLException ex){
            ex.getMessage();
        }
        if (dbConnectionSuccess){
            dbConfiguration.setProperty(this.ip, dbIP);
            dbConfiguration.setProperty(this.dbPort, dbPort);
            dbConfiguration.setProperty(this.username, dbUsername);
            dbConfiguration.setProperty(this.password, dbPassword);
            try {
                dbConfiguration.store(new FileOutputStream("dbConfig.txt"), null);
                PropertiesSaved = true;
            }catch (IOException e){
                e.getMessage();
            }
        }
        if (!PropertiesSaved | !dbConnectionSuccess){
            dbConfiguration.clear();
        }
        return PropertiesSaved;
    }

    public String getDbConfigurationIP(){
        String ipValueLocal = "";
        try {
            dbConfiguration.load(new FileInputStream("dbConfig.txt"));
            ipValueLocal = dbConfiguration.getProperty(this.ip);
        }catch (IOException e){
            return ipValueLocal;
        }
        return ipValueLocal;
    }

    public String getDbConfigurationUsername(){
        String UsernameLocal = "";
        try {
            dbConfiguration.load(new FileInputStream("dbConfig.txt"));
            UsernameLocal = dbConfiguration.getProperty(this.username);
        }catch (IOException e){
            return UsernameLocal;
        }
        return UsernameLocal;
    }

    public String getDbConfigurationPassword(){
        String PasswordLocal = "";
        try {
            dbConfiguration.load(new FileInputStream("dbConfig.txt"));
            PasswordLocal = dbConfiguration.getProperty(this.password);
        }catch (IOException e){
            return PasswordLocal;
        }
        return PasswordLocal;
    }

    public String getDbConfigurationDbPort(){
        String dbPortLocal = "";
        try {
            dbConfiguration.load(new FileInputStream("dbConfig.txt"));
            dbPortLocal = dbConfiguration.getProperty(this.dbPort);
        }catch (IOException e){
            return dbPortLocal;
        }
        return dbPortLocal;
    }
}
