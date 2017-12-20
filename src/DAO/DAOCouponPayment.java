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

    public int insertPayment(int shoppingBasketNumber, String couponNumber, int paymentAmount) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int couponAmount=DAOCoupon.getCouponAmount(couponNumber);
        if(couponAmount==-1){
            return 1;
            //쿠폰 번호 에러
        }else if (DAOCoupon.getCouponAmount(couponNumber) >= paymentAmount) {
            String query = String.format("INSERT INTO payment (payment_amount, payment_type, payment_date, shopping_basket_number) VALUES (%d,'%s','%s',%d)", paymentAmount, Type.COUPON.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
            try {
                ConnectionManager cm = new ConnectionManager();
                conn = cm.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                query = String.format("INSERT INTO coupon_payment VALUES (%d,'%s', %d)", getPaymentSize(), couponNumber, paymentAmount);
                pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                DAOCoupon.useCoupon(couponNumber,paymentAmount);
                pstmt.close();
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
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
        else {
            //잔액부족
            return 2;
        }
    }
}
