import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

/**
 * @author - 임진수
 */

public class App extends Application {

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

        /* Todo overlay를 넣는 코드 작성해야함 */

        // login 창 띄움
        LoginController loginController = new LoginController();
        loginController.showLoginDialog(primaryStage);
    }
}