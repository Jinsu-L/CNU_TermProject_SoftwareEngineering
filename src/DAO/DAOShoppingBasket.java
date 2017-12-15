package DAO;

import java.util.ArrayList;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;
    private ArrayList<DAOShoppingHistory> daoShoppingHistories;
    private DAOShoppingHistory daoShoppingHistory;

    static private int shoppingBasketCount=0;

    public DAOShoppingBasket() {
        this.shoppingBasketNumber=shoppingBasketCount++;
        daoShoppingHistories=new ArrayList<>();
        daoShoppingHistory=new DAOShoppingHistory();
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

    //상품 수량 변경

    //상품 선택 삭제
    public void deleteHistory(int shoppingBasketNumber,String itemName){
        daoShoppingHistory.deleteHistory(shoppingBasketNumber,itemName);
    }
    //상품 전체 삭제
    public void deleteHistory(int shoppingBasketNumber){
        daoShoppingHistory.deleteHistory(shoppingBasketNumber);
    }
}