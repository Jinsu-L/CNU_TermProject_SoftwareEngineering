package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

enum Type {
    CARD, COUPON, CASH;
}

public class DAOPayment {
    private int paymentNumber;
    private int paymentAmount;
    private Type paymentType;
    private boolean paymentStatus;
    private String paymentDate;
    private DAOShoppingBasket daoShoppingBasket;

    public DAOPayment(int paymentNumber, int paymentAmount) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount=paymentAmount;
        this.paymentType=Type.CASH;
        this.paymentStatus=false;
        this.paymentDate= new SimpleDateFormat("yyyy-MM-dd").format( new Date());
        this.daoShoppingBasket=new DAOShoppingBasket();
    }

    public DAOPayment(int paymentNumber,int paymentAmount, Type type) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount=paymentAmount;
        this.paymentType=type;
        this.paymentStatus=false;
        this.paymentDate= new SimpleDateFormat("yyyy-MM-dd").format( new Date());
        this.daoShoppingBasket=new DAOShoppingBasket();
    }

    public DAOPayment(int paymentNumber, int paymentAmount, Type paymentType, String paymentDate,DAOShoppingBasket daoShoppingBasket) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
        this.paymentStatus = true;
        this.paymentDate = paymentDate;
        this.daoShoppingBasket=daoShoppingBasket;
    }

    public int getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Type getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Type paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    //매출 현황 조회
    public ArrayList<DAOPayment> selectPayment(String start,String end){
        ArrayList<DAOPayment> result=new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query=String.format("SELECT * FROM payment WHERE payment_date BETWEEN '%s' AND '%s'",start,end);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsPaymentNumber=rs.getInt("payment_number");
                int rsPaymentAmount=rs.getInt("payment_amount");
                Type rsPaymentType=Type.valueOf(rs.getString("payment_type"));
                String rsDate=new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("payment_date"));
                int rsShoppingBasketNumber=rs.getInt("shopping_basket_number");
                DAOShoppingBasket rsdaoShoppingBasket=new DAOShoppingBasket(rsShoppingBasketNumber);
                result.add(new DAOPayment(rsPaymentNumber,rsPaymentAmount,rsPaymentType,rsDate,rsdaoShoppingBasket));
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

    //현금 결제
    public void insertPayment(int shoppingBasketNumber, int paymentAmount){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("INSERT INTO payment VALUES (%d,%d,'%s','%s',%d)",paymentNumber,paymentAmount,Type.CASH.toString().toLowerCase(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),shoppingBasketNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
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

    public void deletePayment(int shoppingBasketNumber){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("DELETE FROM payment WHERE shopping_basket_number=%d",shoppingBasketNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
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

    public static int getPaymentSize(){
        int size=-1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM payment";
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
