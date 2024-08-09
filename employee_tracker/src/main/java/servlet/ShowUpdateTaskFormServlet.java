package servlet;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.TaskDAO;
import model.Task;

@WebServlet("/ShowUpdateTaskFormServlet")
public class ShowUpdateTaskFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        TaskDAO taskDAO = new TaskDAO();
        Task task = null;

        try {
            task = taskDAO.getTaskById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error (e.g., show an error message on the page)
        }

        request.setAttribute("task", task);
        request.getRequestDispatcher("/updateTaskForm.jsp").forward(request, response);
    }
}
