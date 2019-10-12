package dbUtil;

import java.sql.*;

public class dbOperations {
    private Connection connection;
    private PreparedStatement prepareStatement = null;
    private String sqlOperationType;
    private ResultSet resultSet = null;
    private int rowCount = 0;

    public int dbOperations(Connection connection, PreparedStatement preparedStatement, String sqlOperationType) throws  SQLException{
        this.connection = connection;
        this.prepareStatement = preparedStatement;
        this.sqlOperationType = sqlOperationType;
        switch (this.sqlOperationType) {
            case "select":
                preparedStatement.execute();
                this.resultSet = preparedStatement.getResultSet();
                while (this.resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
                this.connection.close();
                break;
            case "update":
                System.out.println("blah");
                break;
            case "delete":
                try {
                    rowCount = preparedStatement.executeUpdate();
                    this.resultSet = preparedStatement.getResultSet();
                }catch (SQLIntegrityConstraintViolationException ex){
                    ex.getMessage();
                    return 111;
                }
                this.connection.close();
                break;
            case "insert":
                try {
                    rowCount = this.prepareStatement.executeUpdate();
                }catch (Exception ex){
                    System.out.println(ex.getMessage() + " in the called function.");
                    if (ex.getMessage().contains("Duplicate")){
                        return 111;
                    }
                }
                break;
            default:
                System.out.println("Bad operation!");
                break;
        }
        return this.rowCount;
    }

}

