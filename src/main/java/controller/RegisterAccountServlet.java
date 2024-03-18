package controller;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/register"})
public class RegisterAccountServlet extends HttpServlet {

    public RegisterAccountServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String agreed = request.getParameter("agreed");
        boolean status = false;
        String errorMessage = "";
        String successMessage = "";
        if (agreed != null) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setEmail(email);
            newUser.setPasswordHash(password);
            UserDAO userDAO = new UserDAO();
            status = userDAO.insert(newUser);
            if (status) {
                successMessage = "Register successfully !";
            } else {
                errorMessage = "Existed data example email or phone ! Please try again !";
            }
        } else {
            errorMessage = "Please agree with us term";
        }
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("successMessage", successMessage);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

}
