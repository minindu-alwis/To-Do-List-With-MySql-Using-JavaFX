package Controller.signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    public TextField txtNewUserName;
    public TextField txtNewUserPassword;
    public Label userIdDisplay;



    public void btnSingUpOnAction(ActionEvent actionEvent) throws IOException {
        boolean isRegistered = false;
        try {
            isRegistered = SignupController.getInstance().registerUser(
                    new User(
                            userIdDisplay.getText(),
                            txtNewUserName.getText(),
                            txtNewUserPassword.getText()
                    )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isRegistered) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully");
            alert.showAndWait();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
                Parent root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User Not Registerd ! Try Agin Later !").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String id= SignupController.getInstance().generateuserId();
        userIdDisplay.setText(id);
    }
}
