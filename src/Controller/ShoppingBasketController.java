package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 * <p>
 * 장바구니 화면에 이벤트 처리를 담당하는 컨트롤러
 * 버튼 종류 :
 * 관리 버튼 - 확장 메뉴 toggle 버튼
 * 상품 관리 - 상품관리 창으로 이동
 * 쿠폰 관리 - 쿠폰발급 모달 동작
 * 매출 현황 - 매출현황 창으로 이동
 * 비밀번호변경 - 비밀번호 변경 모달 동작
 * 로그아웃 - 로그아웃 기능 동작
 * 선택 삭제 - 선택한 물품 삭제
 * 전체 삭제 - 현재 장바구니 내 리스트 전부 삭제
 * 결제 - 결제 기능 모달 동작
 * 적용 - 텍스트 박스에 숫자를 입력 받아서 선택한 상품에 수량을 변경
 * <p>
 * 상품 리스트 클릭 - 해당 상품 포커스
 * <p>
 * 카테고니 내 상품 클릭 - 각 해당하는 상품을 장바구니에 하나씩 등록
 */

public class ShoppingBasketController implements Initializable {
    @FXML
    private Button itemMangementBtn;
    @FXML
    private Button mangementBtn;
    @FXML
    private Button couponBtn;
    @FXML
    private Button salesStatusBtn;
    @FXML
    private Button pwChgBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button slcDelBtn;
    @FXML
    private Button allDelBtn;
    @FXML
    private Button payBtn;
    @FXML
    private Button applyBtn;

    private boolean managementToggle = false;

    @FXML
    private void itemManagementButtonAction(ActionEvent event) {
        Parent ItemManagement = null;
        try {
            ItemManagement = FXMLLoader.load(getClass().getResource("/View/ItemManagement.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(ItemManagement);
        Stage primaryStage = (Stage) itemMangementBtn.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    private void mangementButtonAction(ActionEvent event) {
        System.out.println("managementBtn");
        managementToggle = !managementToggle;
        if (managementToggle) {
            mangementBtn.setText("닫기");
            itemMangementBtn.setVisible(true);
            couponBtn.setVisible(true);
            salesStatusBtn.setVisible(true);
            pwChgBtn.setVisible(true);
            logoutBtn.setVisible(true);
        } else {
            mangementBtn.setText("관리");
            itemMangementBtn.setVisible(false);
            couponBtn.setVisible(false);
            salesStatusBtn.setVisible(false);
            pwChgBtn.setVisible(false);
            logoutBtn.setVisible(false);
        }
    }

    @FXML
    private void couponButtonAction(ActionEvent event) {
        System.out.println("couponBtn");

        CouponController couponController = new CouponController();
        /* Todo 쿠폰 발급 */
        couponController.showCouponDialog((Stage) couponBtn.getScene().getWindow());
    }

    @FXML
    private void salesStatusButtonAction(ActionEvent event) {
        System.out.println("salesStatusBtn");
        Parent SalesStatus = null;
        try {
            SalesStatus = FXMLLoader.load(getClass().getResource("/View/SalesStatus.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(SalesStatus);
        Stage primaryStage = (Stage) salesStatusBtn.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    private void pwChgButtonAction(ActionEvent event) {
        System.out.println("pwChgBtn");
        Stage ownerStage = (Stage) pwChgBtn.getScene().getWindow();
        PasswordChgController passwordChgController = new PasswordChgController();
        passwordChgController.showPwdChgDialog(ownerStage);
    }


    @FXML
    private void logoutButtonAction(ActionEvent event) {
        System.out.println("logoutBtn");
        LogoutController logoutController = new LogoutController();
        logoutController.showLogoutDialog();
    }

    @FXML
    private void slcDelButtonAction(ActionEvent event) {
        System.out.println("slcDelBtn");
    }

    @FXML
    private void allDelButtonAction(ActionEvent event) {
        System.out.println("allDelBtn");
    }

    @FXML
    private void payButtonAction(ActionEvent event) {
        System.out.println("payBtn");

        PaymentController paymentCont = new PaymentController();
        paymentCont.PayProgress((Stage) payBtn.getScene().getWindow(), getTotalPrice());
    }

    @FXML
    private void applyButtonAction(ActionEvent event) {
        System.out.println("applyBtn");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemMangementBtn.setOnAction(this::itemManagementButtonAction);
        mangementBtn.setOnAction(this::mangementButtonAction);
        couponBtn.setOnAction(this::couponButtonAction);
        salesStatusBtn.setOnAction(this::salesStatusButtonAction);
        pwChgBtn.setOnAction(this::pwChgButtonAction);
        logoutBtn.setOnAction(this::logoutButtonAction);
        slcDelBtn.setOnAction(this::slcDelButtonAction);
        allDelBtn.setOnAction(this::allDelButtonAction);
        payBtn.setOnAction(this::payButtonAction);
        applyBtn.setOnAction(this::applyButtonAction);
    }

    public int getTotalPrice() {
        return 25000;
    }
}
