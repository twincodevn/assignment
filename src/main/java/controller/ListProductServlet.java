
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "ListProductServlet", urlPatterns = {"/admin/products"})
public class ListProductServlet extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> fruits = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        fruits = productDAO.getAll();
        
        request.setAttribute("fruits", fruits);
        System.out.println(fruits);
        RequestDispatcher rd = request.getRequestDispatcher("products.jsp");
        rd.forward(request, response);
        
    }

    
}
