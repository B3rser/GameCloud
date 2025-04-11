package Game.entity;

import java.awt.image.BufferedImage;

public class Entity {

    protected int worldX, worldY;
    protected int speed;
    protected BufferedImage up1, up2, down1, down2,
            left1, left2, right1,
            right2;
    protected String direction;
    protected int spriteCount = 0;
    protected int numSprite = 1;
    protected int changeSprite = 15;

    public int getSpeed() {
        return this.speed;
    }

    public void setWorldX(int valor) {
        this.worldX = valor;
    }

    public void setWorldY(int valor) {
        this.worldY = valor;
    }

    public int getWorldX() {
        return this.worldX;
    }

    public int getWorldY() {
        return this.worldY;
    }
}
