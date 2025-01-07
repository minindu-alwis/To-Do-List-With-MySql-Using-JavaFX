package Controller.signup;

import Connection.DBConnection;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupController implements SignupService {

    private static SignupController instance;

    private SignupController() {
    }

    public static SignupController getInstance() {
        return instance == null ? instance = new SignupController() : instance;
    }


    @Override
    public boolean registerUser(User user) throws SQLException {

        PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user VALUES(?,?,?)");
        prepareStm.setString(1, user.getUserid());
        prepareStm.setString(2, user.getUsername());
        prepareStm.setString(3, user.getPassword());
        return prepareStm.executeUpdate() > 0;

    }

    @Override
    public String generateuserId() {

            try {
                ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT user_id from user ORDER BY user_id DESC LIMIT 1");
                if (rst.next()) {
                    String existId = rst.getString(1);
                    return String.format("C%04d", Integer.parseInt(existId.substring(1)) + 1);
                } else {
                    return "C0001";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
