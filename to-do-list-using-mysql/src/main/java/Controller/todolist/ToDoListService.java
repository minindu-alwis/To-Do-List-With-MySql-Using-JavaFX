package Controller.todolist;

import models.ToDoList;

import java.util.ArrayList;

public interface ToDoListService {
    boolean addTask(ToDoList task);
    ArrayList<ToDoList> loadTasks();
    void setUserIdAndName(String userId,String userName);
    String generateTaskId();
    boolean completedTask();
    String getuUserName();
    String getUserId();
    boolean deleteCompletedTask(String taskName);
}
