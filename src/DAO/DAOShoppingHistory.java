package DAO;

public class DAOShoppingHistory {
    private int shoppingHistoryNumber;
    private int itemQuantity;

    public DAOShoppingHistory(int shoppingHistoryNumber) {
        this.shoppingHistoryNumber = shoppingHistoryNumber;
    }

    public DAOShoppingHistory(int shoppingHistoryNumber, int itemQuantity) {
        this.shoppingHistoryNumber = shoppingHistoryNumber;
        this.itemQuantity = itemQuantity;
    }

    public int getShoppingHistoryNumber() {
        return shoppingHistoryNumber;
    }

    public void setShoppingHistoryNumber(int shoppingHistoryNumber) {
        this.shoppingHistoryNumber = shoppingHistoryNumber;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
