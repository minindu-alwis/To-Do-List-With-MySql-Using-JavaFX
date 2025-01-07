package Controller.login;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginFormController {

    public TextField txtusername;
    public TextField txtpassword;


    public void btnLoginOnAction(ActionEvent actionEvent) {

        if (LoginController.getInstance().authenticateUser(txtusername.getText(),txtpassword.getText())){

            new Alert(Alert.AlertType.INFORMATION, "User Found").show();

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please Create Account and After Login...").show();
        }
    }



    public void btnSingUpOnAction(ActionEvent actionEvent) {




    }


}
