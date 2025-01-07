package Controller.todolist;
import Controller.completedtask.CompletedTaskController;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ToDoList;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ToDoListFormController implements Initializable {


    public Label usenametxt;
    public TextField newTaskNametxt;
    public DatePicker newTaskDatetxt;
    public JFXListView todolistview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        loadtask();
    }

    private void loadtask() {
        try {
            ArrayList<ToDoList> todoListArrayList = ToDoListController.getInstance().loadTasks();

            if (todoListArrayList == null || todoListArrayList.isEmpty()) {
                System.out.println("No tasks to display.");
                return;
            }

            for (ToDoList todoList : todoListArrayList) {
                HBox hBox = new HBox(30);
                hBox.getStyleClass().add("task-card");

                Label newTask = new Label("Task: " + todoList.getTaskName());
                newTask.getStyleClass().add("task-name");
                hBox.getChildren().add(newTask);

                String taskDate = todoList.getDate();
                if (taskDate != null && !taskDate.isEmpty()) {
                    Label date = new Label("Date: " + taskDate);
                    date.getStyleClass().add("task-date");
                    hBox.getChildren().add(date);
                }

                CheckBox checkBox = new CheckBox("Completed");
                checkBox.getStyleClass().add("task-checkbox");
                hBox.getChildren().add(checkBox);

                todolistview.getItems().add(hBox);

                checkBox.setOnAction(actionEvent -> {
                    if (checkBox.isSelected()) {
                        String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                        if (CompletedTaskController.getInstance().completedTask(todoList.getTaskName(), currentDate) &&
                                ToDoListController.getInstance().deleteCompletedTask(todoList.getTaskName())) {
                            new Alert(Alert.AlertType.INFORMATION, "Task Finished").show();
                            todolistview.getItems().remove(hBox);
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Task operation failed").show();
                        }
                    }
                });
            }

            System.out.println("Tasks loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading tasks: " + e.getMessage()).show();
        }
    }


    private void setName(){
        String name=ToDoListController.getInstance().getuUserName();
        usenametxt.setText(name);
    }


    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        if (newTaskDatetxt.getValue() == null || newTaskNametxt.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").show();
            return;
        }

        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.getStyleClass().add("task-card");

        Label taskNameLabel = new Label("Task: " + newTaskNametxt.getText().trim());
        taskNameLabel.getStyleClass().add("task-name");
        hBox.getChildren().add(taskNameLabel);

        String taskDate = newTaskDatetxt.getValue().toString();
        Label dateLabel = new Label("Date: " + taskDate);
        dateLabel.getStyleClass().add("task-date");
        hBox.getChildren().add(dateLabel);

        CheckBox checkBox = new CheckBox("Completed");
        checkBox.getStyleClass().add("task-checkbox");
        hBox.getChildren().add(checkBox);

        todolistview.getItems().add(hBox);

        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                // Extract the task name from the Label in the HBox
                String taskName = taskNameLabel.getText().replace("Task: ", "").trim();
                String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                System.out.println("Marking task as completed: " + taskName + " on " + currentDate);

                boolean taskCompleted = CompletedTaskController.getInstance().completedTask(taskName, currentDate);
                boolean taskDeleted = ToDoListController.getInstance().deleteCompletedTask(taskName);

                System.out.println("Task Completed: " + taskCompleted);
                System.out.println("Task Deleted: " + taskDeleted);

                if (taskCompleted && taskDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Task Finished").show();
                    todolistview.getItems().remove(hBox);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Task operation failed").show();
                }
            }
        });

        boolean taskAdded = ToDoListController.getInstance().addTask(new ToDoList(null, newTaskNametxt.getText().trim(), taskDate, null));

        if (taskAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Task Added Successfully").show();
            cleartxts();
        } else {
            new Alert(Alert.AlertType.ERROR, "Task Addition Failed").show();
        }
    }


    private void cleartxts() {
        newTaskNametxt.clear();
        newTaskDatetxt.setValue(null);
    }

    public void btcViewCompletedTasksOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/completed_task.fxml"))));
        stage.show();

    }
}
