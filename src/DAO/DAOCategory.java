package DAO;

import DBCP.ConnectionManager;
import java.sql.*;
import java.util.Vector;

public class DAOCategory {
    private int categoryID;
    private String categoryName;

    public DAOCategory() {
    }

    public DAOCategory(String categoryName) {
        this.categoryID = getCategoryID(categoryName);
        this.categoryName=categoryName;
    }

    public DAOCategory(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
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

    public String getCategoryName(int categoryID){
        String result="";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = "SELECT category_name FROM category WHERE categoryID='"+categoryID+"'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result=rs.getString("category_name");
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //카테고리 내역 조회
    public Vector<DAOCategory> getCategories(){
        Vector<DAOCategory> result = new Vector<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM item";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsCategoryID=rs.getInt("categoryID");
                String rsCategoryName=rs.getString("category_name");
                result.add(new DAOCategory(rsCategoryID,rsCategoryName));
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
}
