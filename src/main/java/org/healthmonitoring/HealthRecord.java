package org.healthmonitoring;

public class HealthRecord implements java.io.Serializable {
    int id;
    int userId;
    double weight;
    String exercise;
    String timestamp;

    public HealthRecord(int id, int userId, double weight, String exercise, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.exercise = exercise;
        this.timestamp = timestamp;
    }

    public HealthRecord(int userId, double weight, String exercise, String timestamp) {
        this.userId = userId;
        this.weight = weight;
        this.exercise = exercise;
        this.timestamp = timestamp;
    }
}
