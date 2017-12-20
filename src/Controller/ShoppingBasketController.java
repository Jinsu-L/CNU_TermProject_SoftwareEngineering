package Controller;

import DAO.DAOCategory;
import DAO.DAOItem;

import DAO.DAOShoppingBasket;
import DAO.DAOShoppingHistory;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
    @FXML
    private Tab TabSet;
    @FXML
    private Tab TabSingle;
    @FXML
    private Tab TabSide;
    @FXML
    private Tab TabDrink;
    @FXML
    private Tab TabETC;
    @FXML
    private TextField amountTF;
    @FXML
    private TableView<TableRowDataModel> basketList;
    @FXML
    private TableColumn<TableRowDataModel, String> itemName;
    @FXML
    private TableColumn<TableRowDataModel, Integer> amount;
    @FXML
    private TableColumn<TableRowDataModel, Integer> price;

    DAOShoppingBasket shoppingBasket;


    private boolean managementToggle = false;

    @FXML
    private void itemManagementButtonAction(ActionEvent event) {
        shoppingBasket.deleteBasket();
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
        shoppingBasket.deleteBasket();
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
        if (logoutController.showLogoutDialog()) {
            shoppingBasket.deleteBasket();
            System.exit(0);
        }

    }

    @FXML
    private void slcDelButtonAction(ActionEvent event) {
        System.out.println("slcDelBtn");
        int index = basketList.getSelectionModel().getSelectedIndex();
//        if (!tempList.isEmpty() && index >= 0)
//            tempList.remove(index);
        TableRowDataModel selectedModel = basketList.getItems().get(index);
        String name = selectedModel.itemName.getValue();
        basketList.setItems(convertHistoryArrayListToObservableList(shoppingBasket.deleteHistory(shoppingBasket.getShoppingBasketNumber(), name)));
    }

    @FXML
    private void allDelButtonAction(ActionEvent event) {
        System.out.println("allDelBtn");
//        tempList.clear();
//        refreshBasketList();
        basketList.setItems(convertHistoryArrayListToObservableList(shoppingBasket.deleteHistory(shoppingBasket.getShoppingBasketNumber())));

    }

    @FXML
    private void payButtonAction(ActionEvent event) {
        System.out.println("payBtn");

        PaymentController paymentCont = new PaymentController();
        if (paymentCont.PayProgress((Stage) payBtn.getScene().getWindow(), shoppingBasket.getShoppingBasketNumber(), getTotalPrice())) {
            shoppingBasket = new DAOShoppingBasket();
            refreshBasketList();
        }
    }

    @FXML
    private void applyButtonAction(ActionEvent event) {
        System.out.println("applyBtn");
        int index = basketList.getSelectionModel().getSelectedIndex();
        String temp = amountTF.getText();
        if (!"".equals(temp) && temp.matches("^[0-9]*$") && index >= 0) {
            if(Integer.parseInt(temp) <= 0) {
                alert("에러메시지", "수량 0");
                return;
            }
//            TableRowDataModel modifyModel = basketList.getItems().get(index);
//            IntegerProperty modify = new SimpleIntegerProperty(Integer.parseInt(temp));
//            modifyModel.amount = modify;
//            basketList.getItems().set(index, modifyModel);
            TableRowDataModel selectedModel = basketList.getItems().get(index);
            String name = selectedModel.itemName.getValue();
            basketList.setItems(convertHistoryArrayListToObservableList(shoppingBasket.updateHistory(shoppingBasket.getShoppingBasketNumber(), name, Integer.parseInt(temp))));
            amountTF.clear();
        } else if (index < 0) {
            alert("에러메시지", "수정 상품 미선택!");
        } else {
            alert("에러메시지", "수량 문자");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hello!!");
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
        shoppingBasket = new DAOShoppingBasket();
        refreshTab();
        refreshBasketList();
    }

    public int getTotalPrice() {
        return shoppingBasket.getBasketTotalPrice(shoppingBasket.getShoppingBasketNumber());
    }

    private int startX = 10;
    private int startY = 20;
    private int buttonSize = 90;
    private int gap = 15;

    public void refreshTab() {
        ArrayList categories = (new DAOCategory()).getCategories();
        settingTab(TabSet, "세트");
        settingTab(TabSingle, "단품");
        settingTab(TabDrink, "음료수");
        settingTab(TabSide, "사이드");
        settingTab(TabETC, "기타");
    }

    public void settingTab(Tab tab, String CategoryName) {
        AnchorPane content = (AnchorPane) tab.getContent();
        ArrayList singleList = getItemListByCategory(CategoryName);
        for (int i = 0; i < singleList.size(); i++) {
            DAOItem temp = (DAOItem) singleList.get(i);
            int indexX = i % 4;
            int indexY = i / 4;
            Button button = new Button();
            button.setPrefWidth(buttonSize);
            button.setPrefHeight(buttonSize);
            button.setLayoutX(startX + indexX * gap + (buttonSize * indexX));
            button.setLayoutY(startY + indexY * gap + (buttonSize * indexY));
            button.setText(temp.getItemName() + "\n" + "(" + temp.getItemPrice() + ")");
            button.setTextAlignment(TextAlignment.CENTER);
            button.setOnAction(this::menuItemAction);
            button.setWrapText(true);
            button.setId(temp.getItemName());
            content.getChildren().add(button);
        }
    }

    /* Todo 메뉴를 클릭할 시 동작하는 이벤트 리스너 getID에 상품명 넣어둠 */
    @FXML
    private void menuItemAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        System.out.println(node.getId());
        ArrayList newList = shoppingBasket.insertHistory(shoppingBasket.getShoppingBasketNumber(), node.getId());
        basketList.setItems(convertHistoryArrayListToObservableList(newList));
    }


    public ArrayList<DAOItem> getItemListByCategory(String categoryName) {
        return new DAOItem().getItems(categoryName);
    }

    public void refreshBasketList() {
        itemName.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        basketList.setItems(convertHistoryArrayListToObservableList(shoppingBasket.getDaoShoppingHistories()));
    }

    public ObservableList<TableRowDataModel> convertHistoryArrayListToObservableList(ArrayList Histories) {
        ObservableList<TableRowDataModel> tempList = FXCollections.observableArrayList();
        for (Object history : Histories) {
            SimpleStringProperty ItemName = new SimpleStringProperty(((DAOShoppingHistory) history).getDaoItem().getItemName());
            int amount = ((DAOShoppingHistory) history).getItemQuantity();
            int price = ((DAOShoppingHistory) history).getDaoItem().getItemPrice();
            tempList.add(new TableRowDataModel(ItemName, new SimpleIntegerProperty(amount), new SimpleIntegerProperty(amount * price)));
        }
        return tempList;
    }

    public void alert(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);

        alert.showAndWait();
    }

    public class TableRowDataModel {
        private StringProperty itemName;
        private IntegerProperty amount;
        private IntegerProperty price;

        public TableRowDataModel(StringProperty name, IntegerProperty amount, IntegerProperty price) {
            this.itemName = name;
            this.amount = amount;
            this.price = price;
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public IntegerProperty amountProperty() {
            return amount;
        }

        public IntegerProperty priceProperty() {
            return price;
        }
    }


}
