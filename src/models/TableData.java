package models;

public class TableData {

    private static String[][] players = new String[10][2];

    public static String[][] getPlayers() {
        return players;
    }

    public static void setPlayers(String[][] players) {
        TableData.players = players;
    }

}
