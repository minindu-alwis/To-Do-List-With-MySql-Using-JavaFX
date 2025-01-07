package Controller.completedtask;

import Connection.DBConnection;
import models.CompletedTask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class CompletedTaskController implements CompletedTaskService {

    private static CompletedTaskService instance;

    private CompletedTaskController() {
    }

    public static CompletedTaskService getInstance() {
        return instance == null ? instance = new CompletedTaskController() : instance;
    }

    @Override
    public boolean completedTask(String taskName, String date) {
        try {
            PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(
                    "SELECT task_id, user_id FROM todolist WHERE task_name = ?");
            stmt.setString(1, taskName);

            ResultSet rst = stmt.executeQuery();
            if (rst.next()) {
                // Get the task_id from todolist to insert into completedtask
                String taskId = rst.getString("task_id");

                PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement(
                        "INSERT INTO completedtask (comtask_id, task_name, user_id, completed_date) VALUES (?, ?, ?, ?)"
                );
                prepareStm.setString(1, generateId());  // This should be a unique ID for the completed task
                prepareStm.setString(2, taskName);
                prepareStm.setString(3, rst.getString("user_id"));
                prepareStm.setString(4, date);

                // Ensure you're associating the correct task_id in the completedtask table
                return prepareStm.executeUpdate() > 0;
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting completed task", e);
        }
    }




    private String generateId() {
        try {
            // Use the correct field (comtask_id) to generate the ID
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT comtask_id FROM completedtask ORDER BY comtask_id DESC LIMIT 1"
            );
            if (rst.next()) {
                String existId = rst.getString(1);
                return String.format("P%04d", Integer.parseInt(existId.substring(1)) + 1);
            } else {
                return "P0001"; // First ID
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating task ID", e);
        }
    }

    @Override
    public ArrayList<CompletedTask> loadCompletedTask() {
       return null;
    }
}
