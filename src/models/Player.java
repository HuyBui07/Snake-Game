package models;

public class Player {
    private static String name;
    private static String password;
    private static int highScore;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Player.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Player.password = password;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setHighScore(int highScore) {
        Player.highScore = highScore;
    }
}
