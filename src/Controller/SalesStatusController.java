package Controller;

import DAO.DAOPayment;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author - 임진수
 */

public class SalesStatusController implements Initializable {
    @FXML
    private TableView statusView;
    @FXML
    private TableColumn<TableRowDataModel, String> date;
    @FXML
    private TableColumn<TableRowDataModel, String> daytotal;
    @FXML
    private Button backBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;

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
    private void searchButtonAction(ActionEvent event) {
        System.out.println("search");
        LocalDate startDate = start.getValue();
        LocalDate endDate = end.getValue();
        boolean al = false;

        if (LocalDate.now().compareTo(startDate) < 0) {
            start.setValue(LocalDate.now());
            startDate = LocalDate.now();
            al =true;
        }
        if (LocalDate.now().compareTo(endDate) < 0) {
            end.setValue(LocalDate.now());
            endDate = LocalDate.now();
            al = true;
        }
        if (al)
            alert("에러 메시지", "미래 날짜 조회");


        System.out.println(startDate.toString());
        System.out.println(endDate.toString());
        ArrayList<Pair<String, Integer>> PaymentList = DAOPayment.selectPayment(startDate.toString(), endDate.toString());
//        ArrayList<Pair<String, Integer>> PaymentList = new ArrayList();
//        PaymentList.add(new Pair<>("2017-12-14", 10000));
//        PaymentList.add(new Pair<>("2017-12-15", 20000));
//        PaymentList.add(new Pair<>("2017-12-13", 17500));
        statusView.setItems(convertPaymentArrayListToObservableList(PaymentList));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setOnAction(this::backButtonAction);
        searchBtn.setOnAction(this::searchButtonAction);
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        daytotal.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        end.setValue(LocalDate.now());
        LocalDate date = LocalDate.now().minusMonths(1);
        start.setValue(date);
    }

    public ObservableList<TableRowDataModel> convertPaymentArrayListToObservableList(ArrayList Payments) {
        ObservableList<TableRowDataModel> tempList = FXCollections.observableArrayList();
        for (Object dateAndPrice : Payments) {
            String oldstring = (String) ((Pair) dateAndPrice).getKey();
            try {
                Date old = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
                String newstring = new SimpleDateFormat("yyyy년MM월dd일").format(old);

            SimpleStringProperty date = new SimpleStringProperty(newstring);
            int price = (int) ((Pair) dateAndPrice).getValue();
            tempList.add(new TableRowDataModel(date, new SimpleStringProperty(price + "원")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return tempList;
    }


    public class TableRowDataModel {
        private StringProperty date;
        private StringProperty price;

        public TableRowDataModel(StringProperty date, StringProperty price) {
            this.date = date;
            this.price = price;
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty priceProperty() {
            return price;
        }
    }

    public void alert(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);

        alert.showAndWait();
    }
}
