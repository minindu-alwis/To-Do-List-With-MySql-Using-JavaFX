import java.sql.Connection;
import java.sql.SQLException;

import Connection.DBConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection= DBConnection.getInstance().getConnection();
        System.out.println(connection);
        Starter.main(args);

    }
}