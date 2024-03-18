package controller;

import dao.UserDAO;
import java.io.IOException;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        User user = authenticateUser(email, password);
        if (user != null) {
            String remember = request.getParameter("remember");
            if (remember != null) {
                Cookie cRemember = new Cookie("cookrem", remember.trim());
                Cookie cEmail = new Cookie("cookuser", user.getEmail());
                Cookie cPassword = new Cookie("cookpass", password);
                cEmail.setMaxAge(60 * 60 * 24 * 15);// 15 days
                cPassword.setMaxAge(60 * 60 * 24 * 15);
                cRemember.setMaxAge(60 * 60 * 24 * 15);
                response.addCookie(cEmail);
                response.addCookie(cPassword);
                response.addCookie(cRemember);
            }
            session.setAttribute("userObj", user);
            if (user.isAdmin()) {
                response.sendRedirect("admin/products");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Username or password may be wrong !");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
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

    private User authenticateUser(String email, String password) {
        UserDAO userDAO = new UserDAO();
        User foundUser = userDAO.findByEmail(email);
        try {
            String passwordHashed = hashPassword(password);
            if (foundUser != null && foundUser.getPasswordHash().equals(passwordHashed)) {
                return foundUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
