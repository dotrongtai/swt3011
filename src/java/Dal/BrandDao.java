/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;
import Model.Brand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDao extends DBContext{
    public List<Brand> getAllBrand(){
        List<Brand> list = new ArrayList<>();
        try{
           String sql = "SELECT * FROM Brand";
           PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Brand a = new Brand();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                list.add(a);
            }
        }catch(Exception e){
                System.out.println(e);
                }
        return list;
    }
    public static void main(String[] args) {
        // Create an instance of BrandDao
        BrandDao brandDao = new BrandDao();

        // Call getAllBrand to retrieve all brands from the database
        List<Brand> brands = brandDao.getAllBrand();

        // Display the retrieved brands
        System.out.println("All Brands:");
        for (Brand brand : brands) {
            System.out.println("ID: " + brand.getId());
            System.out.println("Name: " + brand.getName());
            System.out.println("---------------------");
        }
    }
}
