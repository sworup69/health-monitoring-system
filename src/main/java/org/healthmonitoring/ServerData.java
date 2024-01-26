package org.healthmonitoring;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerData {
    private Connection connection;

    final String dbName = "health_monitoring";
    final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
    final String username = "root";
    final String password = "";

    public ServerData() {
        try {
            this.connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
        } catch (SQLException e) {
            System.out.println("Error while making database");
        }
    }

    void addHealthRecord(HealthRecord r) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO" +
                " records(userId, weight, exercise, timestamp) " +
                " VALUES(?, ?, ?, ?)"
        );
        stmt.setInt(1, r.userId);
        stmt.setDouble(2, r.weight);
        stmt.setString(3, r.exercise);
        stmt.setString(4, r.timestamp);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<HealthRecord> getHealthRecords() throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM records");
        List<HealthRecord> records = new ArrayList<>();
        while (rs.next()) {
            HealthRecord r = new HealthRecord(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getDouble("weight"),
                    rs.getString("exercise"),
                    rs.getString("timestamp")
            );
            records.add(r);
        }
        return records;
    }
}
