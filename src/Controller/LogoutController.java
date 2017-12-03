package Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 */

public class LogoutController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showLogoutDialog() {

        Optional input = getDialog().showAndWait();

        if (input.isPresent()) {
            if ((boolean) input.get())
                System.exit(0);
        }
    }

    private Dialog getDialog() {
        Dialog dialog = new Dialog();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/Logout.fxml")
        );
        try {
            dialog.setResizable(false);
            dialog.setDialogPane(loader.load());
            ButtonType buttonTypeOk = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

            dialog.setResultConverter(param -> {
                if (param == buttonTypeOk) {
                    return true;
                }
                return false;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dialog;
    }
}
