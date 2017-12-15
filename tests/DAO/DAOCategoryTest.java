package DAO;

import org.junit.*;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DAOCategoryTest {
    @Test
    public void getCategoriesTest(){
        DAOCategory daoCategory=new DAOCategory();
        ArrayList<DAOCategory> getlist=daoCategory.getCategories();
        ArrayList<DAOCategory> testlist=new ArrayList<>();
        testlist.add(new DAOCategory(1,"세트"));
        testlist.add(new DAOCategory(2,"단품"));
        testlist.add(new DAOCategory(3,"사이드"));
        testlist.add(new DAOCategory(4,"음료수"));
        testlist.add(new DAOCategory(5,"기타"));
        assertThat(getlist.size(),is(testlist.size()));
        for(int i=0;i<5;i++){
            assertThat(getlist.get(i).getCategoryID(),is(testlist.get(i).getCategoryID()));
            assertThat(getlist.get(i).getCategoryName(),is(testlist.get(i).getCategoryName()));
        }
    }

}