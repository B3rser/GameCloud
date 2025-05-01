package com.mycompany.gamecloud;

import Game.GamePanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.json.JSONObject;

public class GameLauncher {

    public static void launchGame(JSONObject initialData) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        gamePanel.setGameWindow(window);

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                gamePanel.closeGameWindow();
            }
        });

        gamePanel.startGameThread();
    }
}
