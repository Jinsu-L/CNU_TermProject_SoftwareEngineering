package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 */

public class ItemManagementController implements Initializable {
    @FXML private Button backBtn;

    @FXML
    private void backButtonAction(ActionEvent event)  {
        Parent ShoppingBasket = null;
        try {
            ShoppingBasket = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(ShoppingBasket);
        Stage primaryStage = (Stage)backBtn.getScene().getWindow(); // 현재 윈도우 가져오기
        primaryStage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setOnAction(this::backButtonAction);
    }
}
