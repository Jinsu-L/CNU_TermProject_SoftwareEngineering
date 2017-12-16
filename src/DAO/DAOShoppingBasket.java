package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;
    private ArrayList<DAOShoppingHistory> daoShoppingHistories;
    private DAOShoppingHistory daoShoppingHistory;

    public DAOShoppingBasket() {
        insertBasket();
        this.shoppingBasketNumber = getBasketIncrementNum();
        daoShoppingHistories = new ArrayList<>();
        daoShoppingHistory = new DAOShoppingHistory(getShoppingBasketNumber());
    }

    public DAOShoppingBasket(int shoppingBasketNumber) {
        insertBasket();
        this.shoppingBasketNumber = shoppingBasketNumber;
        this.daoShoppingHistory = new DAOShoppingHistory();
        this.daoShoppingHistories = getDaoShoppingHistory().getShoppingHistories(this.getShoppingBasketNumber());
    }

    public int getShoppingBasketNumber() {
        return shoppingBasketNumber;
    }

    public void setShoppingBasketNumber(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
    }

    public ArrayList<DAOShoppingHistory> getDaoShoppingHistories() {
        return daoShoppingHistories;
    }

    public void setDaoShoppingHistories(ArrayList<DAOShoppingHistory> daoShoppingHistories) {
        this.daoShoppingHistories = daoShoppingHistories;
    }

    public DAOShoppingHistory getDaoShoppingHistory() {
        return daoShoppingHistory;
    }

    public void setDaoShoppingHistory(DAOShoppingHistory daoShoppingHistory) {
        this.daoShoppingHistory = daoShoppingHistory;
    }

    //상품 담기
    public ArrayList<DAOShoppingHistory> insertHistory(int shoppingBasketNumber, String itemName) {
        ArrayList<DAOShoppingHistory> result = daoShoppingHistory.insertHistory(shoppingBasketNumber, itemName);
        updateBasketTotalPrice(shoppingBasketNumber);
        return result;
    }

    //상품 수량 변경
    public ArrayList<DAOShoppingHistory> updateHistory(int shoppingBasketNumber, String itemName, int itemQuantity) {
        ArrayList<DAOShoppingHistory> result = daoShoppingHistory.updateHistory(shoppingBasketNumber, itemName, itemQuantity);
        updateBasketTotalPrice(shoppingBasketNumber);
        return result;
    }

    //상품 선택 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber, String itemName) {
        ArrayList<DAOShoppingHistory> result = daoShoppingHistory.deleteHistory(shoppingBasketNumber, itemName);
        updateBasketTotalPrice(shoppingBasketNumber);
        return result;
    }

    //상품 전체 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber) {
        ArrayList<DAOShoppingHistory> result = daoShoppingHistory.deleteHistory(shoppingBasketNumber);
        updateBasketTotalPrice(shoppingBasketNumber);
        return result;
    }

    public void updateBasketTotalPrice(int shoppingBasketNumber) {
        int total = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String select = String.format("SELECT sum(item_price * item_quantity) total FROM shopping_history h,item i WHERE shopping_basket_number = " + shoppingBasketNumber + " and h.item_number = i.item_number");
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(select);
            rs = pstmt.executeQuery();
            rs.last();
            total = rs.getInt("total");
            String update = String.format("UPDATE shopping_basket SET total_amount = '" + total + "' WHERE shopping_basket_number = " + shoppingBasketNumber);
            pstmt = conn.prepareStatement(update);
            pstmt.executeUpdate();
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public int getBasketTotalPrice(int shoppingBasketNumber) {
        int total = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String select = String.format("SELECT sum(item_price * item_quantity) total FROM shopping_history h,item i WHERE shopping_basket_number = " + shoppingBasketNumber + " and h.item_number = i.item_number");
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(select);
            rs = pstmt.executeQuery();
            rs.last();
            total = rs.getInt("total");
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return total;
    }

    public static int getBasketIncrementNum() {
        int size = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM shopping_basket";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.last();
            size = rs.getInt("shopping_basket_number");
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return size;
    }

    public static void insertBasket() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("INSERT INTO shopping_basket (total_amount) VALUES(0)");
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public void deleteBasket() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("DELETE FROM shopping_basket WHERE shopping_basket_number = %d", getBasketIncrementNum());
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
}