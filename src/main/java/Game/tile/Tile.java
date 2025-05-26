package Game.tile;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private boolean collision = false;
    private boolean collisionDisparo = false;


    public BufferedImage getImage() {
        return this.image;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    public boolean getCollisionDisparo() {
        return this.collisionDisparo;
    }
    public void setCollisionDisparo(boolean collisionDisparo) {
        this.collisionDisparo = collisionDisparo;
    }
    
}
