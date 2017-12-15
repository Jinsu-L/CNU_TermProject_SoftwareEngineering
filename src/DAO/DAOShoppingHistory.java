package DAO;

import DBCP.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;

public class DAOShoppingHistory {
    private int shoppingHistoryNumber;
    private int itemQuantity;
    private int daoShoppingBasketNumber;
    private DAOItem daoItem;

    private static int shoppingHistoryCount=0;

    public DAOShoppingHistory() {
        this.shoppingHistoryNumber=this.getHistorySize();
        this.itemQuantity=0;
        this.daoShoppingBasketNumber=-1;
        this.daoItem=new DAOItem();
    }

    public DAOShoppingHistory(int daoShoppingBasketNumber){
        this.shoppingHistoryNumber=this.getHistorySize();
        this.itemQuantity=0;
        this.daoShoppingBasketNumber=daoShoppingBasketNumber;
        this.daoItem=new DAOItem();
    }

    public DAOShoppingHistory(int shoppingHistoryNumber, int itemQuantity, int daoShoppingBasketNumber, DAOItem daoItem) {
        this.shoppingHistoryNumber = shoppingHistoryNumber;
        this.itemQuantity = itemQuantity;
        this.daoShoppingBasketNumber=daoShoppingBasketNumber;
        this.daoItem = daoItem;
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

    public int getDaoShoppingBasketNumber() {
        return daoShoppingBasketNumber;
    }

    public void setDaoShoppingBasketNumber(int daoShoppingBasketNumber) {
        this.daoShoppingBasketNumber = daoShoppingBasketNumber;
    }

    public DAOItem getDaoItem() {
        return daoItem;
    }

    public void setDaoItem(DAOItem daoItem) {
        this.daoItem = daoItem;
    }

    public static int getShoppingHistoryCount() {
        return shoppingHistoryCount;
    }

    public static void setShoppingHistoryCount(int shoppingHistoryCount) {
        DAOShoppingHistory.shoppingHistoryCount = shoppingHistoryCount;
    }

    //장바구니 내역 전체 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("DELETE FROM shopping_history WHERE shopping_basket_number= %d",shoppingBasketNumber);
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
        return new ArrayList<>();
    }

    //장바구니 선택 삭제
    public ArrayList<DAOShoppingHistory> deleteHistory(int shoppingBasketNumber,String itemName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("DELETE FROM shopping_history WHERE shopping_basket_number = %d AND item_name = '%s'",shoppingBasketNumber,itemName);
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
        return getShoppingHistories(shoppingBasketNumber);
    }

    //아이템 수량 변경
    public ArrayList<DAOShoppingHistory> updateHistory(int shoppingBasketNumber, String itemName, int itemQuantity){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("UPDATE FROM shopping_history SET item_quantity=%d WHERE shopping_basket_number=%d AND item_name='%s'",itemQuantity,shoppingBasketNumber,itemName);
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
        return getShoppingHistories(shoppingBasketNumber);
    }

    //상품 담기
    public ArrayList<DAOShoppingHistory> insertHistory(int shoppingBasketNumber,String itemName){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = String.format("SELECT * FROM shopping_history WHERE shopping_basket_number =%d AND  item_name='%s'",shoppingBasketNumber,itemName);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            if(rs.next()){
                //해당 바구니에 상품 존재시 수량 +1
                updateHistory(shoppingBasketNumber,itemName,rs.getInt("item_quantity")+1);
            }else{
                query=String.format("INSERT INTO shopping_history VALUES (%d, 1, %d,'%s')",shoppingHistoryCount++,shoppingBasketNumber,itemName);
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
        return getShoppingHistories(shoppingBasketNumber);
    }

    //해당 장바구니 번호의 장바구니 내역 반환
    public ArrayList<DAOShoppingHistory> getShoppingHistories(int shoppingBasketNumber){
        ArrayList<DAOShoppingHistory> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = String.format("SELECT * FROM shopping_history WHERE shopping_basket_number  = %d",shoppingBasketNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsShoppingHistoryNumber=rs.getInt("shopping_history_number");
                int rsItemQuantity=rs.getInt("item_quantity");
                int rsShoppingBasketNumber=rs.getInt("shopping_basket_number");
                DAOItem rsDaoItem=daoItem.getItemDetail(rs.getString("item_name"));
                result.add(new DAOShoppingHistory(rsShoppingHistoryNumber,rsItemQuantity,rsShoppingBasketNumber,rsDaoItem));
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
        return result;
    }
    public int getHistorySize(){
        int size=-1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM shopping_history";
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
