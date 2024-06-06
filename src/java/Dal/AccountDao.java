/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class AccountDao extends DBContext {

    public User GetAccount(String gmail, String pass) {

        String sql = "select * from [dbo].[Users] where email=? and pass=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, gmail);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setEmail(rs.getString("email"));
                a.setPass(rs.getString("pass"));
                a.setFullName(rs.getString("fullName"));
                a.setPhone(rs.getInt("phone"));
                a.setAddress(rs.getString("address"));
                a.setRoleId(rs.getInt("roleId"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public User CheckAccountByEmail(String email) {

        String sql = "select * from [dbo].[Users] where email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setEmail(rs.getString("email"));
                a.setPass(rs.getString("pass"));
                a.setFullName(rs.getString("fullName"));
                a.setPhone(rs.getInt("phone"));
                a.setAddress(rs.getString("address"));
                a.setRoleId(rs.getInt("roleId"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void UpdatePassword(String email, String pass) {
        String sql = "UPDATE [dbo].[Users]\n"
                + "   SET [pass] = ?\n"
                + " WHERE email = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pass);
            st.setString(2, email);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String email = "hai31082003@gmail.com";
        String pass = "123";
        AccountDao ad = new AccountDao();
        User u = ad.CheckAccountByEmail(email);
        System.out.println(u.getRoleId());
    }
}
