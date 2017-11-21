package DAO;

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

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }
}
