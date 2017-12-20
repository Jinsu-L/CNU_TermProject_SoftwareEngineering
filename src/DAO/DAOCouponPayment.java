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
    private int couponNumber;
    private DAOCoupon daoCoupon;

    public DAOCouponPayment(int paymentNumber, int paymentAmount) {
        super(paymentNumber, paymentAmount);
        this.couponNumber = -1;
        daoCoupon=new DAOCoupon();
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, int paymentCouponNumber) {
        super(paymentNumber, paymentAmount, Type.COUPON);
        this.couponNumber = paymentCouponNumber;
        daoCoupon=new DAOCoupon();
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, Type type, int paymentCouponNumber) {
        super(paymentNumber, paymentAmount, type);
        this.couponNumber = paymentCouponNumber;
        daoCoupon=new DAOCoupon();
    }

    public int getPaymentCouponNumber() {
        return couponNumber;
    }

    public void setPaymentCouponNumber(int paymentCouponNumber) {
        this.couponNumber = paymentCouponNumber;
    }

    public boolean insertPayment(int shoppingBasketNumber, String couponNumber, int paymentAmount) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        if (daoCoupon.getCouponAmount(couponNumber) > paymentAmount) {
            String query = String.format("INSERT INTO payment VALUES (%d,%d,'%s','%s',%d)", paymentNumber, paymentAmount, Type.COUPON.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
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
