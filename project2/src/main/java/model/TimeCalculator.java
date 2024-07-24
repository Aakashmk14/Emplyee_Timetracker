package model;
import java.time.LocalTime;
import java.time.Duration;

public class TimeCalculator {

    // Method to calculate the duration between two LocalTime instances
    public static long calculateHoursBetween(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours();
    }

    // Method to check if the duration is valid (not more than 8 hours)
    public static boolean isValidDuration(LocalTime startTime, LocalTime endTime) {
        long hours = calculateHoursBetween(startTime, endTime);
        return hours <= 8;
    }

    public static void main(String[] args) {
        // Example usage
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endTime = LocalTime.of(15, 30); // 3:30 PM

        System.out.printf("Duration: %d hours%n", calculateHoursBetween(startTime, endTime));
        System.out.println("Is valid duration: " + isValidDuration(startTime, endTime));
    }
}
