package Controller.todolist;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ToDoListFormController implements Initializable {


    public Label usenametxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
    }

    private void setName(){
        String name=ToDoListController.getInstance().getuUserName();
        usenametxt.setText(name);
    }

}
