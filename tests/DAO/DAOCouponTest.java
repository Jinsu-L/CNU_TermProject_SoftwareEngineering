package DAO;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DAOCouponTest {

    @Test
    public void testGetCouponNumber() {
        String couponNumber = "ABCDEFGHIJ";
        DAOCoupon coupon = new DAOCoupon(couponNumber, 5000);
        assertThat(coupon.getCouponNumber(), is(couponNumber));
    }

    @Test
    public void testSetCouponNumber() {
        String couponNumber = "ABCDEFGHIJ";
        String newCouponNumber = "JIHGFEDCBA";
        DAOCoupon coupon = new DAOCoupon(couponNumber, 5000);
        coupon.setCouponNumber(newCouponNumber);
        assertThat(coupon.getCouponNumber(), is(newCouponNumber));
    }

    @Test
    public void testGetCouponAmount() {
        int couponAmount = 5000;
        DAOCoupon coupon = new DAOCoupon("ABCDEFGHIJ", couponAmount);
        assertThat(coupon.getCouponAmount(), is(couponAmount));
    }

    @Test
    public void testSetCouponAmount() {
        int couponAmount = 10000;
        int newCouponAmount = 4700;
        DAOCoupon coupon = new DAOCoupon("ABCDEFGHIJ", couponAmount);
        coupon.setCouponAmount(newCouponAmount);
        assertThat(coupon.getCouponAmount(), is(newCouponAmount));
    }
}