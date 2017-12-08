package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.Vector;

public class DAOItem {
    private String itemName;
    private int itemPrice;
    private DAOCategory daoCategory;

    public DAOItem(){
        this.daoCategory=new DAOCategory();
    }

    public DAOItem(String itemName, int itemPrice, String categoryName) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.daoCategory=new DAOCategory(categoryName);
    }

    public DAOItem(String itemName, int itemPrice, DAOCategory daoCategory) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.daoCategory = daoCategory;
    }

    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName=itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public DAOCategory getDaoCategory() {
        return daoCategory;
    }

    public void setDaoCategory(DAOCategory daoCategory) {
        this.daoCategory = daoCategory;
    }

    public Vector<DAOItem> getItems(String categoryName){
        Vector<DAOItem> result=new Vector<>();
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        DAOCategory rsDAOCategory=new DAOCategory(getDaoCategory().getCategoryID(categoryName),categoryName);
        String query = "SELECT * FROM item WHERE categoryID='"+rsDAOCategory.getCategoryID()+"'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String rsItemName=rs.getString("item_name");
                int rsItemPrice=rs.getInt("item_price");
                result.add(new DAOItem(rsItemName,rsItemPrice,rsDAOCategory));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(pstmt != null) try { pstmt.close(); } catch(Exception e) {}
            if(conn != null) try { conn.close(); } catch(Exception e) {}
        }
        return result;
    }

    
}
