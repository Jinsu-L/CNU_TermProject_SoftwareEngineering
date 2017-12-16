package DAO;

import DBCP.ConnectionManager;

import java.sql.*;

public class DAOEnvironment {
    public static String getPassword(){
        String password="";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM environment";
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            rs.next();
            password=rs.getString("password");
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return password;
    }

    public static boolean setPassword(String password,String newPassword, String newPasswordCheck){
        if(!password.equals(getPassword()) || newPassword.length()>10 || !newPassword.equals(newPasswordCheck)){
            return false;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = String.format("UPDATE FROM environment password='%s' WHERE user_number=1",password);
        try {
            ConnectionManager cm = new ConnectionManager();
            conn = cm.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
}
