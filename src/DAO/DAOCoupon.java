package DAO;

import DBCP.ConnectionManager;

import java.sql.*;

public class DAOCoupon {

    private String couponNumber;
    private int couponAmount;

    public DAOCoupon() {
    }

    public DAOCoupon(String couponNumber, int couponAmount) {
        this.couponNumber = couponNumber;
        this.couponAmount = couponAmount;
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public int getCouponAmount(String couponNumber){
        int couponAmount=0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = String.format("SELECT * FROM coupon WHERE coupon_number = '%s'",couponNumber);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            couponAmount=rs.getInt("coupon_amount");
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
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String createCoupon(int couponAmount){
        //쿠폰 생성 생각 해야함
        String couponNumber="testtest00";
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("INSERT INTO coupon VALUES('%s', %d)",couponNumber,couponAmount);
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
        return couponNumber;
    }

    public boolean useCoupon(String couponNumber,int useAmount) {
        int amount = getCouponAmount(couponNumber);
        if (amount >= useAmount) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String query = String.format("UPDATE coupon SET coupon_amount=%d WHERE coupon_number='%s'", amount - useAmount, couponNumber);
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
            return true;
        }else{
            return false;
        }
    }
}
