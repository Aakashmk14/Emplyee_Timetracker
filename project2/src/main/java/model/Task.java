package model;
import java.io.Serializable;
public class Task {
    private int id;
    private String employeeName;
    private String date;
    private String startTime;
    private String endTime;
    private String category;
    private String description;
    private String project;

    // Constructor
    public Task(int id, String employeeName, String date, String startTime, String endTime, String category, String description, String project) {
        this.id = id;
        this.employeeName = employeeName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
        this.project = project;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getProject() {
        return project;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
