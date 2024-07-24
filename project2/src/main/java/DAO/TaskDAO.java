package DAO;
import model.Task;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.temporal.WeekFields;
import java.time.temporal.TemporalAdjusters;
import java.time.format.DateTimeFormatter;
public class TaskDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/project2";
    private String jdbcUsername = "root";
    private String jdbcPassword = "321987";

    private static final String INSERT_TASK_SQL = "INSERT INTO task (employee_name, project, date, start_time, end_time, category, description) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_TASK_SQL = "DELETE FROM task WHERE id = ?;";
    private static final String SELECT_TASK_BY_ID_SQL = "SELECT * FROM task WHERE id = ?;";
    private static final String SELECT_TASKS_BY_EMPLOYEE_NAME_SQL = "SELECT * FROM task WHERE employee_name = ?;";
    private static final String UPDATE_TASK_SQL = "UPDATE task SET project = ?, date = ?, start_time = ?, end_time = ?, category = ?, description = ? WHERE id = ?;";
    private static final String SELECT_TASK_BY_DATE_AND_TIME_SQL = "SELECT id FROM task WHERE date = ? AND start_time = ? AND end_time = ? AND employee_name = ?;";

    public TaskDAO() {
    }

    // Establish a connection to the database
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Add a new task
    public int addTask(String employeeName, String project, LocalDate date, LocalTime startTime, LocalTime endTime, String category, String description) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employeeName);
            preparedStatement.setString(2, project);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));
            preparedStatement.setTime(4, java.sql.Time.valueOf(startTime));
            preparedStatement.setTime(5, java.sql.Time.valueOf(endTime));
            preparedStatement.setString(6, category);
            preparedStatement.setString(7, description);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated task ID
                    }
                }
            }
        }
        return -1; // Return -1 if task ID generation fails
    }

    public Duration calculateduration(String startTimeStr, String endTimeStr) {
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);
        return Duration.between(startTime, endTime);
    }

    // Delete a task by ID
    public void deleteTask(int id) throws SQLException {
        String query = "DELETE FROM task WHERE id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    // Check for duplicate task entry
    // Check for duplicate entries
    public boolean isDuplicateTask(LocalDate date, LocalTime startTime, LocalTime endTime, String employeeName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_DATE_AND_TIME_SQL)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            preparedStatement.setTime(2, java.sql.Time.valueOf(startTime));
            preparedStatement.setTime(3, java.sql.Time.valueOf(endTime));
            preparedStatement.setString(4, employeeName);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if there is a record with the same date and time
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get tasks by employee name
    public List<Task> getTasksByEmployeeName(String employeeName) throws SQLException {
        String query = "SELECT * FROM task WHERE employee_name = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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
        String query = "SELECT * FROM task WHERE id = ?";
        Task task = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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

    public void updateTask(Task task) throws SQLException {
        String query = "UPDATE task SET date = ?, start_time = ?, end_time = ?, category = ?, description = ?, project = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, java.sql.Date.valueOf(task.getDate()));
            statement.setTime(2, java.sql.Time.valueOf(task.getStartTime()));
            statement.setTime(3, java.sql.Time.valueOf(task.getEndTime()));
            statement.setString(4, task.getCategory());
            statement.setString(5, task.getDescription());
            statement.setString(6, task.getProject());
            statement.setInt(7, task.getId());
            statement.executeUpdate();
        }
    }
    public boolean isUniqueEntry(String date, String startTime, String endTime, int taskId) throws SQLException {
        String query = "SELECT COUNT(*) FROM task WHERE date = ? AND start_time = ? AND end_time = ? AND id <> ?";
     try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, date);
            statement.setString(2, startTime);
            statement.setString(3, endTime);
            statement.setInt(4, taskId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0; // True if no duplicates found
                }
            }
        }
        return false;
    }
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task"; // Adjust the table name if needed

        try (Connection connection = getConnection(); // Assuming a Database class handles connection
             PreparedStatement preparedStatement = connection.prepareStatement(query);
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
    public Map<String, Integer> getProjectStats(List<Task> tasks) {
        Map<String, Integer> projectStats = new HashMap<>();

        for (Task task : tasks) {
            String project = task.getProject();
            int minutes = calculateMinutes(task.getStartTime(), task.getEndTime());

            // Add minutes to the project's total in the map
            projectStats.put(project, projectStats.getOrDefault(project, 0) + minutes);
        }

        return projectStats;
    }
    public Map<String, Integer> calculateTaskDuration(List<Task> tasks, String period) {
        Map<String, Integer> stats = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Task task : tasks) {
            LocalDate date = LocalDate.parse(task.getDate(), formatter);
            String key;
            switch (period) {
                case "daily":
                    key = date.toString();
                    break;
                case "weekly":
                    key = date.with(WeekFields.ISO.getFirstDayOfWeek()).toString(); // Start of the week
                    break;
                case "monthly":
                    key = date.with(TemporalAdjusters.firstDayOfMonth()).toString();
                    break;
                default:
                    key = date.toString();
                    break;
            }
            int duration = calculateDuration(task.getStartTime(), task.getEndTime());
            stats.put(key, stats.getOrDefault(key, 0) + duration);
        }
        return stats;
    }

    private int calculateDuration(String startTime, String endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, timeFormatter);
        LocalTime end = LocalTime.parse(endTime, timeFormatter);
        return (int) Duration.between(start, end).toMinutes();
    }

    // Utility method to calculate minutes from start and end time
    private int calculateMinutes(String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1]);
        int endHour = Integer.parseInt(endTime.split(":")[0]);
        int endMinute = Integer.parseInt(endTime.split(":")[1]);

        int startTotalMinutes = startHour * 60 + startMinute;
        int endTotalMinutes = endHour * 60 + endMinute;

        return endTotalMinutes - startTotalMinutes;
    }

}





