package DAO;

import DBCP.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;

public class DAOCategory {
    private int categoryNumber;
    private String categoryName;

    public DAOCategory() {
    }

    public DAOCategory(String categoryName) {
        this.categoryNumber = getCategoryNumber(categoryName);
        this.categoryName=categoryName;
    }

    public DAOCategory(int categoryNumber, String categoryName) {
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public int getCategoryNumber(String categoryName){
        int result=1;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = String.format("SELECT * FROM category WHERE category_name='%s'",categoryName);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result=rs.getInt("categoryID");
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

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryName(int categoryNumber){
        String result="";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = String.format("SELECT category_name FROM category WHERE category_number=%d",categoryNumber);
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
    public ArrayList<DAOCategory> getCategories(){
        ArrayList<DAOCategory> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM category";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsCategoryNumber=rs.getInt("category_number");
                String rsCategoryName=rs.getString("category_name");
                result.add(new DAOCategory(rsCategoryNumber,rsCategoryName));
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
