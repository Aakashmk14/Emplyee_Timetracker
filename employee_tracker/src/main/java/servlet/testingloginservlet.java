package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.userdao;
@WebServlet("/testingloginservlet")
public class testingloginservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        userdao userDAO = new userdao();
        String role = userDAO.validateUser(name, password);

        if (role != null) {
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("role", role);

            if (role.equals("Admin")) {
                response.sendRedirect("adminDashboard.jsp");
            } else if (role.equals("Associate")) {
                response.sendRedirect("associatedashboard.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
