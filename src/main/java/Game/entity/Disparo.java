package Game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Game.GamePanel;
import Game.tile.TilesHandler;
import Game.tile.Tile;


public class Disparo extends Entity {
    private int x, y;
    private BufferedImage imagen;
    private boolean activo = true; 
    
    public Disparo(int x, int y, String direccion, BufferedImage imagen) {
        this.x = x;
        this.y = y;
        this.direction = direccion;
        this.imagen = imagen;
        this.speed = 1;
    }


    public void update(){
        switch(direction) {
            case "up":
                y -= speed;
                break;
            case "down":
                y += speed;
                break;
            case "left":
                x -= speed;
                break;
            case "right":
                x += speed;
                break;
        }
    }
    
    public void draw(Graphics2D g2, int pantallaX, int pantallaY, int mundoX, int mundoY) {
        int screenX = x-(worldX-pantallaX);
        int screenY = y-(worldY-pantallaY);
        g2.drawImage(imagen,screenX, screenY, null);
        // Implement drawing logic here, for example:
        // g2.drawImage(imagen, screenX, y - worldY, null);
    }

    public boolean disparoIsCollisioning(int nuevaX, int nuevaY, TilesHandler mTi, int tamanioTile) {
        int izquierdaX = nuevaX;
        int derechaX = nuevaX + tamanioTile - 1;
        int superiorY = nuevaY;
        int inferiorY = nuevaY + tamanioTile - 1;
    
        int colIzquierda = izquierdaX / tamanioTile;
        int colDerecha = derechaX / tamanioTile;
        int renSuperior = superiorY / tamanioTile;
        int renInferior = inferiorY / tamanioTile;
    
        return mTi.getArrayTiles()[mTi.getCodigoMapaTiles(renSuperior, colIzquierda)].getCollisionDisparo() ||
               mTi.getArrayTiles()[mTi.getCodigoMapaTiles(renSuperior, colDerecha)].getCollisionDisparo() ||
               mTi.getArrayTiles()[mTi.getCodigoMapaTiles(renInferior, colIzquierda)].getCollisionDisparo() ||
               mTi.getArrayTiles()[mTi.getCodigoMapaTiles(renInferior, colDerecha)].getCollisionDisparo();
    }
    

    public int getX() { return x; }
    public int getY() { return y; }
}
