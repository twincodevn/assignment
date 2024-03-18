package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import model.User;
import utils.DBContext;

public class UserDAO extends DBContext implements BaseDAO<User> {

    @Override
    public boolean insert(User newUser) {
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String hashedPassword = hashPassword(newUser.getPasswordHash());
            
            String sql = "INSERT INTO users (first_name, last_name, phone_number,email,password_hash) VALUES (?, ?, ?, ?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newUser.getFirstName());
                pstmt.setString(2, newUser.getLastName());
                pstmt.setString(3, newUser.getPhoneNumber());
                pstmt.setString(4, newUser.getEmail());
                pstmt.setString(5, hashedPassword);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the insertion was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public User findByEmail(String email) {
    // Define the query with all columns you need from the users table
    String query = "SELECT id, first_name, last_name, phone_number, email, password_hash, is_admin, is_vendor, registered_at, last_login, intro, profile FROM users WHERE email = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
        // Set the email parameter
        ps.setString(1, email);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Fetch all user details from the ResultSet
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");
                String passwordHash = rs.getString("password_hash");
                boolean isAdmin = rs.getBoolean("is_admin");
                boolean isVendor = rs.getBoolean("is_vendor");
                Date registersedAt = rs.getDate("registered_at");
                Date lastLogin = rs.getDate("last_login");
                String intro = rs.getString("intro");
                String profile = rs.getString("profile");

                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhoneNumber(phoneNumber);
                user.setEmail(email);
                user.setPasswordHash(passwordHash);
                user.setAdmin(isAdmin);
                user.setVendor(isVendor);
                user.setRegisteredAt(registersedAt); 
                user.setLastLogin(lastLogin != null ? lastLogin : null); 
                user.setIntro(intro);
                user.setProfile(profile);

                return user;
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log or handle the exception as needed
    }
    return null;
}


    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean update(User obj) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
