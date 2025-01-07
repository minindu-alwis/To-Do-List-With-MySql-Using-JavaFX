package Controller.todolist;

import models.ToDoList;

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
        return false;
    }

    @Override
    public ArrayList<ToDoList> loadTasks() {
        return null;
    }

    @Override
    public void setUserIdAndName(String userId, String userName) {
        this.userId=userId;
        this.userName=userName;
    }

    @Override
    public String generateTaskId() {
        return "";
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
