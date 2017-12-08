package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.Vector;

public class DAOItem {
    private String itemName;
    private int itemPrice;
    private DAOCategory daoCategory;

    public DAOItem() {
        this.daoCategory = new DAOCategory();
    }

    public DAOItem(String itemName) {
        this.itemName = itemName;
    }

    public DAOItem(String itemName, int itemPrice, String categoryName) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.daoCategory = new DAOCategory(categoryName);
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
        this.itemName = itemName;
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

    //상품 내역 조회
    public Vector<DAOItem> getItems() {
        Vector<DAOItem> result = new Vector<>();
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
                String rsItemName = rs.getString("item_name");
                int rsItemPrice = rs.getInt("item_price");
                int rsCategoryID=rs.getInt("categoryID");
                DAOCategory rsDAOCategory=new DAOCategory(rsCategoryID,daoCategory.getCategoryName(rsCategoryID));
                result.add(new DAOItem(rsItemName,rsItemPrice,rsDAOCategory));
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

    //해당 카테고리 상품 요청청
    public Vector<DAOItem> getItems(String categoryName) {
        Vector<DAOItem> result = new Vector<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryID(categoryName), categoryName);
        String query = "SELECT * FROM item WHERE categoryID='" + rsDAOCategory.getCategoryID() + "'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String rsItemName = rs.getString("item_name");
                int rsItemPrice = rs.getInt("item_price");
                result.add(new DAOItem(rsItemName, rsItemPrice, rsDAOCategory));
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

    //상품 등록 요청
    public void insertItem(String itemName, int itemPrice, String categoryName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryID(categoryName), categoryName);
        String query = "INSERT INTO item VALUES (" + itemName + "," + itemPrice + "," + rsDAOCategory.getCategoryID() + ")";
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

    //상품 정보 요청
    public DAOItem getItemDetail(String itemName) {
        DAOItem result = new DAOItem(itemName);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM item WHERE item_name='" + itemName + "'";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result.setItemPrice(rs.getInt("item_price"));
            int rsCategoryID = rs.getInt("categoryID");
            result.setDaoCategory(new DAOCategory(rsCategoryID, daoCategory.getCategoryName(rsCategoryID)));
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

    //상품 삭제 요청
    public void deleteItem(String itemName) {
        DAOItem result = new DAOItem(itemName);
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "DELETE FROM item WHERE item_name='" + itemName + "'";
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

    //상품 수정 요청
    public void updateItem(String itemName, String newItemName, int newItemPrice, String newCategoryName) {
        DAOItem result = new DAOItem(itemName);
        Connection conn = null;
        PreparedStatement pstmt = null;
        int newCategoryID = daoCategory.getCategoryID(newCategoryName);
        String query = "UPDATE FROM item SET item_name='" + newItemName + "', item_price =" + newItemPrice + ", categoryID=" + newCategoryID + "WHERE item_name='" + itemName + "'";
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
