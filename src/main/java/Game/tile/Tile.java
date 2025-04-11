package Game.tile;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private boolean collision = false;

    public BufferedImage getImage() {
        return this.image;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
