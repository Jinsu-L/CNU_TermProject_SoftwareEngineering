package DAO;
enum CategoryType{
    세트,
    단품,
    사이드,
    음료수,
    기타
};

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
