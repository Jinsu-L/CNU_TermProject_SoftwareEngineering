package DAO;

import DBCP.ConnectionManager;
import javafx.util.Pair;

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
//    private DAOShoppingBasket daoShoppingBasket;


    public DAOPayment() {
    }

    public DAOPayment(int paymentNumber, int paymentAmount) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount = paymentAmount;
        this.paymentType = Type.CASH;
        this.paymentStatus = false;
        this.paymentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        this.daoShoppingBasket=new DAOShoppingBasket();
    }

    public DAOPayment(int paymentNumber, int paymentAmount, Type type) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount = paymentAmount;

        this.paymentType = type;
        this.paymentStatus = false;
        this.paymentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        this.daoShoppingBasket=new DAOShoppingBasket();
    }

    public DAOPayment(int paymentNumber, int paymentAmount, Type paymentType, String paymentDate, DAOShoppingBasket daoShoppingBasket) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;
        this.paymentStatus = true;
        this.paymentDate = paymentDate;
//        this.daoShoppingBasket=daoShoppingBasket;
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
    //Todo 현재는 payment 리스트 반환인데 시퀀스에는 일자별 금액반환 -> ArrayList<Integer>로 하고 금액 계산해서 넘겨야할듯
    public static ArrayList<Pair<String, Integer>> selectPayment(String start, String end) {
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = String.format("SELECT payment_date, sum(Payment_amount) as Payment_amount FROM payment WHERE payment_date BETWEEN '%s' AND '%s' GROUP BY payment_date", start, end);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
//                int rsPaymentNumber=rs.getInt("payment_number");
//                int rsPaymentAmount=rs.getInt("payment_amount");
//                Type rsPaymentType=Type.valueOf(rs.getString("payment_type"));
//                String rsDate=new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("payment_date"));
//                int rsShoppingBasketNumber=rs.getInt("shopping_basket_number");
//                DAOShoppingBasket rsdaoShoppingBasket=new DAOShoppingBasket(rsShoppingBasketNumber);
                int rsPaymentAmount = rs.getInt("Payment_amount");
                String rsDate = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("payment_date"));
                result.add(new Pair<>(rsDate, rsPaymentAmount));
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
    public boolean insertPayment(int shoppingBasketNumber, int paymentAmount) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("INSERT INTO payment (payment_amount, payment_type, payment_date, shopping_basket_number) VALUES (%d,'%s','%s',%d)", paymentAmount, Type.CASH.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
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

    public static void deletePayment(int shoppingBasketNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("DELETE FROM payment WHERE shopping_basket_number=%d", shoppingBasketNumber);
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

    public static int getPaymentSize() {
        int size = -1;
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
