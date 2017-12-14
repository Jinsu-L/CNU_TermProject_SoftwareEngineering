package Controller;

import DAO.DAOItem;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    }

    @FXML
    private void modifyButtonAction(ActionEvent event) {
        System.out.println("modify");
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        System.out.println("delete");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setOnAction(this::backButtonAction);
        registerBtn.setOnAction(this::registerButtonAction);
        modifyBtn.setOnAction(this::modifyButtonAction);
        deleteBtn.setOnAction(this::deleteButtonAction);
        loadItemList();
    }

    ObservableList<TableRowDataModel> itemList = FXCollections.observableArrayList();
    @FXML
    private TableView<TableRowDataModel> itemListView;
    @FXML
    private TableColumn<TableRowDataModel, String> itemName;
    @FXML
    private TableColumn<TableRowDataModel, String> category;
    @FXML
    private TableColumn<TableRowDataModel, Integer> price;

    public void loadItemList() {
        ArrayList<DAOItem> itemArrayList = new DAOItem().getItems();
        for (DAOItem item : itemArrayList) {
            itemList.add(new TableRowDataModel(new SimpleStringProperty(item.getItemName()),new SimpleStringProperty(item.getDaoCategory().getCategoryName()),new SimpleIntegerProperty(item.getItemPrice())));
        }

        itemName.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        category.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        itemListView.setItems(itemList);
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
