package servlet;

import dao.TaskDAO; // Ensure you have a DAO to fetch employee details
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectEmployeeServlet")
public class SelectEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	TaskDAO taskDAO = new TaskDAO();
        List<String> employees = taskDAO.getAllEmployeeNames(); // Ensure this method exists in your DAO

        request.setAttribute("employees", employees);
        request.getRequestDispatcher("selectEmployee.jsp").forward(request, response);
    }
}
