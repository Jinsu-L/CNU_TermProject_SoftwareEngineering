import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Optional;

/**
 * @author - 임진수
 */

public class App extends Application {
    private Dialog dialog;
    private Text actionStatus;
    private final String defaultVal = "Default text";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("POS");
        primaryStage.setScene(scene);
        primaryStage.show();
        loginDialog(primaryStage);
    }

    public void loginDialog(Stage primaryStage) {

        /* Todo. overlay를 넣는 코드 작성해야함 */

        displayLoginDialog();
    }

    private void displayLoginDialog() {

        // Custom dialog
        Dialog<String> dialog = new Dialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Input Password");
        dialog.setResizable(true);

        // Widgets
        Label label1 = new Label("Password : ");
        PasswordField passwordField = new PasswordField();

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1); // col=1, row=1
        grid.add(passwordField, 2, 1);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("로그인", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType param) {
                if(param == buttonTypeOk)
                    return passwordField.getText();
                System.exit(0);
                return null;
            }
        });

        // Show dialog
        Optional result = dialog.showAndWait();

        if (result.isPresent()) {

            System.out.println(result.get());
        }
    }
}