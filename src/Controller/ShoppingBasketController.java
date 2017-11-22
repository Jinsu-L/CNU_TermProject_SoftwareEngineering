package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingBasketController implements Initializable{

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("ButtonClick!!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
