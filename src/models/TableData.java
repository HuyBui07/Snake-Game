package models;

public class TableData {

    private static String[][] players = new String[10][2];

    public static String[][] getPlayers() {

        int rowCount = 0;
        for (String[] player : players) {
            if (player[0] != null && player[1] != null) {
                rowCount++;
            }
        }

        String[][] data = new String[rowCount][3];

        // Fill the data array
        for (int i = 0; i < rowCount; i++) {
            String[] player = players[i];
            data[i][0] = String.valueOf(i + 1); // Rank
            data[i][1] = player[0]; // Name
            data[i][2] = player[1]; // Score
        }

        return data;
    }

    public static void setPlayers(String[][] players) {
        TableData.players = players;
    }

}
