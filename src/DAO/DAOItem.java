package DAO;

import DBCP.ConnectionManager;

import java.sql.*;

public class DAOItem {
    private String itemName;
    private int itemPrice;

    public DAOItem(){

    }

    public DAOItem(String itemName, int itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        //이런 형식으로 작성
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = "SHOW TABLES";

        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println("result=>"+rs.getString(1));
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
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
