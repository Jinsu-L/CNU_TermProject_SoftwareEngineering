package DAO;

import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DAOCouponTest {
    @Test
    public void getCouponNumberTest(){
        DAOCoupon testCoupon=new DAOCoupon();
        assertThat(testCoupon.createCoupon(30000).getCouponAmount(),is(30000));
    }
}