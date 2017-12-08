package Controller;

import DAO.DAOPayment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 */

public class PaymentController implements Initializable {
    Stage owner;
    Dialog dialog = null;

    ArrayList<DAOPayment> paymentInfo = new ArrayList();

    private boolean paymentStatus = true;
    private String paytype = "";

    private int totalPrice = 0;
    private int paidPrice = 0;

    private Text totalPriceText;
    private Text paidPriceText;
    private Text remindPriceText;

    private Button cash;
    private Button card;
    private Button coupon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /* Todo 장바구니 번호 가져오도록 수정 */
    public void PayProgress(Stage owner, int totalPrice) {
        this.totalPrice = totalPrice;
        this.owner = owner;
        do {
            paymentStatus = false;
            paytype = "";
            getMainProgressDialog();
            dialog.showAndWait();

            switch (paytype) {
                case "cash":
                    showCashPayProgress();
                    break;
                case "card":
                    showCardPayProgress();
                    break;
                case "coupon":
                    showCouponPayProgress();
                    break;
                default:

                    break;
            }

        } while (paymentStatus);

        if (paidPrice == totalPrice) {
            /* Todo DB에 결제 내역 반영  */
            System.out.println("결제 성공!!");
        }
    }

    private Dialog getMainProgressDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/Payment.fxml")
            );
            Parent root = loader.load();
            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane((DialogPane) root);
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            GridPane grid = (GridPane) dialog.getDialogPane().getContent();
            totalPriceText = (Text) grid.lookup("#TotalCash");
            paidPriceText = (Text) grid.lookup("#PaidPrice");
            remindPriceText = (Text) grid.lookup("#RemindPrice");
            updateText();
            ButtonType closeButton = ButtonType.CLOSE;
            dialog.getDialogPane().getButtonTypes().addAll(closeButton);

            cash = (Button) grid.lookup("#CashBtn");
            card = (Button) grid.lookup("#CardBtn");
            coupon = (Button) grid.lookup("#CouponBtn");

            cash.setOnAction(this::cashProgressAction);
            card.setOnAction(this::cardProgressAction);
            coupon.setOnAction(this::couponProgressAction);


            // Result converter for dialog
            dialog.setResultConverter(param -> {
                if (param != closeButton) {
                    return true;
                }
                System.out.println(param);
                return false;
            });

        } catch (IOException e) {
            System.out.println("Unable to load dialog FXML");
            e.printStackTrace();
        }
        return dialog;
    }

    @FXML
    private void cashProgressAction(ActionEvent event) {
        System.out.println("click! Cash!");
        paytype = "cash";
        paymentStatus = true;
        dialog.close();

    }

    @FXML
    private void cardProgressAction(ActionEvent event) {
        System.out.println("click! Card!");
        paytype = "card";
        paymentStatus = true;
        dialog.close();
    }

    @FXML
    private void couponProgressAction(ActionEvent event) {
        System.out.println("click! Coupon!");
        paytype = "coupon";
        paymentStatus = true;
        dialog.close();
    }

    private void showCashPayProgress() {
        Dialog payDialog = getPayProgressDialog();
        GridPane grid = (GridPane) dialog.getDialogPane().getContent();

        TextField pay = (TextField) grid.lookup("#payPrice");
        Text remind = (Text) grid.lookup("#remind");
        remind.setText(String.valueOf(totalPrice - paidPrice));

        ButtonType closeButton = ButtonType.CLOSE;
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                if (!PaymentMoney(Integer.parseInt(pay.getText())))
                    return "결제 금액 이상";
                if (paidPrice == totalPrice)
                    paymentStatus = false;
                return "성공";
            }
            return "닫기";
        });

        Optional option = payDialog.showAndWait();
        System.out.println(option.get());

    }

    private boolean PaymentMoney(int money) {
        paidPrice += money;
        paymentInfo.add(new DAOPayment(0, money));
        return true;
        /* Todo 일단 paymentInfo에 저장을 해 두었다가 결제가 완료 될 떄 DB에 저장 */
        /* Todo 결제번호는 static 같은 걸로 구현하는게 좋지 않을까? 자동으로 하나씩 올라가도록 */
        /* Todo 남은 금액보다 큰 경우 에러처리는 ?? */
    }

    private void showCardPayProgress() {
        Dialog payDialog = getPayProgressDialog();
        GridPane grid = (GridPane) dialog.getDialogPane().getContent();

        TextField pay = (TextField) grid.lookup("#payPrice");
        Text remind = (Text) grid.lookup("#remind");
        remind.setText(String.valueOf(totalPrice - paidPrice));
        Label label = (Label) grid.lookup("#Label");
        label.setText("카드 번호");
        label.setVisible(true);
        TextField input = (TextField) grid.lookup("#input");
        input.setVisible(true);

        ButtonType closeButton = ButtonType.CLOSE;
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                if (!PaymentCard(Integer.parseInt(pay.getText()), input.getText()))
                    return "결제 금액 이상";
                if (paidPrice == totalPrice)
                    paymentStatus = false;
                return "성공";
            }
            return "닫기";
        });

        Optional option = payDialog.showAndWait();
        System.out.println(option.get());

    }

    private boolean PaymentCard(int money, String CardNumber) {
        paidPrice += money;
        paymentInfo.add(new DAOPayment(0, money)); // 카드 버전으로 수정해야함
        return true;
        /* Todo 일단 paymentInfo에 저장을 해 두었다가 결제가 완료 될 떄 DB에 저장 */
        /* Todo 결제번호는 static 같은 걸로 구현하는게 좋지 않을까? 자동으로 하나씩 올라가도록 */
        /* Todo 남은 금액보다 큰 경우 에러처리는 ?? */
    }

    private void showCouponPayProgress() {
        Dialog payDialog = getPayProgressDialog();
        GridPane grid = (GridPane) dialog.getDialogPane().getContent();

        TextField pay = (TextField) grid.lookup("#payPrice");
        Text remind = (Text) grid.lookup("#remind");
        remind.setText(String.valueOf(totalPrice - paidPrice));
        Label label = (Label) grid.lookup("#Label");
        label.setText("쿠폰 번호");
        label.setVisible(true);
        TextField input = (TextField) grid.lookup("#input");
        input.setVisible(true);

        ButtonType closeButton = ButtonType.CLOSE;
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                if (!PaymentCoupon(Integer.parseInt(pay.getText()), input.getText()))
                    return "결제 금액 이상";
                if (paidPrice == totalPrice)
                    paymentStatus = false;
                return "성공";
            }
            return "닫기";
        });

        Optional option = payDialog.showAndWait();
        System.out.println(option.get());

    }

    private boolean PaymentCoupon(int money, String CouponNumber) {
        paidPrice += money;
        paymentInfo.add(new DAOPayment(0, money)); // 쿠폰 버전으로 수정
        return true;
        /* Todo 일단 paymentInfo에 저장을 해 두었다가 결제가 완료 될 떄 DB에 저장 */
        /* Todo 결제번호는 static 같은 걸로 구현하는게 좋지 않을까? 자동으로 하나씩 올라가도록 */
        /* Todo 남은 금액보다 큰 경우 에러처리는 ?? */
    }


    private Dialog getPayProgressDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/PayProgress.fxml")
            );
            Parent root = loader.load();
            dialog = new Dialog();
            dialog.setResizable(false);
            dialog.setDialogPane((DialogPane) root);
            dialog.initOwner(owner);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

        } catch (IOException e) {
            System.out.println("Unable to load dialog FXML");
            e.printStackTrace();
        }
        return dialog;
    }

    private void updateText() {
        totalPriceText.setText(String.valueOf(totalPrice));
        paidPriceText.setText(String.valueOf(paidPrice));
        remindPriceText.setText(String.valueOf(totalPrice - paidPrice));
    }
}
