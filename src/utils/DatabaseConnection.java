package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.TableData;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3307/snake";
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

    public static void getLeaderBoard() {

        Connection connection = null;
        String query = "SELECT * FROM leaderboard ORDER BY score DESC LIMIT 10";

        // Get leader board data from database
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<String[]> results = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[2];
                row[0] = resultSet.getString("playerName");
                row[1] = resultSet.getString("score");
                results.add(row);
            }
            TableData.setPlayers(results.toArray(new String[0][0]));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

    }

    public static int getHighScore(String name) {

        Connection connection = null;
        String query = "SELECT score FROM leaderboard WHERE playerName = '" + name + "'";

        // Get high score from database
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return 0;
    }

    public static void setScore(String playerName, int score) {

        Connection connection = null;
        try {
            connection = getConnection();

            // Insert new row
            String query = "INSERT INTO leaderboard (playerName, score) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}