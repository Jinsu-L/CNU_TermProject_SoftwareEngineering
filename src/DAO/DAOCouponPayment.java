package DAO;


import DBCP.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DAOCouponPayment extends DAOPayment {
    private int paymentNumber;
    private int paymentAmount;
    private Type paymentType;
    private boolean paymentStatus;
    private String paymentDate;
    private String couponNumber;
    private DAOCoupon daoCoupon;

    public DAOCouponPayment() {
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount) {
        super(paymentNumber, paymentAmount);
        this.couponNumber = "";
        daoCoupon=new DAOCoupon();
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, String paymentCouponNumber) {
        super(paymentNumber, paymentAmount, Type.COUPON);
        this.couponNumber = paymentCouponNumber;
        daoCoupon=new DAOCoupon();
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, Type type, String paymentCouponNumber) {
        super(paymentNumber, paymentAmount, type);
        this.couponNumber = paymentCouponNumber;
        daoCoupon=new DAOCoupon();
    }

    public String getPaymentCouponNumber() {
        return couponNumber;
    }

    public void setPaymentCouponNumber(String paymentCouponNumber) {
        this.couponNumber = paymentCouponNumber;
    }

    public boolean insertPayment(int shoppingBasketNumber, String couponNumber, int paymentAmount) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        if (daoCoupon.getCouponAmount(couponNumber) > paymentAmount) {
            String query = String.format("INSERT INTO payment (payment_amount, payment_type, payment_date, shopping_basket_number) VALUES (%d,'%s','%s',%d)", paymentAmount, Type.COUPON.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
            try {
                ConnectionManager cm = new ConnectionManager();
                conn = cm.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                query = String.format("INSERT INTO coupon_payment VALUES (%d,'%s', %d)", paymentNumber, couponNumber, paymentAmount);
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
        return false;
    }
}
