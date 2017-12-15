package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

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
    public ArrayList<DAOItem> getItems() {
        ArrayList<DAOItem> result = new ArrayList<>();
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

    //해당 카테고리 상품 요청
    public ArrayList<DAOItem> getItems(String categoryName) {
        ArrayList<DAOItem> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryID(categoryName), categoryName);
        String query = String.format("SELECT * FROM item WHERE categoryID=%d",rsDAOCategory.getCategoryID());
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
    public boolean insertItem(String itemName, int itemPrice, String categoryName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryID(categoryName), categoryName);
        String query=String.format("INSERT INTO item VALUES('%s', %d, %d)",itemName,itemPrice,rsDAOCategory.getCategoryID());
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
        String query = String.format("SELECT * FROM item WHERE item_name='%s'",itemName);
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
        String query=String.format("DELETE FROM item WHERE item_name='%s'",itemName);
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
        String query=String.format("UPDATE FROM item SET item_name='%s', item_price=%d, categoryId=%d WHERE item_name='%s'",newItemName,newItemPrice,newCategoryID,itemName);
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
