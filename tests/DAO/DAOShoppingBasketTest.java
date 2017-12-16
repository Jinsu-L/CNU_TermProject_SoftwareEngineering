package DAO;

import org.junit.Test;

import static org.junit.Assert.*;

public class DAOShoppingBasketTest {
    @Test
    public void createBasket() {
        DAOShoppingBasket test = new DAOShoppingBasket();
        DAOShoppingBasket.insertBasket();
        test.insertHistory(test.getShoppingBasketNumber(),"동하 버거 세트");
        test.insertHistory(test.getShoppingBasketNumber(),"동하 버거 세트");
        test.insertHistory(test.getShoppingBasketNumber(),"동하 버거 세트");
    }



}