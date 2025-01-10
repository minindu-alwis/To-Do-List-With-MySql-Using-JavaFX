import java.sql.Connection;
import java.sql.SQLException;

import Connection.DBConnection;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection= DBConnection.getInstance().getConnection();
        System.out.println(connection);
        Starter.main(args);

    }
}