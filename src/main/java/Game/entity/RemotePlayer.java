
package Game.entity;

import Game.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author uriel
 */
public class RemotePlayer extends Entity {
    private GamePanel gP;

    public RemotePlayer(GamePanel gP) {
        this.gP = gP;
        this.worldX = gP.getSizeTile() * 38 - (gP.getSizeTile() / 2);
        this.worldY = gP.getSizeTile() * 23 - (gP.getSizeTile() / 2);
        this.speed = 4;
        this.direction = "down";
        getPlayerSprites();
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

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage sprite = null;
        switch (this.direction) {
            case "up":
                sprite = (this.numSprite == 1) ? up1 : up2;
                break;
            case "down":
                sprite = (this.numSprite == 1) ? down1 : down2;
                break;
            case "left":
                sprite = (this.numSprite == 1) ? left1 : left2;
                break;
            case "right":
                sprite = (this.numSprite == 1) ? right1 : right2;
                break;
        }
        g2.drawImage(sprite, this.screenX, this.screenY, gP.getSizeTile(), gP.getSizeTile(), null);
    }
}
