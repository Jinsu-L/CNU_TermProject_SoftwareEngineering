package DAO;

import java.text.SimpleDateFormat;
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

    public DAOPayment(int paymentNumber,int paymentAmount) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount=paymentAmount;
        this.paymentType=Type.CASH;
        this.paymentStatus=false;
        this.paymentDate= new SimpleDateFormat("yyyy:mm:dd").format( new Date());
    }

    public DAOPayment(int paymentNumber,int paymentAmount, Type type) {
        this.paymentNumber = paymentNumber;
        this.paymentAmount=paymentAmount;
        this.paymentType=type;
        this.paymentStatus=false;
        this.paymentDate= new SimpleDateFormat("yyyy:mm:dd").format( new Date());
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
}
