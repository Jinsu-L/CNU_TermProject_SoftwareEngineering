package DAO;

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
}
