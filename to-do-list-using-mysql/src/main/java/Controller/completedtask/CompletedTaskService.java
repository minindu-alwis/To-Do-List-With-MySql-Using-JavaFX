package Controller.completedtask;

import models.CompletedTask;

import java.util.ArrayList;

public interface CompletedTaskService {

    boolean completedTask(String taskName, String date);
    ArrayList<CompletedTask> loadCompletedTask();

}
