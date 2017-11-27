package DAO;


public class DAOCouponPayment extends DAOPayment {
    private int paymentNumber;
    private int paymentAmount;
    private Type paymentType;
    private boolean paymentStatus;
    private String paymentDate;
    private int couponNumber;

    public DAOCouponPayment(int paymentNumber, int paymentAmount) {
        super(paymentNumber, paymentAmount);
        this.couponNumber =-1;
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, int paymentCouponNumber) {
        super(paymentNumber, paymentAmount, Type.COUPON);
        this.couponNumber = paymentCouponNumber;
    }

    public DAOCouponPayment(int paymentNumber, int paymentAmount, Type type, int paymentCouponNumber) {
        super(paymentNumber, paymentAmount, type);
        this.couponNumber = paymentCouponNumber;
    }

    public int getPaymentCouponNumber() {
        return couponNumber;
    }

    public void setPaymentCouponNumber(int paymentCouponNumber) {
        this.couponNumber = paymentCouponNumber;
    }
}
