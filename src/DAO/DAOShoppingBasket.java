package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class DAOShoppingBasket {
    private int shoppingBasketNumber;
    private ArrayList<DAOShoppingHistory> daoShoppingHistories;
    private DAOShoppingHistory daoShoppingHistory;

    public DAOShoppingBasket() {
        daoShoppingHistories = new ArrayList<>();
        daoShoppingHistory = new DAOShoppingHistory();
    }

    public DAOShoppingBasket(int shoppingBasketNumber) {
        this.shoppingBasketNumber = shoppingBasketNumber;
        this.daoShoppingHistories=getDaoShoppingHistory().getShoppingHistories(this.getShoppingBasketNumber());
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

    public int getBasketSize(){
        int size=-1;
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
            size=rs.getRow();
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
}