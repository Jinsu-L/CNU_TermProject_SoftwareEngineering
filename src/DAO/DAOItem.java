package DAO;

import DBCP.ConnectionManager;

import javax.management.Query;
import java.sql.*;
import java.util.Vector;

public class DAOItem {
    private String itemName;
    private int itemPrice;
    private int categoryNum;

    public DAOItem(){

    }

    public DAOItem(String itemName,int itemPrice,int categoryNum){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.categoryNum=categoryNum;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

    public Vector<DAOItem> getItems(CategoryType type){
        Vector<DAOItem> result=new Vector<>();
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String query = "SELECT * FROM item WHERE categoryID='"+type.toString()+"'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery(query);
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
            if(conn != null) try { conn.close(); } catch(Exception e) {}
        }
        return result;
    }

    public void insertItem(String itemName, int itemPrice, int categoryNum){
        Connection conn = null;
        PreparedStatement pstmt=null;
        String query = "INSERT INTO item VALUES ('"+itemName+"', "+itemPrice+","+categoryNum+")";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate(query);
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
            if(conn != null) try { conn.close(); } catch(Exception e) {}
        }
    }

    public void deleteItem(String itemName){
        Connection conn = null;
        PreparedStatement pstmt=null;
        String query = "DELETE FROM item WHERE item_name='"+itemName+"')";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate(query);
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
            if(conn != null) try { conn.close(); } catch(Exception e) {}
        }
    }
}
