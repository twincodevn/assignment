package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "AddProductServlet", urlPatterns = {"/admin/add-product"})
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra nếu request có multipart content
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }

        // Cấu hình cho việc upload
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // Parse các phần của request để lấy file upload và các dữ liệu form khác
            List<FileItem> formItems = upload.parseRequest(request);

            String name = null;
            String description = null;
            double price = 0;
            int categoryId = 0;
            String imagePath = null;
            double weight = 0.0;
            String origin = null;
            String quality = null;
            int quantity = 0;
            boolean test = false;

            for (FileItem item : formItems) {
                if (item.isFormField()) {
                    // Xử lý các trường form thông thường
                    switch (item.getFieldName()) {
                        case "name":
                            name = item.getString();
                            break;
                        case "description":
                            description = item.getString();
                            break;
                        case "price":
                            price = Double.parseDouble(item.getString());
                            break;
                        case "category":
                            categoryId = Integer.parseInt(item.getString());
                            break;
                        case "weight":
                            weight = Double.parseDouble(item.getString());
                            break;
                        case "origin":
                            origin = item.getString();
                            break;
                        case "quality":
                            quality = item.getString();
                            break;
                        case "quantity":
                            quantity = Integer.parseInt(item.getString());
                            break;
                        case "test":
                            test = Boolean.parseBoolean(item.getString());
                            break;
                    }

                } else {
                    // Process only if the file extension is valid (simple example)
                    if (item.getName().endsWith(".jpg") || item.getName().endsWith(".png")) {
                        String fileName = new File(item.getName()).getName();
                        String savePath = "img"; // Use a relative path for storage
                        File uploadDir = new File(request.getServletContext().getRealPath("") + File.separator + savePath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir(); // Ensure directory exists
                        }
                        String filePath = savePath + File.separator + fileName;
                        File storeFile = new File(uploadDir + File.separator + fileName);
                        item.write(storeFile);
                        imagePath = filePath; // Save relative path
                    } else {
                        request.setAttribute("errorMessage", "Invalid file type for image ensure is jpg or png.");
                        doGet(request, response);
                        return;
                    }
                }
            }

            // Thực hiện validate dữ liệu (Giả sử validate đơn giản)
            // After form processing, validate and construct the Product object
            if (name == null || name.isEmpty() || description == null || description.isEmpty() || price <= 0 || categoryId <= 0) {
                request.setAttribute("errorMessage", "Product data validation failed.");
                doGet(request, response);
                return;
            }

            // Lưu sản phẩm vào DB
            Product product = new Product(name, imagePath, categoryId, price, description, weight, origin, quality, test, quantity);
            ProductDAO productDAO = new ProductDAO();
            boolean insertResult = productDAO.insert(product);
            if (insertResult) {
                String msg = "Add product successfully !";
                request.setAttribute("msg", msg);

                doGet(request, response);
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Lỗi trong quá trình upload file: " + ex.getMessage());
            request.getRequestDispatcher("add-product.jsp").forward(request, response);
        }
    }

}
