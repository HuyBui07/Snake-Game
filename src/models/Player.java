package models;

public class Player {
    private static int id;
    private static String name;
    private static int highScore;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Player.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Player.name = name;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setHighScore(int highScore) {
        Player.highScore = highScore;
    }
}
