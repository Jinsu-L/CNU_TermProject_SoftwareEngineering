package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author - 임진수
 *
 * 장바구니 화면에 이벤트 처리를 담당하는 컨트롤러
 * 버튼 종류 :
 *      관리 버튼 - 확장 메뉴 toggle 버튼
 *      상품 관리 - 상품관리 창으로 이동
 *      쿠폰 관리 - 쿠폰발급 모달 동작
 *      매출 현황 - 매출현황 창으로 이동
 *      비밀번호변경 - 비밀번호 변경 모달 동작
 *      로그아웃 - 로그아웃 기능 동작
 *      선택 삭제 - 선택한 물품 삭제
 *      전체 삭제 - 현재 장바구니 내 리스트 전부 삭제
 *      결제 - 결제 기능 모달 동작
 *      적용 - 텍스트 박스에 숫자를 입력 받아서 선택한 상품에 수량을 변경
 *
 * 상품 리스트 클릭 - 해당 상품 포커스
 *
 * 카테고니 내 상품 클릭 - 각 해당하는 상품을 장바구니에 하나씩 등록
 */

public class ShoppingBasketController implements Initializable{
    @FXML private Button itemMangementBtn;
    @FXML private Button mangementBtn;
    @FXML private Button couponBtn;
    @FXML private Button salesStatusBtn;
    @FXML private Button pwChgBtn;
    @FXML private Button logoutBtn;
    @FXML private Button slcDelBtn;
    @FXML private Button allDelBtn;
    @FXML private Button payBtn;
    @FXML private Button applyBtn;

    @FXML
    private void itemManagementButtonAction(ActionEvent event) throws Exception{
        Parent ItemManagement = FXMLLoader.load(getClass().getResource("/View/ItemManagement.fxml"));
        Scene scene = new Scene(ItemManagement);
        Stage primaryStage = (Stage)itemMangementBtn.getScene().getWindow(); // 현재 윈도우 가져오기
        primaryStage.setScene(scene);
    }

    @FXML
    private void mangementButtonAction(ActionEvent event) throws Exception{
        System.out.println("managementBtn");
    }

    @FXML
    private void couponButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void salesStatusButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void pwChgButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void slcDelButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void allDelButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void payButtonAction(ActionEvent event) throws Exception{

    }

    @FXML
    private void applyButtonAction(ActionEvent event) throws Exception{

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemMangementBtn.setOnAction(event -> {
            try {
                itemManagementButtonAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
