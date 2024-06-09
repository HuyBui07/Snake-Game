package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3307/mydatabase";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[][] getLeaderBoard(Connection connection) {
        
        // Get leader board data from database
        String query = "SELECT * FROM leaderboard ORDER BY score DESC LIMIT 10";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<String[]> results = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[2];
                row[0] = resultSet.getString("playerName");
                row[1] = resultSet.getString("score");
                results.add(row);
            }
            return results.toArray(new String[0][]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}