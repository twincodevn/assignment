package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

@WebServlet(name = "ProductServlet", urlPatterns = {"/shop"})
public class ShopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cid = request.getParameter("cid");
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int pageSize = 5; 

        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        List<Category> categories = categoryDAO.getAll();
        List<Product> fruits = new ArrayList<>();
        int totalProducts = 0;

        if (cid != null) {
            int cidNum = Integer.parseInt(cid);
            try {
                fruits = productDAO.getAllByCategoryId(cidNum, page, pageSize);
            } catch (Exception ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                totalProducts = productDAO.getCountByCategoryId(cidNum);
            } catch (Exception ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                fruits = productDAO.getAll(page, pageSize);
            } catch (Exception ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                totalProducts = productDAO.getTotalCount();
            } catch (Exception ex) {
                Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("fruits", fruits);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
