package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.userdao;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        userdao userDAO = new userdao();
        boolean isRegistered = userDAO.registerUser(name, password, role);

        if (isRegistered) {
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("register.jsp");
        }
    }
}
