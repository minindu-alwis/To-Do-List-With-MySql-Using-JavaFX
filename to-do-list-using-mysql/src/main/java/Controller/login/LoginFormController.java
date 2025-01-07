package Controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public TextField txtusername;
    public TextField txtpassword;


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        if (LoginController.getInstance().authenticateUser(txtusername.getText(),txtpassword.getText())){
            Stage stage=new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/todolist_form.fxml"))));
            stage.show();

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please Create Account and After Login...").show();
        }
    }



    public void btnSingUpOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/singup_form.fxml"))));
        stage.show();
    }


}
