package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Player;
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

    public static boolean authenticateUser(String playerName, String password) {

        Connection connection = null;
        String query = "SELECT * FROM users WHERE playerName = ? AND password = ?";

        // Authenticate user
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Player.setId(resultSet.getInt("id"));
                Player.setName(resultSet.getString("playerName"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return false;

    }

    public static boolean checkPlayerName(String playerName) {

        Connection connection = null;
        String query = "SELECT * FROM users WHERE playerName = ?";

        // Check if player name exists
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return false;
    }

    public static void addPlayer(String playerName, String password) {

        Connection connection = null;
        try {
            connection = getConnection();

            // Insert new row
            String query = "INSERT INTO users (playerName, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerName);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public static int getPlayerId(String playerName) {

        Connection connection = null;
        String query = "SELECT id FROM users WHERE playerName = ?";

        // Get player id from database
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return 0;
    }

    public static void getLeaderBoard() {

        Connection connection = null;
        String query = "SELECT users.playerName, leaderboard.score FROM users JOIN leaderboard ON users.id = leaderboard.userId ORDER BY leaderboard.score DESC LIMIT 10;";

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

    public static int getHighScore(int id) {

        Connection connection = null;
        String query = "SELECT MAX(score) as maxScore FROM leaderboard WHERE UserId = '" + id + "'";

        // Get high score from database
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("maxScore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return 0;
    }

    public static void setScore(int UserId, int score) {

        Connection connection = null;
        try {
            connection = getConnection();

            // Insert new row
            String query = "INSERT INTO leaderboard (UserId, score) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, UserId);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}