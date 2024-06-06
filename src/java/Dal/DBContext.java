package Dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
            String url = "jdbc:sqlserver://DOTRONGTAI;databaseName=SWP391";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }

    }
     public static void main(String[] args) {
        DBContext a = new DBContext();
        if (a.connection != null) {
            System.out.println("Kết nối thành công!");

        }
    }

}
