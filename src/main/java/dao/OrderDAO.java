package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import model.Order;
import utils.DBContext;

public class OrderDAO extends DBContext {

    public boolean doesUserExist(int userId) throws SQLException, Exception {
        String checkUserSql = "SELECT COUNT(id) FROM [Fruit_Shop].[dbo].[users] WHERE id = ?";
        try (PreparedStatement checkUserStmt = getConnection().prepareStatement(checkUserSql)) {
            checkUserStmt.setInt(1, userId);
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int insertOrder(Order order) throws SQLException, Exception {
        if (!doesUserExist(order.getU_id())) {
            throw new Exception("User ID does not exist.");
        }

        String sql = "INSERT INTO [Fruit_Shop].[dbo].[orders] (u_id, order_date, total_price, order_status, payment_method, shipping_address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getU_id());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(3, order.getTotalPrice());
            statement.setString(4, order.getOrderStatus());
            statement.setString(5, order.getPaymentMethod());
            statement.setString(6, order.getShippingAddress());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }
    public Order getOrderById(int orderId) throws SQLException, Exception {
    String sql = "SELECT * FROM [Fruit_Shop].[dbo].[orders] WHERE id = ?";
    Order order = null;
    
    try (Connection connection = getConnection(); 
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, orderId);
        
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setU_id(resultSet.getInt("u_id"));
                order.setOrderDate(new Date(resultSet.getTimestamp("order_date").getTime()));
                order.setTotalPrice(resultSet.getDouble("total_price"));
                order.setOrderStatus(resultSet.getString("order_status"));
                order.setPaymentMethod(resultSet.getString("payment_method"));
                order.setShippingAddress(resultSet.getString("shipping_address"));
            }
        }
    }
    
    return order;
}


}
