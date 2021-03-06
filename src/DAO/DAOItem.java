package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class DAOItem {
    private int itemNumber;
    private String itemName;
    private int itemPrice;
    private DAOCategory daoCategory;

    public DAOItem() {
        this.daoCategory = new DAOCategory();
    }

    public DAOItem(int itemNumber){
        this.itemNumber=itemNumber;
        this.itemName=getItemName(itemNumber);
    }
    public DAOItem(String itemName) {
        this.itemNumber=getItemNumber(itemName);
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

    public int getItemNumber() {
        return itemNumber;
    }

    public int getItemNumber(String itemName){
        int result=1;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = String.format("SELECT * FROM item WHERE item_name='%s'",itemName);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result=rs.getInt("item_number");
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

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }
    public String getItemName(int itemNumber){
        String result="";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs =null;
        String query = String.format("SELECT * FROM item WHERE item_number=%d",itemNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result=rs.getString("item_name");
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
                int rsCategoryNumber = rs.getInt("category_number");
                DAOCategory rsDAOCategory = new DAOCategory(rsCategoryNumber, daoCategory.getCategoryName(rsCategoryNumber));
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

    //해당 카테고리 상품 요청
    public ArrayList<DAOItem> getItems(String categoryName) {
        ArrayList<DAOItem> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryNumber(categoryName), categoryName);
        String query = String.format("SELECT * FROM item WHERE category_number=%d",rsDAOCategory.getCategoryNumber());
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
        DAOCategory rsDAOCategory = new DAOCategory(getDaoCategory().getCategoryNumber(categoryName), categoryName);
        String query=String.format("INSERT INTO item (item_name, item_price, category_number) VALUES('%s', %d, %d)",itemName,itemPrice,rsDAOCategory.getCategoryNumber());
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
    public DAOItem getItemDetail(int itemNumber) {
        DAOItem result = new DAOItem(itemNumber);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = String.format("SELECT * FROM item WHERE item_number=%d", itemNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            result.setItemPrice(rs.getInt("item_price"));
            int rsCategoryNumber = rs.getInt("category_number");
            result.setDaoCategory(new DAOCategory(rsCategoryNumber, daoCategory.getCategoryName(rsCategoryNumber)));
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
        String query = String.format("DELETE FROM item WHERE item_name='%s'", itemName);
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
    public boolean updateItem(String itemName, String newItemName, int newItemPrice, String newCategoryName) {
        boolean ret = true;
        DAOItem result = new DAOItem(itemName);
        Connection conn = null;
        PreparedStatement pstmt = null;
        int newCategoryID = daoCategory.getCategoryNumber(newCategoryName);
        String query=String.format("UPDATE item SET item_name='%s', item_price=%d, category_number=%d WHERE item_name='%s'",newItemName,newItemPrice,newCategoryID,itemName);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
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
        return ret;
    }

    public static int getItemSize() {
        int size = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM shopping_basket";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.last();
            size = rs.getRow();
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
