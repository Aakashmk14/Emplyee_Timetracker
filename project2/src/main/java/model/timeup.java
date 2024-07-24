package model;
import java.time.LocalTime;
import java.time.Duration;

public class timeup {

    // Method to check if the duration between startTime and endTime does not exceed 8 hours
    public boolean isValidDuration(String startTime, String endTime) {
        try {
            // Parse the startTime and endTime strings to LocalTime objects
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            // Calculate the duration between start and end times
            Duration duration = Duration.between(start, end);

            // Convert duration to hours
            long hours = duration.toHours();

            // Check if the duration exceeds 8 hours
            return hours <= 8;
        } catch (Exception e) {
            // Handle parsing exceptions or other errors
            e.printStackTrace();
            return false; // Consider duration invalid in case of an error
        }
    }
}
