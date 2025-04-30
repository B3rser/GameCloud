package Game;

import Game.entity.Entity;
import Game.entity.Player;
import Game.tile.TilesHandler;
import com.mycompany.gamecloud.ConnectionManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int sizeTile = originalTileSize * scale;
    private final int maxRenScreen = 15;
    private final int maxColScreen = 26;

    private int widthScreen;
    private int heightScreen;

    private int maxRenWorld;
    private int maxColWorld;
    private int widthWorld;
    private int heightWorld;

    private int FPS = 60;

    private Thread gameThread;

    private KeyHandler kH;
    private Player player;
    private EntityManager eM;
    private TilesHandler tH;

    private ConnectionManager connectionManager;

    private Entity[] obj;

    public GamePanel() {
        this.maxColWorld = 78;
        this.maxRenWorld = 45;

        this.widthScreen = sizeTile * maxColScreen;
        this.heightScreen = sizeTile * maxRenScreen;
        this.widthWorld = sizeTile * maxColWorld;
        this.heightWorld = sizeTile * maxRenWorld;

        this.kH = new KeyHandler();
        this.player = new Player(this, kH);
        this.eM = new EntityManager(this);
        this.tH = new TilesHandler(this);
        this.connectionManager = ConnectionManager.getConnectionManagerInstance();

        this.tH = new TilesHandler(this);
        this.connectionManager = ConnectionManager.getConnectionManagerInstance();

        this.setPreferredSize(new Dimension(this.widthScreen, this.heightScreen));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public GamePanel(
            int maxColWorld,
            int maxRenWorld,
            int widthScreen,
            int heightScreen,
            int playerSpeed,
            int projectileSpeed,
            String map,
            int spawnX,
            int spawnY
    ) {
        this.maxColWorld = maxColWorld;
        this.maxRenWorld = maxRenWorld;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.widthWorld = sizeTile * maxColWorld;
        this.heightWorld = sizeTile * maxRenWorld;

        this.kH = new KeyHandler();
        this.player = new Player(this, kH, playerSpeed, spawnX, spawnY);
        this.eM = new EntityManager(this);
        this.tH = new TilesHandler(this);
        this.connectionManager = ConnectionManager.getConnectionManagerInstance();

        this.setPreferredSize(new Dimension(this.widthScreen, this.heightScreen));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
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
        eM.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tH.draw(g2);
        player.draw(g2);
        eM.draw(g2);
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

    public TilesHandler getTilesHandler() {
        return this.tH;
    }

    public Entity[] getEntities() {
        return this.obj;
    }

}
