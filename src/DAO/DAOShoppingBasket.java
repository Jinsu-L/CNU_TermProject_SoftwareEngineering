package DAO;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;

    static private int shoppingBasketCount=0;

    public DAOShoppingBasket() {
        this.shoppingBasketNumber=shoppingBasketCount++;
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

    //상품 전체 삭제
}