package DAO;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;

    public DAOShoppingBasket(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
    }

    public int getShoppingBasketNumber() {
        return shoppingBasketNumber;
    }

    public void setShoppingBasketNumber(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
    }
}
