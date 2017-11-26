package DAO;

public class DAOCategory {
    private String categoryName;

    public DAOCategory() {
    }

    public DAOCategory(String categoryName) {
        categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        categoryName = categoryName;
    }
}
