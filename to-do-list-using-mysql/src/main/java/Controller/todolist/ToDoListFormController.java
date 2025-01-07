package Controller.todolist;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
        ArrayList<ToDoList> todoListArrayList = ToDoListController.getInstance().loadTasks();
        todoListArrayList.forEach(todoList -> {

            VBox vBox = new VBox();
            vBox.getStyleClass().add("task-card");

            // Only add the task name label if it's not null
            String taskText = todoList.getTaskName();
            if (taskText != null) {
                Label taskName = new Label("Task: " + taskText);
                taskName.getStyleClass().add("task-name");
                vBox.getChildren().add(taskName);
            }

            // Only add the date label if it's not null
            String taskDate = todoList.getDate();
            if (taskDate != null) {
                Label date = new Label("Date: " + taskDate);
                date.getStyleClass().add("task-date");
                vBox.getChildren().add(date);
            }

            CheckBox checkBox = new CheckBox("Completed");
            checkBox.getStyleClass().add("task-checkbox");

            vBox.getChildren().add(checkBox);
            todolistview.getItems().add(vBox);
        });
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

        VBox vBox = new VBox();
        vBox.getStyleClass().add("task-card");

        Label taskName = new Label("Task: " + newTaskNametxt.getText());
        taskName.getStyleClass().add("task-name");

        Label date = new Label("Date: " + newTaskDatetxt.getValue());
        date.getStyleClass().add("task-date");

        CheckBox checkBox = new CheckBox("Completed");
        checkBox.getStyleClass().add("task-checkbox");

        vBox.getChildren().addAll(taskName, date, checkBox);
        todolistview.getItems().add(vBox);


        String taskDate = newTaskDatetxt.getValue().toString();

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
