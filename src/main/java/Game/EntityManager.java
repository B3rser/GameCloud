package Game;

import Game.entity.Entity;
import Game.entity.RemotePlayer;
import com.mycompany.gamecloud.ConnectionManager;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author uriel
 */
public class EntityManager {
    
    private GamePanel gP;
    private ConnectionManager connectionManager;
    private Map<String, Entity> remoteEntities;
    
    public EntityManager(GamePanel gP) {
        this.gP = gP;
        this.connectionManager = ConnectionManager.getConnectionManagerInstance();
        this.remoteEntities = new HashMap<>();
    }
    
    public void update() {
        JSONObject message;
        while ((message = connectionManager.pollIncomingMessage()) != null) {
            processMessage(message);
        }
    }
    
    private void processMessage(JSONObject msg) {
        String command = msg.getString("command");
        String ID = msg.getString("username");
        int x = 0;
        int y = 0;
        int numLives = 0;
        String facing = "";
        if (command.equals("spawn") || command.equals("status")) {
            x = msg.getInt("x");
            y = msg.getInt("y");
            numLives = msg.getInt("y");
            facing = msg.getString("facing");
        }
        
        switch (command) {
            case "spawn":
                createRemotePlayer(ID, x, y, numLives, facing, command);
                sendStatus();
                break;
            case "status":
                createRemotePlayer(ID, x, y, numLives, facing, command);
                break;
            case "delete":
                deleteRemotePlayer(ID);
                break;
            case "up":
            case "down":
            case "left":
            case "right":
                moveRemotePlayer(ID, command);
                break;
        }
    }
    
    public void sendStatus() {
        gP.getPlayer().sendStatus(false);
    }
    
    public void createRemotePlayer(String id, int x, int y, int numLives, String facing, String command) {
        if (!remoteEntities.containsKey(id)) {
            RemotePlayer rp = new RemotePlayer(gP, x, y, numLives, facing);
            remoteEntities.put(id, rp);
        } else if (command.equals("status")) {
            RemotePlayer rp = (RemotePlayer) remoteEntities.get(id);
            rp.updateInfo(x, y, numLives, facing);
        }
    }
    
    public void deleteRemotePlayer(String id) {
        remoteEntities.remove(id);
    }
    
    public void moveRemotePlayer(String id, String direction) {
        Entity entity = remoteEntities.get(id);
        if (entity instanceof RemotePlayer) {
            RemotePlayer rp = (RemotePlayer) entity;
            rp.setDirection(direction);
            switch (direction) {
                case "up":
                    rp.setWorldY(rp.getWorldY() - rp.getSpeed());
                    break;
                case "down":
                    rp.setWorldY(rp.getWorldY() + rp.getSpeed());
                    break;
                case "left":
                    rp.setWorldX(rp.getWorldX() - rp.getSpeed());
                    break;
                case "right":
                    rp.setWorldX(rp.getWorldX() + rp.getSpeed());
                    break;
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for (Entity entity : remoteEntities.values()) {
            entity.setScreenX(entity.getWorldX() - gP.getPlayer().getWorldX() + gP.getPlayer().getScreenX());
            entity.setScreenY(entity.getWorldY() - gP.getPlayer().getWorldY() + gP.getPlayer().getScreenY());
            
            if (entity.getWorldX() + gP.getSizeTile() > gP.getPlayer().getWorldX() - gP.getPlayer().getScreenX()
                    && entity.getWorldX() - gP.getSizeTile() < gP.getPlayer().getWorldX() + gP.getPlayer().getScreenX()
                    && entity.getWorldY() + gP.getSizeTile() > gP.getPlayer().getWorldY() - gP.getPlayer().getScreenY()
                    && entity.getWorldY() - gP.getSizeTile() < gP.getPlayer().getWorldY() + gP.getPlayer().getScreenY()) {
                entity.draw(g2);
            }
        }
    }
}
