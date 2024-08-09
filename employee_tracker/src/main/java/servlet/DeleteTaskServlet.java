package servlet;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.TaskDAO;  // Adjust import according to your project structure

@WebServlet("/DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            TaskDAO taskDAO = new TaskDAO();

            try {
                // Delete the task from the database
                taskDAO.deleteTask(id);
                // Redirect to the task list page or any other appropriate page
                response.sendRedirect("ViewTasksServlet");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exception (e.g., show an error message)
                request.setAttribute("errorMessage", "An error occurred while deleting the task.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            // Handle missing task ID (e.g., redirect to the task list page with an error message)
            request.setAttribute("errorMessage", "Task ID is missing.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
