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

public class LoginController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showLoginDialog(Stage owner) {
        String message = "로그인";
        boolean pass = false;
        while (!pass) {
            // Show dialog
            Optional input = getDialog(owner, message).showAndWait();

            if (input.isPresent()) {
                if ((boolean) input.get())
                    pass = true;
                else
                    message = "비밀번호 재입력!!";
            }
        }
    }

    private Dialog getDialog(Stage owner, String message) {
        Dialog dialog = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/Login.fxml")
            );
            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane(loader.load());
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            // Add button to dialog
            ButtonType buttonTypeOk = new ButtonType("로그인", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            PasswordField passwordField = new PasswordField();
            Label label = new Label(message);
            grid.add(label,0,0);
            grid.add(passwordField, 0, 1);
            passwordField.getScene();

            // Result converter for dialog
            dialog.setResultConverter(param -> {
                if (param == buttonTypeOk) {
                    return checkPassword(passwordField.getText());
                }
                System.exit(0); // 시스템 종료
                return false;
            });
        } catch (IOException e) {
            System.out.println("Unable to load dialog FXML");
            e.printStackTrace();
        }
        return dialog;
    }


    /* Todo 여기서 Password 확인해서 로그인 처리 해야 */
    private boolean checkPassword(String input) {
        String password = "admin";
        return input.equals(password);
    }
}
