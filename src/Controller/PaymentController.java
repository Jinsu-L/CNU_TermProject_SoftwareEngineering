package Controller;

import DAO.DAOCouponPayment;
import DAO.DAOCreditCardPayment;
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
    private int basketNumber;

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
    public boolean PayProgress(Stage owner, int basketNumber, int totalPrice) {
        this.totalPrice = totalPrice;
        this.owner = owner;
        this.basketNumber = basketNumber;
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
            return true;
        } else {
            /* Todo 결제 취소 - 결제 번호를 돌려 놓음 */
            DAOPayment.deletePayment(basketNumber);
            return false;
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
            ButtonType closeButton = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
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

        ButtonType closeButton = new ButtonType("취소", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                int money = 0;
                try {
                    money = Integer.parseInt(pay.getText());
                }catch(NumberFormatException e){
                    alert("에러메시지","숫자 외 문자 입력");
                    return "결제 금액 이상";
                }
                if(money == 0){
                    alert("에러메시지", "숫자 0 입력");
                    return "결제 금액 이상";
                }
                if(money > (totalPrice - paidPrice))
                    money = totalPrice - paidPrice;

                if (!PaymentMoney(money)) {
                    return "결제 금액 이상";
                }
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
        if(new DAOPayment().insertPayment(basketNumber, money)){
            paidPrice += money;
            return true;
        }
        return false;
//        paymentInfo.add(new DAOPayment(0, money));
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
        input.setPromptText("카드 번호 입력");
        input.setVisible(true);

        ButtonType closeButton = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                int money = 0;
                try {
                    money = Integer.parseInt(pay.getText());
                }catch(NumberFormatException e){
                    alert("에러메시지","숫자 외 문자 입력");
                    return "결제 금액 이상";
                }
                if(money == 0){
                    alert("에러메시지", "숫자 0 입력");
                    return "결제 금액 이상";
                }
                if(money > (totalPrice - paidPrice))
                    money = totalPrice - paidPrice;

                if (!PaymentCard(money, input.getText()))
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
        if(new DAOCreditCardPayment().insertPayment(basketNumber, CardNumber, money)) {
            paidPrice += money;
            return true;
        }
        return false;
        //paymentInfo.add(new DAOPayment(0, money)); // 카드 버전으로 수정해야함
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
        input.setPromptText("쿠폰 번호 입력");

        ButtonType closeButton = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType OkButton = new ButtonType("결제", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButton, OkButton);

        // Result converter for dialog
        /* Todo 단순 취소, 결제 성공, 결제 금액 이상 나누어서 처리할 수 있어야함 */
        dialog.setResultConverter(param -> {
            if (param != closeButton) {
                int money = 0;
                try {
                    money = Integer.parseInt(pay.getText());
                }catch(NumberFormatException e){
                    alert("에러메시지","숫자 외 문자 입력");
                    return "결제 금액 이상";
                }
                if(money == 0){
                    alert("에러메시지", "숫자 0 입력");
                    return "결제 금액 이상";
                }
                if(money > (totalPrice - paidPrice))
                    money = totalPrice - paidPrice;
                int result=PaymentCoupon(money, input.getText());
                if (result==1) {
                    alert("에러메시지","쿠폰 번호 조회 불가");
                    return "결제 금액 이상";
                }else if (result==2) {
                    alert("에러메시지","쿠폰 잔액 부족");
                    return "결제 금액 이상";
                }
                if (paidPrice == totalPrice)
                    paymentStatus = false;
                return "성공";
            }
            return "닫기";
        });

        Optional option = payDialog.showAndWait();
        System.out.println(option.get());

    }

    private int PaymentCoupon(int money, String CouponNumber) {
        int result=new DAOCouponPayment().insertPayment(basketNumber, CouponNumber, money);
        if(result==0){
            paidPrice += money;
        }
        return result;
        //paymentInfo.add(new DAOPayment(0, money)); // 쿠폰 버전으로 수정
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

    public void alert(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);

        alert.showAndWait();
    }
}
