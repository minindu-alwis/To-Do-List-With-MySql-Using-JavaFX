package Controller.todolist;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.ToDoList;

import java.net.URL;
import java.util.ArrayList;
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
            // Load tasks from the controller
            ArrayList<ToDoList> todoListArrayList = ToDoListController.getInstance().loadTasks();

            // Check if the list is not empty
            if (todoListArrayList.isEmpty()) {
                System.out.println("No tasks to display.");
            }

            // Loop through each task in the list
            todoListArrayList.forEach(todoList -> {
                HBox hBox = new HBox();
                hBox.setSpacing(30);  // Set spacing between items in the HBox
                hBox.getStyleClass().add("task-card");

                // Add task name label if it's not null
                String taskText = todoList.getTaskName();
                if (taskText != null && !taskText.isEmpty()) {
                    Label taskName = new Label("Task: " + taskText);
                    taskName.getStyleClass().add("task-name");
                    hBox.getChildren().add(taskName);
                }

                // Add date label if it's not null
                String taskDate = todoList.getDate();
                if (taskDate != null && !taskDate.isEmpty()) {
                    Label date = new Label("Date: " + taskDate);
                    date.getStyleClass().add("task-date");
                    hBox.getChildren().add(date);
                }

                // Add checkbox for completion
                CheckBox checkBox = new CheckBox("Completed");
                checkBox.getStyleClass().add("task-checkbox");
                hBox.getChildren().add(checkBox);

                // Add HBox to the ListView
                todolistview.getItems().add(hBox);
            });

            // Log success message
            System.out.println("Tasks loaded successfully!");
        } catch (Exception e) {
            // Handle any exceptions that occur during the task loading process
            System.err.println("Error loading tasks: " + e.getMessage());
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
        hBox.setSpacing(30);  // Set spacing between items in the HBox
        hBox.getStyleClass().add("task-card");

        // Add task name label if it's not null
        String taskText = newTaskNametxt.getText();
        if (taskText != null && !taskText.isEmpty()) {
            Label taskName = new Label("Task: " + taskText);
            taskName.getStyleClass().add("task-name");
            hBox.getChildren().add(taskName);
        }

        // Add date label if it's not null
        String taskDate = newTaskDatetxt.getValue().toString();
        if (taskDate != null && !taskDate.isEmpty()) {
            Label date = new Label("Date: " + taskDate);
            date.getStyleClass().add("task-date");
            hBox.getChildren().add(date);
        }

        // Add checkbox for completion
        CheckBox checkBox = new CheckBox("Completed");
        checkBox.getStyleClass().add("task-checkbox");
        hBox.getChildren().add(checkBox);

        // Add HBox to the ListView
        todolistview.getItems().add(hBox);

        boolean taskAdded = ToDoListController.getInstance().addTask(new ToDoList(null, newTaskNametxt.getText(), taskDate, null));

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
}
