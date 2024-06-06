/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TRONG TAI
 */
public class CategoryDao extends DBContext{
     public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Category";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category a = new Category();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                list.add(a);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;

    }
      public static void main(String[] args) {
        // Create an instance of CategoryDao
        CategoryDao categoryDao = new CategoryDao();

        // Call getAllCategory to retrieve all categories from the database
        List<Category> categories = categoryDao.getAllCategory();

        // Display the retrieved categories
        System.out.println("All Categories:");
        for (Category category : categories) {
            System.out.println("ID: " + category.getId());
            System.out.println("Name: " + category.getName());
            System.out.println("---------------------");
        }
    }
}
