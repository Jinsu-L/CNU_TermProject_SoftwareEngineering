package DAO;

import DBCP.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DAOCreditCardPayment extends DAOPayment {
    private int paymentNumber;
    private int paymentAmount;
    private Type paymentType;
    private boolean paymentStatus;
    private String paymentDate;
    private String cardCompany;
    private int cardNumber;
    private int approvalNumber;

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount) {
        super(paymentNumber, paymentAmount, Type.CARD);
        this.cardCompany=null;
        this.cardNumber=-1;
        this.approvalNumber=-1;
    }

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount, Type type) {
        super(paymentNumber, paymentAmount, type);
        this.cardCompany=null;
        this.cardNumber=-1;
        this.approvalNumber=-1;
    }

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount, Type type, String cardCompany, int cardNumber, int approvalNumber) {
        super(paymentNumber, paymentAmount, type);
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.approvalNumber = approvalNumber;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(int approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public boolean insertPayment(int shoppingBasketNumber, String cardNumber, int paymentAmount) {
        if(cardNumber.length()==16) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            String query = String.format("INSERT INTO payment VALUES (%d,%d,'%s','%s',%d)", paymentNumber, paymentAmount, Type.CARD.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
            try {
                ConnectionManager cm = new ConnectionManager();
                conn = cm.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                query = String.format("INSERT INTO card_payment VALUES (%d,'%s', %d)", paymentNumber, cardNumber, paymentAmount);
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
