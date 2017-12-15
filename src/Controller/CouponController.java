package Controller;

import DAO.DAOCoupon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author - 임진수
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CouponController implements Initializable {
    private int selectedCoupon = 5000;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showCouponDialog(Stage owner) {

        Optional input = getCouponInputDialog(owner).showAndWait();

        if (input.isPresent()) {
            if ((boolean) input.get()) {
                System.out.println("[Debug] : selected coupon : " + selectedCoupon);
                getCouponResultDialog(owner).showAndWait();
            }
        }
    }

    private Dialog getCouponInputDialog(Stage owner) {
        Dialog dialog = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/Coupon.fxml")
            );
            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane(loader.load());
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            // Add button to dialog
            ButtonType buttonTypeOk = new ButtonType("SUBMIT", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            ChoiceBox<String> choiceBox = new ChoiceBox<String>();
            choiceBox.setMaxWidth(260.0);
            choiceBox.setMaxHeight(26.0);
            choiceBox.resize(260.0, 26.0);
            ObservableList<String> choiceBoxItems = FXCollections.observableArrayList();
            choiceBoxItems.add("5000");
            choiceBoxItems.add("10000");
            choiceBoxItems.add("30000");
            choiceBoxItems.add("50000");
            choiceBox.setItems(choiceBoxItems);
            choiceBox.getSelectionModel().selectFirst();
            grid.add(choiceBox, 0, 1);


            // Result converter for dialog
            dialog.setResultConverter(param -> {
                if (param == buttonTypeOk) {
                    selectedCoupon = Integer.parseInt(choiceBox.getValue());
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

    private Dialog getCouponResultDialog(Stage owner) {
        Dialog dialog = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/CouponResult.fxml")
            );
            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane(loader.load());
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            // Add button to dialog
            ButtonType buttonTypeOk = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            Text couponNumberField = new Text();
            couponNumberField.setText(getCouponNumber(selectedCoupon));
            Text couponPriceField = new Text();
            couponPriceField.setText(String.valueOf(selectedCoupon));
            grid.add(couponNumberField, 0, 1);
            grid.add(couponPriceField, 0, 3);

        } catch (IOException e) {
            System.out.println("Unable to load dialog FXML");
            e.printStackTrace();
        }
        return dialog;
    }

    /* Todo coupon 발급 기능 */
    private String getCouponNumber(int inputPrice) {
        return new DAOCoupon().createCoupon(inputPrice);
    }
}
