package DAO;

import java.util.ArrayList;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;
    private ArrayList<DAOShoppingHistory> daoShoppingHistories;
    private DAOShoppingHistory daoShoppingHistory;

    static private int shoppingBasketCount = 0;

    public DAOShoppingBasket() {
        this.shoppingBasketNumber = shoppingBasketCount++;
        daoShoppingHistories = new ArrayList<>();
        daoShoppingHistory = new DAOShoppingHistory();
    }

    public DAOShoppingBasket(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
    }

    public int getShoppingBasketNumber() {
        return shoppingBasketNumber;
    }

    public void setShoppingBasketNumber(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
    }

    //상품 담기
    public ArrayList<DAOShoppingHistory> insertHistory(int shoppingBasketNumber, String itemName) {
        return daoShoppingHistory.insertHistory(shoppingBasketNumber, itemName);
    }

    //상품 수량 변경
    public ArrayList<DAOShoppingHistory> updateHistory(int shoppingBasketNumber, String itemName, int itemQuantity) {
        return daoShoppingHistory.updateHistory(shoppingBasketNumber, itemName, itemQuantity);
    }

    //상품 선택 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber, String itemName) {
        return daoShoppingHistory.deleteHistory(shoppingBasketNumber, itemName);
    }

    //상품 전체 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber) {
        return daoShoppingHistory.deleteHistory(shoppingBasketNumber);
    }
}