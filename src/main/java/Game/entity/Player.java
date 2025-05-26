package Game.entity;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import com.mycompany.gamecloud.ConnectionManager;

import Game.GamePanel;
import Game.KeyHandler;

public class Player extends Entity {

    private GamePanel gP;
    private KeyHandler kH;
    private ConnectionManager connectionManager;

    public BufferedImage imageShoot;
    public List <Disparo> disparos;
    private long timeLastShoot=0;
    public final long timeBetweenShoots=400;
    // Vidas jugador
    public int numLives = 3; // Número de vidas del jugador
    private BufferedImage heart1, heart2, heart3, heart0;
    public boolean isAlive = true;

    public Player(GamePanel gP, KeyHandler kH) {
        this.gP = gP;
        this.kH = kH;
        this.screenX = gP.getWidthScreen() / 2 - (gP.getSizeTile() / 2);
        this.screenY = gP.getHeigthScreen() / 2 - (gP.getSizeTile() / 2);
        connectionManager = ConnectionManager.getConnectionManagerInstance();
        initialConfiguration();
        getPlayerSprites();
        this.disparos = new ArrayList<>();
        sendStatus(true);
    }

    public Player(GamePanel gP, KeyHandler kH, int speed, int spawnX, int spawnY) {
        this.gP = gP;
        this.kH = kH;
        this.screenX = gP.getWidthScreen() / 2 - (gP.getSizeTile() / 2);
        this.screenY = gP.getHeigthScreen() / 2 - (gP.getSizeTile() / 2);
        connectionManager = ConnectionManager.getConnectionManagerInstance();
        initialConfiguration(speed, spawnX, spawnY);
        getPlayerSprites();
        loadImageLifes();
        sendStatus(true);
    }
    private void loadImageLifes() {
        try {
            this.heart1 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/corazon1.png"));
            this.heart2 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/corazon2.png"));
            this.heart3 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/corazon3.png"));
            this.heart0 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/corazon0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialConfiguration() {
        this.worldX = gP.getSizeTile() * 38 - (gP.getSizeTile() / 2);
        this.worldY = gP.getSizeTile() * 23 - (gP.getSizeTile() / 2);
        this.speed = 4;
        this.direction = "down";
    }

    private void initialConfiguration(int speed, int spawnX, int spawnY) {
        this.worldX = spawnX;
        this.worldY = spawnY;
        this.speed = speed;
        this.direction = "down";
    }

    private void getPlayerSprites() {
        try {
            this.up1 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverArriba1.png"));
            this.up2 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverArriba2.png"));
            this.down1 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverAbajo1.png"));
            this.down2 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverAbajo2.png"));
            this.left1 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverIzquierda1.png"));
            this.left2 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverIzquierda2.png"));
            this.right1 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverDerecha1.png"));
            this.right2 = ImageIO.read(getClass().getResourceAsStream("/assets/playerSprites/moverDerecha2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(isAlive){
            return;
        }
        if (kH.getUpKey() == true || kH.getDownKey() == true
                || kH.getLeftKey() == true || kH.getRigthKey() == true) {
            this.sendMessageMove();
            this.spriteCount++;
        }

        if (kH.getShoot() == true) {
            this.shoot();
            return;
        }
        actualShoot(); // actualiza los disparos 
        for(Disparo disparo : disparos){
            if(colisionaConDisparo(disparo)){
                this.numLives--;
                disparos.remove(disparo);
                break;
            }
        }

        if (kH.getExit() == true) {
            gP.closeGameWindow();
            return;
        }

        if (kH.getUpKey() == true) {
            setWorldY(getWorldY() - getSpeed());
            this.direction = "up";
        } else if (kH.getDownKey() == true) {
            setWorldY(getWorldY() + getSpeed());
            this.direction = "down";
        } else if (kH.getLeftKey() == true) {
            setWorldX(getWorldX() - getSpeed());
            this.direction = "left";
        } else if (kH.getRigthKey() == true) {
            setWorldX(getWorldX() + getSpeed());
            this.direction = "right";
        }
        if (this.spriteCount > this.changeSprite) {
            if (this.numSprite == 1) {
                this.numSprite = 2;
            } else {
                this.numSprite = 1;
            }
            this.spriteCount = 0;
        }

    }
        private boolean colisionaConDisparo(Disparo disparo) {
        // Verificar si las coordenadas del disparo coinciden con las del jugador
        Rectangle jugadorHitbox = new Rectangle(worldX, worldY, gP.getSizeTile(), gP.getSizeTile());
        Rectangle disparoHitbox = new Rectangle(disparo.getX(), disparo.getY());

        return jugadorHitbox.intersects(disparoHitbox);
    }

    private void shoot() {
        long time =System.currentTimeMillis();
        int posX = this.worldX ;
        int posY = this.worldY ;

        switch (direction) {
            case "up":
                posY -= gP.getSizeTile();
                break;
            case "down":
                posY += gP.getSizeTile();
                break;
            case "left":
                posX -= gP.getSizeTile();
                break;
            case "rigt":
                posX += gP.getSizeTile();
                break;
        }
        if(time - timeLastShoot >= timeBetweenShoots){
            loadImageShoot();
            Disparo disparo = new Disparo(posX, posY, this.direction, imageShoot);
            disparos.add(disparo);
            timeLastShoot = time;
            return;
        }
        System.out.println("Shoot");
    }

    private void loadImageShoot() {
     if (direction == "up") {
            try {
                imageShoot = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/disparoArriba.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (direction == "down") {
            try {
                imageShoot = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/disparoAbajo.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (direction == "left") {
            try {
                imageShoot = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/disparoIzquierda.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (direction == "right") {
            try {
                imageShoot = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/disparoDerecha.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*private void sendMessageMove() {
        JSONObject message = new JSONObject();
        message.put("command", "move");
        message.put("direction", this.direction);

        connectionManager.queueMessage(message);
    }*/
    private void sendMessageMove() {
        JSONObject message = new JSONObject();
        message.put("command", this.direction); // "up", "down", "left" o "right"
        connectionManager.queueMessage(message);
    }
    public void actualShoot(){
                // Actualizar cada disparo y eliminar los que están fuera de los límites del
        // mundo
        disparos.removeIf(disparo-> {
                        // Eliminar disparo si sale de los límites del mundo, no de la pantalla

            disparo.update();
            return disparo.getX() < 0 || disparo.getX() > gP.getWidthScreen() || disparo.getY() < 0 || disparo.getY() > gP.getHeigthScreen();
        });
    }
    

    public void sendStatus(Boolean isSpawn) {
        JSONObject message = new JSONObject();
        message.put("command", isSpawn ? "spawn" : "status");
        message.put("x", this.worldX);
        message.put("y", this.worldY);
        message.put("numLives", this.numLives);
        message.put("facing", this.direction);

        connectionManager.queueMessage(message);
    }
    public void restLife(){
        if(numLives > 0){
            numLives--;
        }
        if(numLives <= 0){
            isAlive = false;
            sendStatus(false);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage sprite = null;
        switch (this.direction) {
            case "up":
                if (this.numSprite == 1) {
                    sprite = this.up1;
                }
                if (this.numSprite == 2) {
                    sprite = this.up2;
                }
                break;
            case "down":
                if (this.numSprite == 1) {
                    sprite = this.down1;
                }
                if (this.numSprite == 2) {
                    sprite = this.down2;
                }
                break;
            case "left":
                if (this.numSprite == 1) {
                    sprite = this.left1;
                }
                if (this.numSprite == 2) {
                    sprite = this.left2;
                }
                break;
            case "right":
                if (this.numSprite == 1) {
                    sprite = this.right1;
                }
                if (this.numSprite == 2) {
                    sprite = this.right2;
                }
                break;
        }
        g2.drawImage(sprite, this.screenX, this.screenY, gP.getSizeTile(),
                gP.getSizeTile(), null);
    }
}
