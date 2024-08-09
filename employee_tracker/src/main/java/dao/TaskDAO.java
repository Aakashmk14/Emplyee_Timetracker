package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Task;

public class TaskDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/project2";
    private String jdbcUsername = "root";
    private String jdbcPassword = "321987";
    private static final String VIEW_TASKS = "SELECT * FROM task"; 
    private static final String INSERT_TASK_SQL = "INSERT INTO task (employee_name, project, date, start_time, end_time, category, description) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_TASK_SQL = "DELETE FROM task WHERE id = ?";
    private static final String SELECT_TASK_BY_ID_SQL =  "SELECT * FROM task WHERE id = ?";
    private static final String SELECT_TASKS_BY_EMPLOYEE_NAME_SQL = "SELECT * FROM task WHERE employee_name = ?";
    private static final String UPDATE_TASK_SQL = "UPDATE task SET date = ?, start_time = ?, end_time = ?, category = ?, description = ?, project = ? WHERE id = ?";
    private static final String SELECT_TASK_BY_DATE_AND_TIME_SQL = "SELECT id FROM task WHERE date = ? AND start_time = ? AND end_time = ? AND employee_name = ?;";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    
    public List<String[]> getDailyTasks(String employeeName) {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT date, project, COUNT(*) AS count FROM task WHERE employee_name = ? GROUP BY date, project";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = { rs.getString("date") + " (" + rs.getString("project") + ")", String.valueOf(rs.getInt("count")) };
                    data.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<String[]> getWeeklyTasks(String employeeName) {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT WEEK(date) AS week, project, COUNT(*) AS count FROM task WHERE employee_name = ? GROUP BY WEEK(date), project";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = { "Week " + rs.getInt("week") + " (" + rs.getString("project") + ")", String.valueOf(rs.getInt("count")) };
                    data.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<String[]> getMonthlyTasks(String employeeName) {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT MONTH(date) AS month, project, COUNT(*) AS count FROM task WHERE employee_name = ? GROUP BY MONTH(date), project";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = { "Month " + rs.getInt("month") + " (" + rs.getString("project") + ")", String.valueOf(rs.getInt("count")) };
                    data.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public int addTask(String employeeName, String project, LocalDate date, LocalTime startTime, LocalTime endTime, String category, String description) throws SQLException {
        String query = "INSERT INTO task (employee_name, project, date, start_time, end_time, category, description) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employeeName);
            statement.setString(2, project);
            statement.setDate(3, java.sql.Date.valueOf(date));
            statement.setTime(4, java.sql.Time.valueOf(startTime));
            statement.setTime(5, java.sql.Time.valueOf(endTime));
            statement.setString(6, category);
            statement.setString(7, description);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated task ID
                    }
                }
            }
            return -1; // Indicate failure
        }
    }
    public boolean isValidDuration(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        // Check if duration exceeds 8 hours
        return hours < 8 || (hours == 8 && minutes == 0);
    }

        
    private int calculateDurationmin(String startTime, String endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, timeFormatter);
        LocalTime end = LocalTime.parse(endTime, timeFormatter);
        return (int) Duration.between(start, end).toMinutes();
    }

    // Delete a task by ID
    public void deleteTask(int id) throws SQLException {
       try (Connection connection = getConnection();
             PreparedStatement st = connection.prepareStatement(DELETE_TASK_SQL)) {
             
            st.setInt(1, id);
            st.executeUpdate();
        }
    }
    
    // Get tasks by employee name
    public List<Task> getTasksByEmployeeName(String employeeName) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TASKS_BY_EMPLOYEE_NAME_SQL)) {

            statement.setString(1, employeeName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("employee_name"),
                        resultSet.getString("date"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getString("project")
                    );
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    // Get a task by ID
    public Task getTaskById(int id) throws SQLException {
        Task task = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement( SELECT_TASK_BY_ID_SQL)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("employee_name"),
                        resultSet.getString("date"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getString("project")
                    );
                }
            }
        }

        return task;
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
       
        try (Connection connection = getConnection(); // Assuming a Database class handles connection
             PreparedStatement preparedStatement = connection.prepareStatement(VIEW_TASKS );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String employeeName = resultSet.getString("employee_name");
                String date = resultSet.getString("date");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String category = resultSet.getString("category");
                String description = resultSet.getString("description");
                String project = resultSet.getString("project");

                Task task = new Task(id, employeeName, date, startTime, endTime, category, description, project);
                tasks.add(task);
            }
        }
        return tasks;
    } 
    public boolean isOverlappingTask(LocalDate date, LocalTime startTime, LocalTime endTime, int taskId) throws SQLException {
        String query = "SELECT * FROM task WHERE date = ? AND id <> ? AND ("
                + "(start_time < ? AND end_time > ?) OR "
                + "(start_time < ? AND end_time > ?) )";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2, taskId);
            statement.setTime(3, Time.valueOf(startTime));
            statement.setTime(4, Time.valueOf(startTime));
            statement.setTime(5, Time.valueOf(endTime));
            statement.setTime(6, Time.valueOf(endTime));

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    public boolean updateTask(int id, String project, LocalDate date, LocalTime startTime, LocalTime endTime,
            String category, String description) throws SQLException {
String query = "UPDATE task SET project = ?, date = ?, start_time = ?, end_time = ?, "
+ "category = ?, description = ? WHERE id = ?";

try (Connection connection = getConnection();
PreparedStatement statement = connection.prepareStatement(query)) {

statement.setString(1, project);
statement.setDate(2, Date.valueOf(date));
statement.setTime(3, Time.valueOf(startTime));
statement.setTime(4, Time.valueOf(endTime));
statement.setString(5, category);
statement.setString(6, description);
statement.setInt(7, id);

return statement.executeUpdate() > 0;}}
public Map<String, Map<String, Integer>> getProjectDurationsByEmployee() throws SQLException {
    String query = "SELECT employee_name, project, start_time, end_time FROM task";
    Map<String, Map<String, Integer>> projectDurations = new HashMap<>();

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String employeeName = rs.getString("employee_name");
            String project = rs.getString("project");
            LocalTime startTime = rs.getTime("start_time").toLocalTime();
            LocalTime endTime = rs.getTime("end_time").toLocalTime();

            int durationMinutes = (int) Duration.between(startTime, endTime).toMinutes();

            projectDurations.putIfAbsent(employeeName, new HashMap<>());
            Map<String, Integer> employeeProjects = projectDurations.get(employeeName);
            employeeProjects.put(project, employeeProjects.getOrDefault(project, 0) + durationMinutes);
        }
    }

    return projectDurations;
} public List<String> getAllEmployeeNames() {
    List<String> employeeNames = new ArrayList<>();
    String query = "SELECT DISTINCT employee_name FROM task"; // Adjust the table and column name as per your database

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            employeeNames.add(resultSet.getString("employee_name")); // Adjust the column name as per your database
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return employeeNames;
}
}


