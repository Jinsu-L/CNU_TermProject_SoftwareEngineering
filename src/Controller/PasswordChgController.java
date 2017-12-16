package Controller;

import DAO.DAOEnvironment;
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

public class PasswordChgController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ini");
    }

    public void showPwdChgDialog(Stage owner) {
        boolean pass = true;
        while (pass) {
            // Show dialog
            Optional input = getDialog(owner).showAndWait();

            if (input.isPresent()) {
                pass = (boolean) input.get();
            }
        }
    }

    private Dialog getDialog(Stage owner) {
        Dialog<Boolean> dialog = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PasswordChg.fxml"));


            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane(loader.load());
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            // Add button to dialog
            ButtonType buttonTypeOk = new ButtonType("확인", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            TextField passwordTextField = new TextField();
            passwordTextField.setPromptText("기존 비밀번호");
            TextField chgPasswordTextField = new TextField();
            chgPasswordTextField.setPromptText("변경할 비밀번호");
            TextField chgPasswordConfirmTextField = new TextField();
            chgPasswordConfirmTextField.setPromptText("변경할 비밀번호 확인");

            grid.add(passwordTextField, 0, 0);
            grid.add(chgPasswordTextField, 0, 1);
            grid.add(chgPasswordConfirmTextField, 0, 2);

            // Result converter for dialog
            dialog.setResultConverter(param -> {
                if (param == buttonTypeOk) {
                    String original = passwordTextField.getText();
                    String newPassword = chgPasswordTextField.getText();
                    String Confirm = chgPasswordConfirmTextField.getText();
                    if (original.equals(DAOEnvironment.getPassword())) {
                        if (newPassword.equals(Confirm)) {
                            DAOEnvironment.setPassword(newPassword);
                            return false;
                        }else{
                            alert("메시지","비밀번호 확인 불일치");
                        }
                    } else {
                        alert("메시지","현재비밀번호 불일치");
                    }
                    return true;
                }
                return false;
            });

        } catch (IOException e) {
            System.out.println("Unable to load dialog FXML");
            e.printStackTrace();
        }
        return dialog;
    }

    public void alert(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);

        alert.showAndWait();
    }
}
