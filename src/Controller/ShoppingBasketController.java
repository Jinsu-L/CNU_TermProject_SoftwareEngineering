package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
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

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("ButtonClick!!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
