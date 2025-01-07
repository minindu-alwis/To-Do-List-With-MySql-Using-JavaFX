package Controller.signup;

import models.User;

import java.sql.SQLException;

public interface SignupService {

    boolean registerUser(User newUser) throws SQLException;

    String generateuserId();


}
