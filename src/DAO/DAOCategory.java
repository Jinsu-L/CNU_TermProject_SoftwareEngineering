package DAO;

import DBCP.ConnectionManager;
import java.sql.*;

public class DAOCategory {
    private int categoryID;
    private String categoryName;

    public DAOCategory() {
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getCategoryID(String categoryName){
        int result=1;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = "SELECT categoryID FROM category WHERE category_name='"+categoryName+"'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result=rs.getInt(1);
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

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
