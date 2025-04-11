package Game;

import Game.entity.Player;
import Game.tile.TilesHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    final int sizeTile = originalTileSize * scale;
    final int maxRenScreen = 15;
    final int maxColScreen = 26;
    final int widthScreen = sizeTile * maxColScreen;
    final int heightScreen = sizeTile * maxRenScreen;

    Thread gameThread;
    KeyHandler kH = new KeyHandler();
    Player player = new Player(this, kH);
    TilesHandler tH = new TilesHandler(this);
    int FPS = 60;

    private final int maxRenWorld = 45;
    private final int maxColWorld = 78;
    private final int widthWorld = this.sizeTile * this.maxColWorld;
    private final int heightWorld = this.sizeTile * this.maxRenWorld;

    public GamePanel() {
        this.setPreferredSize(new Dimension(this.widthScreen, this.heightScreen));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawingInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long actualTime;
        while (gameThread != null) {
            actualTime = System.nanoTime();
            delta += (actualTime - lastTime) / drawingInterval;
            lastTime = actualTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tH.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

    public int getSizeTile() {
        return this.sizeTile;
    }

    public int getMaxRenScreen() {
        return this.maxRenScreen;
    }

    public int getMaxColScreen() {
        return this.maxColScreen;
    }

    public int getWidthScreen() {
        return this.widthScreen;
    }

    public int getHeigthScreen() {
        return this.heightScreen;
    }

    public int getMaxRenWorld() {
        return this.maxRenWorld;
    }

    public int getMaxColWorld() {
        return this.maxColWorld;
    }

    public int getWidthWorld() {
        return this.widthWorld;
    }

    public int getHeightWorld() {
        return this.heightWorld;
    }

    public Player getPlayer() {
        return this.player;
    }

}
