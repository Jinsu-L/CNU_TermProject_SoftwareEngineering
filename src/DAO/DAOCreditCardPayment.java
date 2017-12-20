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
    private String cardNumber;
    private int approvalNumber;

    public DAOCreditCardPayment() {
    }

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount) {
        super(paymentNumber, paymentAmount, Type.CARD);
        this.cardCompany = null;
        this.cardNumber = "";
        this.approvalNumber = -1;
    }

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount, Type type) {
        super(paymentNumber, paymentAmount, type);
        this.cardCompany = null;
        this.cardNumber = "";
        this.approvalNumber = -1;
    }
    public DAOCreditCardPayment(int paymentNumber, int paymentAmount,String cardNumber) {
        super(paymentNumber, paymentAmount, Type.CARD);
        this.cardNumber = cardNumber;
    }

    public DAOCreditCardPayment(int paymentNumber, int paymentAmount, String cardCompany, String cardNumber, int approvalNumber) {
        super(paymentNumber, paymentAmount, Type.CARD);
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(int approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public boolean insertPayment(int shoppingBasketNumber, String cardNumber, int paymentAmount) {
        if (cardNumber.length() == 16) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            String query = String.format("INSERT INTO payment (payment_amount, payment_type, payment_date, shopping_basket_number) VALUES (%d,'%s','%s',%d)", paymentAmount, Type.CARD.toString().toLowerCase(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), shoppingBasketNumber);
            try {
                ConnectionManager cm = new ConnectionManager();
                conn = cm.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                query = String.format("INSERT INTO card_payment VALUES (%d, '%s', '%s', %d)", getPaymentSize(), cardNumber, "회사명", 1234);
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
