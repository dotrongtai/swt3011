/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.Brand;
import Model.Category;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends DBContext{
public List<Product> getAll() {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT p.id AS product_id, p.name AS product_name, p.image AS product_image, p.price AS product_price, p.description AS product_description, p.stock AS product_stock, " +
                 "b.bid AS brand_id, b.name AS brand_name, " +
                 "c.cid AS category_id, c.name AS category_name " +
                 "FROM Product p " +
                 "JOIN Brand b ON p.bid = b.bid " +
                 "JOIN Category c ON p.cid = c.cid";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Brand brand = new Brand(rs.getInt("brand_id"), rs.getString("brand_name"));
            Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));

            Product p = new Product();
            p.setId(rs.getInt("product_id"));
            p.setName(rs.getString("product_name"));
            p.setImage(rs.getString("product_image"));
            p.setPrice(rs.getInt("product_price"));
            p.setDescription(rs.getString("product_description"));
            p.setStock(rs.getInt("product_stock"));
            p.setBrand(brand);
            p.setCategory(category);
            list.add(p);
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return list;
}

public ArrayList<Category> getCategory() {
    ArrayList<Category> list = new ArrayList<>();
    String sql = "SELECT * FROM Category";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Category(rs.getInt("cid"), rs.getString("name")));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return list;
}

public ArrayList<Brand> getBrand() {
    ArrayList<Brand> list = new ArrayList<>();
    String sql = "SELECT * FROM Brand";
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Brand(rs.getInt("bid"), rs.getString("name")));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return list;
}

public void addProduct(Product product) {
    String sql = "INSERT INTO Product (cid, bid, name, image, price, description, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, product.getCategory().getId());
        st.setInt(2, product.getBrand().getId());
        st.setString(3, product.getName());
        st.setString(4, product.getImage());
        st.setDouble(5, product.getPrice()); // Use setDouble for the 'real' type in SQL
        st.setString(6, product.getDescription());
        st.setInt(7, product.getStock());
        st.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e);
    }
}


    // Edit an existing product
 public void editProduct(Product product) {
    String sql = "UPDATE Product SET name = ?, image = ?, price = ?, description = ?, stock = ?, bid = ?, cid = ? WHERE id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, product.getName());
        st.setString(2, product.getImage());
        st.setInt(3, product.getPrice());
        st.setString(4, product.getDescription());
        st.setInt(5, product.getStock());
        st.setInt(6, product.getBrand().getId());
        st.setInt(7, product.getCategory().getId());
        st.setInt(8, product.getId());
        st.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e);
    }
}

    // Delete a product by id
    public void deleteProduct(int id) {
    String sql = "DELETE FROM Product WHERE id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e);
    }
}

public Product getProductById(int productId) {
    Product product = null;
    String sql = "SELECT * FROM Product WHERE id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, productId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            // Create a new Product object and set its properties
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setImage(rs.getString("image"));
            product.setPrice(rs.getInt("price"));
            product.setDescription(rs.getString("description"));
            product.setStock(rs.getInt("stock"));

            // Fetch the category and brand objects using their IDs
            Category category = getCategoryById(rs.getInt("cid"));
            Brand brand = getBrandById(rs.getInt("bid"));

            product.setCategory(category);
            product.setBrand(brand);
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return product;
}

// Utility method to fetch a category by its ID
private Category getCategoryById(int categoryId) throws SQLException {
    String sql = "SELECT * FROM Category WHERE cid = ?";
    PreparedStatement st = connection.prepareStatement(sql);
    st.setInt(1, categoryId);
    ResultSet rs = st.executeQuery();
    if (rs.next()) {
        return new Category(rs.getInt("cid"), rs.getString("name"));
    }
    return null;
}

// Utility method to fetch a brand by its ID
private Brand getBrandById(int brandId) throws SQLException {
    String sql = "SELECT * FROM Brand WHERE bid = ?";
    PreparedStatement st = connection.prepareStatement(sql);
    st.setInt(1, brandId);
    ResultSet rs = st.executeQuery();
    if (rs.next()) {
        return new Brand(rs.getInt("bid"), rs.getString("name"));
    }
    return null;
}
public List<Product> searchByNameProduct(String keyword) {
    List<Product> searchResult = new ArrayList<>();
    String sql = "SELECT p.id AS product_id, p.name AS product_name, p.image AS product_image, p.price AS product_price, p.description AS product_description, p.stock AS product_stock, " +
                 "b.bid AS brand_id, b.name AS brand_name, " +
                 "c.cid AS category_id, c.name AS category_name " +
                 "FROM Product p " +
                 "JOIN Brand b ON p.bid = b.bid " +
                 "JOIN Category c ON p.cid = c.cid " +
                 "WHERE p.name LIKE ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, "%" + keyword + "%");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Brand brand = new Brand(rs.getInt("brand_id"), rs.getString("brand_name"));
            Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));

            Product p = new Product();
            p.setId(rs.getInt("product_id"));
            p.setName(rs.getString("product_name"));
            p.setImage(rs.getString("product_image"));
            p.setPrice(rs.getInt("product_price"));
            p.setDescription(rs.getString("product_description"));
            p.setStock(rs.getInt("product_stock"));
            p.setBrand(brand);
            p.setCategory(category);
            searchResult.add(p);
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return searchResult;
}
public static void main(String[] args) {

    ProductDao productDao = new ProductDao();

    String keyword = "iPhone";
    List<Product> searchResults = productDao.searchByNameProduct(keyword);

    System.out.println("Search Results for keyword: " + keyword);
    for (Product product : searchResults) {
        System.out.println("ID: " + product.getId());
        System.out.println("Name: " + product.getName());
        System.out.println("Image: " + product.getImage());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Stock: " + product.getStock());
        System.out.println("Brand: " + product.getBrand().getName());
        System.out.println("Category: " + product.getCategory().getName());
        System.out.println("---------------------");
    }
}


}
