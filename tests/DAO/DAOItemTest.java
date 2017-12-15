package DAO;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DAOItemTest {

    @Test
    public void insertItemNoOverlap(){
        DAOItem daoItem=new DAOItem();
        assertThat(daoItem.insertItem("testitem",1000,"세트"),is(true));
        daoItem.deleteItem("testitem");
    }

    @Test
    public void insertItemOverlap(){
        DAOItem daoItem=new DAOItem();
        daoItem.insertItem("testitem",1000,"세트");
        assertThat(daoItem.insertItem("testitem",1000,"세트"),is(false));
        daoItem.deleteItem("testitem");
    }

}