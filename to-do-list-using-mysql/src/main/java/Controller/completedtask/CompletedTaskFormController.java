package Controller.completedtask;

import Connection.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import models.CompletedTask;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.*;

public class CompletedTaskFormController implements Initializable {
    public ListView listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTasks();
    }

    private void setTasks() {
        ArrayList<CompletedTask> completedTaskArrayList = CompletedTaskController.getInstance().loadCompletedTask();

        completedTaskArrayList.forEach(completedTask -> {
            VBox vBox = new VBox();
            vBox.getStyleClass().add("vbox");

            Label taskName = new Label(completedTask.getTaskName());
            taskName.getStyleClass().add("task-name-label");

            Label taskCompleteLbl = new Label("Task Completed Date : " + completedTask.getTaskCompletedDate());
            taskCompleteLbl.getStyleClass().add("date-label");

            vBox.getChildren().addAll(taskName, taskCompleteLbl);

            listView.getItems().add(vBox);
        });

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ObservableList<String> items = listView.getItems();
        Object selectedItem = listView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            try {
                String taskName;

                // Check if the selected item is a custom node
                if (selectedItem instanceof VBox) {
                    VBox vbox = (VBox) selectedItem;

                    // Assuming the task name is in a Label inside the VBox
                    Label label = (Label) vbox.getChildren().get(0);
                    taskName = label.getText();
                } else if (selectedItem instanceof String) {
                    // If the item is already a string
                    taskName = (String) selectedItem;
                } else {
                    System.out.println("Invalid selection type.");
                    return;
                }

                // Get the connection using DBConnection
                Connection connection = DBConnection.getInstance().getConnection();

                // Query to delete the task by task_name
                String deleteQuery = "DELETE FROM completedtask WHERE task_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, taskName);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Remove the item from the ListView
                    items.remove(selectedItem);
                    System.out.println("Task deleted successfully.");
                } else {
                    System.out.println("Task not found in the database.");
                }

            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        } else {
            System.out.println("No item selected for deletion.");
        }
    }

}
