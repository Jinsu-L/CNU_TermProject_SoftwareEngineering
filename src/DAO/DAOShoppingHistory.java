package DAO;

import DBCP.ConnectionManager;
import java.sql.*;
import java.util.Vector;

public class DAOShoppingHistory {
    private int shoppingHistoryNumber;
    private int itemQuantity;
    private DAOShoppingBasket daoShoppingBasket;
    private DAOItem daoItem;

    private static int shoppingHistoryCount=0;

    public DAOShoppingHistory() {
    }

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

    //장바구니 내역 전체 삭제
    public void deleteHistory(int shoppingBasketNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "DELETE FROM shopping_history WHERE shopping_basket_number=" + shoppingBasketNumber;
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

    public void deleteHistory(int shoppingBasketNumber,String itemName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "DELETE FROM shopping_history WHERE shopping_basket_number="+shoppingBasketNumber+"AND item_name = '"+itemName+"'";
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

    //아이템 수량 변경
    public void updateHistory(int shoppingBasketNumber, String itemName, int itemQuantity){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE FROM shopping_history SET item_quantity="+itemQuantity+"WHERE shopping_basket_number="+shoppingBasketNumber+"AND item_name='"+itemName+"'";        try {
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

    //상품 담기
    public  void insertHistory(int shoppingHistoryNumber,String itemName){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM shopping_history WHERE shopping_history_number ='"+shoppingHistoryNumber+"' AND  item_name='"+itemName+"'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            if(rs.next()){
                updateHistory(shoppingHistoryNumber,itemName,rs.getInt("item_quantity")+1);
            }else{
                query="INSERT INTO shopping_history VALUES ("+shoppingHistoryNumber+", 1, "+ shoppingHistoryCount++ +","+itemName+"'')";
                pstmt=conn.prepareStatement(query);
                pstmt.executeUpdate();
            }
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
    }
}
