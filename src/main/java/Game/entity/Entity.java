package Game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int worldX, worldY;
    protected int screenX, screenY;
    protected int speed;
    protected BufferedImage up1, up2, down1, down2,
            left1, left2, right1, right2;
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

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }
    
    public abstract void draw(Graphics2D g2);
}
