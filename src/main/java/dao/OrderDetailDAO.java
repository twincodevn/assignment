/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Order;
import model.Product;
import utils.DBContext;

/**
 *
 * @author bravee06
 */
public class OrderDetailDAO extends DBContext {

    public void insertOrderDetail(int orderId, CartItem item) throws SQLException, Exception {
        String sql = "INSERT INTO [Fruit_Shop].[dbo].[order_details] (o_id, p_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getQuantity());
            statement.setDouble(4, item.getProduct().getPrice() * item.getQuantity());

            statement.executeUpdate();
        }
    }

    public List<CartItem> getOrderDetailsByOrderId(int orderId) throws SQLException, Exception {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT od.quantity, od.price, p.* FROM [Fruit_Shop].[dbo].[order_details] od INNER JOIN [Fruit_Shop].[dbo].[products] p ON od.p_id = p.id WHERE od.o_id = ?";

        try (Connection connection = getConnection(); // Replace Database.getConnection() with your actual connection method
                 PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartItem item = new CartItem();
                Product product = new Product();
                product.setId(resultSet.getInt("p_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                item.setProduct(product);
                item.setQuantity(resultSet.getInt("quantity"));
                items.add(item);
            }
        }
        return items;
    }

}
