package DAO;

public class DAOCategory {
    private String CategoryName;

    public DAOCategory() {
    }

    public DAOCategory(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
