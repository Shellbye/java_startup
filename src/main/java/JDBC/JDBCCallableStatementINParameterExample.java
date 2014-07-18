package JDBC;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-17
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCCallableStatementINParameterExample {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] argv) {

        try {

            callOracleStoredProcINParameter();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void callOracleStoredProcINParameter() throws SQLException {

        Connection dbConnection = null;
        CallableStatement callableStatement = null;

        String insertStoreProc = "{call insertDBUSER(?,?,?,?)}";

        try {
            dbConnection = getDBConnection();
            callableStatement = dbConnection.prepareCall(insertStoreProc);

            callableStatement.setInt(1, 1000);
            callableStatement.setString(2, "mkyong");
            callableStatement.setString(3, "system");
            callableStatement.setDate(4, getCurrentDate());

            // execute insertDBUSER store procedure
            callableStatement.executeUpdate();

            System.out.println("Record is inserted into DBUSER table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (callableStatement != null) {
                callableStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER,DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

}