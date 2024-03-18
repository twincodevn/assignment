/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.Order;
import model.User;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "ProcessCheckoutServlet", urlPatterns = {"/process-checkout"})
public class ProcessCheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("chackout.jsp");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");
        // Assume session contains the cart
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("userObj");
        
        Order order = new Order();
        order.setU_id(user.getId());
        order.setOrderDate(new Date());
        order.setTotalPrice(cart.getTotalCost());
        order.setOrderStatus("Processing");
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress);

        // Insert the order using OrderDAO and get the generated order ID
        OrderDAO orderDAO = new OrderDAO();
        int orderId = 0;
        try {
            orderId = orderDAO.insertOrder(order);
        } catch (Exception ex) {
            Logger.getLogger(ProcessCheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Insert each cart item as an order detail
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        for (CartItem item : cart.getItems()) {
            try {
                orderDetailDAO.insertOrderDetail(orderId, item);
            } catch (Exception ex) {
                Logger.getLogger(ProcessCheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        session.removeAttribute("cart");
        session.setAttribute("orderId", orderId);
        response.sendRedirect("orderConfirmation.jsp");
    }

}
