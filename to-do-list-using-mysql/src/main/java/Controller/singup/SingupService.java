package Controller.singup;

import models.User;

import java.sql.SQLException;

public interface SingupService {

    boolean registerUser(User newUser) throws SQLException;

    String generateuserId();


}
