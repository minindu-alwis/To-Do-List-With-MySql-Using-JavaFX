package Controller.login;

import Connection.DBConnection;
import Controller.todolist.ToDoListController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements LoginService{

    private static LoginController instance;

    private LoginController() {
    }

    public static LoginController getInstance() {
        return instance == null ? instance = new LoginController() : instance;
    }


    @Override
    public boolean authenticateUser(String userName, String password) {

        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT user_id, username,password FROM user WHERE username='" + userName + "' AND password='" + password + "'");
            if(rst.next()){
                ToDoListController.getInstance().setUserIdAndName(rst.getString("user_id"), rst.getString("username"));
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
