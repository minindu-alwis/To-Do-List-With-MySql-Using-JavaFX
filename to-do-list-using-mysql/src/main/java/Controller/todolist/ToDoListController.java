package Controller.todolist;

import Connection.DBConnection;
import models.ToDoList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToDoListController implements ToDoListService{

    private static ToDoListController instance;
    private String userId;
    private String userName;

    private ToDoListController() {
    }

    public static ToDoListController getInstance() {
        return instance == null ? instance = new ToDoListController() : instance;
    }


    @Override
    public boolean addTask(ToDoList task) {

        try {
            PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO todolist VALUES(?,?,?,?) ");
            prepareStm.setString(1, generateTaskId());
            prepareStm.setString(2, task.getTaskName());
            prepareStm.setString(3, task.getDate());
            prepareStm.setString(4, userId);
            return prepareStm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ArrayList<ToDoList> loadTasks() {
        ArrayList<ToDoList> todoListArrayList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM todolist WHERE user_id='" + userId + "'");
            while (rst.next()) {
                todoListArrayList.add(new ToDoList(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
            }
            return todoListArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUserIdAndName(String userId, String userName) {
        this.userId=userId;
        this.userName=userName;
    }

    @Override
    public String generateTaskId() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT task_id FROM todolist ORDER BY task_id DESC LIMIT 1");
            if (rst.next()) {
                String existId = rst.getString(1);
                return String.format("T%04d", Integer.parseInt(existId.substring(1)) + 1);
            } else {
                return "T0001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean completedTask() {
        return false;
    }

    @Override
    public String getuUserName() {
        return this.userName;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public boolean deleteCompletedTask(String taskName) {
        return false;
    }
}
