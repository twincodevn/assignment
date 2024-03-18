package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import utils.DBContext;

public class ProductDAO extends DBContext implements BaseDAO<Product> {

    public List<Product> getAll(int page, int pageSize) throws Exception {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than zero.");
        }

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM products ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offset); // Corrected: First parameter is the OFFSET
            stmt.setInt(2, pageSize); // Corrected: Second parameter is the FETCH size
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int categoryId = rs.getInt("c_id");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String origin = rs.getString("origin");
                String quality = rs.getString("quality");
                boolean isPassed = rs.getBoolean("isPassed");
                int quantity = rs.getInt("quantity");
                products.add(new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error fetching products", e);
        }
        return products;
    }

    public List<Product> getAllByCategoryId(int categoryId,int page, int pageSize) throws Exception {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than zero.");
        }

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM products WHERE c_id = ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId); // Corrected: First parameter is the OFFSET
            stmt.setInt(2, page);
            stmt.setInt(3, pageSize); // Corrected: Second parameter is the FETCH size
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String origin = rs.getString("origin");
                String quality = rs.getString("quality");
                boolean isPassed = rs.getBoolean("isPassed");
                int quantity = rs.getInt("quantity");
                products.add(new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error fetching products", e);
        }
        return products;
    }
    public int getTotalCount() throws Exception {
        int totalCount = 0;
        String sql = "SELECT COUNT(*) AS total FROM products";

        // Assuming getConnection() is a method that sets up and returns a connection to your database
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalCount = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions or errors as appropriate for your application
        }

        return totalCount;
    }

    public int getCountByCategoryId(int categoryId) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM products WHERE c_id = ?";

        // Assuming getConnection() is a method that sets up and returns a connection to your database
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions or errors as appropriate for your application
        }

        return count;
    }


    @Override
    public Product findById(int id) {

        String query = "select * from products where id = ?";
        try {

            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String name = rs.getString("name");
                String image = rs.getString("image");
                int categoryId = rs.getInt("c_id");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String origin = rs.getString("origin");
                String quality = rs.getString("quality");
                boolean isPassed = rs.getBoolean("isPassed");
                int quantity = rs.getInt("quantity");
                return (new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getAllByCategoryId(int cid) {
        List<Product> fruits = new ArrayList<>();
        CategoryDAO cdi = new CategoryDAO();
        String query = "select * from products where c_id = ?";
        try {

            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int categoryId = rs.getInt("c_id");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String origin = rs.getString("origin");
                String quality = rs.getString("quality");
                boolean isPassed = rs.getBoolean("isPassed");
                int quantity = rs.getInt("quantity");
                fruits.add(new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));
            }
            return fruits;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getAll() {
        List<Product> fruits = new ArrayList<>();
        CategoryDAO cdi = new CategoryDAO();
        String query = "select * from products";
        try {

            PreparedStatement ps = getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                int categoryId = rs.getInt("c_id");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String origin = rs.getString("origin");
                String quality = rs.getString("quality");
                boolean isPassed = rs.getBoolean("isPassed");
                int quantity = rs.getInt("quantity");
                fruits.add(new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));
            }
            return fruits;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean insert(Product product) {
        String query = "INSERT INTO products (name, image, c_id, price,description, origin, quality, isPassed, quantity,weight) VALUES (?, ?, ?,?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getImage());
            ps.setInt(3, product.getCid());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getDescription());
            ps.setString(6, product.getOrigin());
            ps.setString(7, product.getQuality());
            ps.setBoolean(8, product.isPassed());
            ps.setInt(9, product.getQuantity());
            ps.setDouble(10, product.getWeight());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    @Override
    public boolean update(Product product) {
        try {
            String sql = "UPDATE [Fruit_Shop].[dbo].[products] SET [c_id] = ?, [name] = ?, [image] = ?, [price] = ?, [weight] = ?, [origin] = ?, [quality] = ?, [isPassed] = ?, [description] = ?, [quantity] = ? WHERE [id] = ?";

            boolean rowUpdated;
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.setInt(1, product.getCid());
                statement.setString(2, product.getName());
                statement.setString(3, product.getImage());
                statement.setDouble(4, product.getPrice());
                statement.setDouble(5, product.getWeight());
                statement.setString(6, product.getOrigin());
                statement.setString(7, product.getQuality());
                statement.setBoolean(8, product.isPassed());
                statement.setString(9, product.getDescription());
                statement.setInt(10, product.getQuantity());
                statement.setInt(11, product.getId());
                rowUpdated = statement.executeUpdate() > 0;
                System.out.println("DAO" + product);
                System.out.println(statement.toString());
                System.out.println(rowUpdated);
            }

            return rowUpdated;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id
    ) {
        String query = "delete from products where id = " + id;
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalProductByCagegoryId(int id) {
        String query = "select count(*) from products where c_id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> listAllProducts(int page, int pageSize) throws SQLException, Exception {
        List<Product> listProduct = new ArrayList<>();
        String sql = "SELECT [id], [c_id], [name], [image], [price], [weight], [origin], [quality], [isPassed], [description], [quantity] FROM [Fruit_Shop].[dbo].[products] ORDER BY [id] OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, (page - 1) * pageSize);
        statement.setInt(2, pageSize);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String image = rs.getString("image");
            int categoryId = rs.getInt("c_id");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            String origin = rs.getString("origin");
            String quality = rs.getString("quality");
            boolean isPassed = rs.getBoolean("isPassed");
            int quantity = rs.getInt("quantity");
            listProduct.add(new Product(id, name, image, categoryId, price, description, price, origin, quality, isPassed, quantity));

        }

        rs.close();
        statement.close();

        return listProduct;

    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        System.out.println(productDAO.getAll());

    }
}
