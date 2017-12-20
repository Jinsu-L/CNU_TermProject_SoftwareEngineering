package Controller;

import DAO.DAOItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 */

/* Todo 아이템 관리 기능 추가 */

public class ItemManagementController implements Initializable {
    @FXML
    private Button backBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private ChoiceBox ChoiceCategory;
    @FXML
    private TextField ItemNameTF;
    @FXML
    private TextField PriceBox;
    @FXML
    private TableView<TableRowDataModel> itemListView;
    @FXML
    private TableColumn<TableRowDataModel, String> itemName;
    @FXML
    private TableColumn<TableRowDataModel, String> category;
    @FXML
    private TableColumn<TableRowDataModel, Integer> price;

    @FXML
    private void backButtonAction(ActionEvent event) {
        Parent ShoppingBasket = null;
        try {
            ShoppingBasket = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(ShoppingBasket);
        Stage primaryStage = (Stage) backBtn.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        System.out.println("register");
        String name = ItemNameTF.getText();
        try {
            int price = Integer.parseInt(PriceBox.getText());
            String category = (String) ChoiceCategory.getSelectionModel().getSelectedItem();
            if (!new DAOItem().insertItem(name, price, category)) {
                alert("상품명 중복", "상품명 중복");
            }
        }catch (NumberFormatException n){
            alert("에러메시지","상품 가격 에러");
        }

        loadItemList();
    }

    @FXML
    private void modifyButtonAction(ActionEvent event) {
        System.out.println("modify");
        String oldname = itemListView.getSelectionModel().getSelectedItem().itemName.getValue();
        String newname = ItemNameTF.getText();
        try {
            int price = Integer.parseInt(PriceBox.getText());
            String category = (String) ChoiceCategory.getSelectionModel().getSelectedItem();
            if (!new DAOItem().updateItem(oldname, newname, price, category)) {
                alert("상품명 중복", "상품명 중복");
            }
        }catch (NumberFormatException n){
            alert("에러메시지","상품 가격 오류");
        }
        loadItemList();
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        System.out.println("delete");
        String name = ItemNameTF.getText();
        new DAOItem().deleteItem(name);
        loadItemList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setOnAction(this::backButtonAction);
        registerBtn.setOnAction(this::registerButtonAction);
        modifyBtn.setOnAction(this::modifyButtonAction);
        deleteBtn.setOnAction(this::deleteButtonAction);
        itemName.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        category.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        itemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowDataModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowDataModel> observable, TableRowDataModel oldValue, TableRowDataModel newValue) {
                if (itemList.isEmpty())
                    return;
                TableRowDataModel model = itemListView.getSelectionModel().getSelectedItem();
                System.out.println(model.itemName);
                System.out.println(model.price);
                System.out.println(model.category);
                ItemNameTF.setText(String.valueOf(model.itemName.getValue()));
                PriceBox.setText(String.valueOf(model.price.getValue()));
                ChoiceCategory.getSelectionModel().select(categoryList.indexOf(model.category.getValue()));
            }
        });
        loadCategoryList();
        loadItemList();
        itemListView.getSelectionModel().selectFirst();
    }

    ObservableList<String> categoryList = FXCollections.observableArrayList();
    ObservableList<TableRowDataModel> itemList = FXCollections.observableArrayList();

    public void loadCategoryList() {
        /* Todo 카테고리 DB에서 읽어 오도록 수정 해야함 버그 있음 */
//        ArrayList<DAOCategory> categoryArrayList = new DAOCategory().getCategories();
//        for (DAOCategory category : categoryArrayList) {
//            categoryList.add(category.getCategoryName());
//        }
        categoryList.add("세트");
        categoryList.add("단품");
        categoryList.add("음료수");
        categoryList.add("사이드");
        categoryList.add("기타");

        ChoiceCategory.setItems(categoryList);
    }

    public void loadItemList() {
        itemList.clear();
        ArrayList<DAOItem> itemArrayList = new DAOItem().getItems();
        for (DAOItem item : itemArrayList) {
            itemList.add(new TableRowDataModel(new SimpleStringProperty(item.getItemName()), new SimpleStringProperty(item.getDaoCategory().getCategoryName()), new SimpleIntegerProperty(item.getItemPrice())));
        }
        itemListView.setItems(itemList);
        itemListView.getSelectionModel().selectFirst();
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
        private StringProperty category;
        private IntegerProperty price;

        public TableRowDataModel(StringProperty name, StringProperty category, IntegerProperty price) {
            this.itemName = name;
            this.category = category;
            this.price = price;
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty categoryProperty() {
            return category;
        }

        public IntegerProperty priceProperty() {
            return price;
        }
    }
}
