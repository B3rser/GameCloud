package com.mycompany.gamecloud;

import Game.GamePanel;
import javax.swing.JFrame;
import org.json.JSONObject;

public class GameLauncher {

    public static void launchGame(JSONObject initialData) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");

        GamePanel gamePanel = new GamePanel(
                initialData.getInt("mapWidth"),
                initialData.getInt("mapHeight"),
                initialData.getInt("screenWidth"),
                initialData.getInt("screenHeight"),
                initialData.getInt("playerSpeed"),
                initialData.getInt("projectileSpeed"),
                initialData.getString("map"),
                initialData.getInt("spawnX"),
                initialData.getInt("spawnY")
        );
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
