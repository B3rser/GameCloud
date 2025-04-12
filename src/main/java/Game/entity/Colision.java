/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.entity;
import Game.GamePanel;
public class Colision {
    public GamePanel gP;

    public Colision(GamePanel gP) {
        this.gP = gP;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.detectColision.x;
        int entityRightWorldX = entity.worldX + entity.detectColision.width + entity.detectColision.x;
        int entityTopWorldY = entity.worldY + entity.detectColision.y;
        int entityBottomWorldY = entity.worldY + entity.detectColision.height + entity.detectColision.y;

       int entityLeftCol = entityLeftWorldX / gP.getSizeTile();
       int entityRightCol = entityRightWorldX / gP.getSizeTile();
       int entityTopRow = entityTopWorldY / gP.getSizeTile();
       int entityBottomRow = entityBottomWorldY / gP.getSizeTile();

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "arriba":
                entityTopRow = (entityTopWorldY - entity.speed) / gP.getSizeTile();
                tileNum1 = gP.getTilesHandler().tilesMapCodes[entityLeftCol][entityTopRow];
                tileNum2 = gP.getTilesHandler().tilesMapCodes[entityRightCol][entityTopRow];
                if (gP.getTilesHandler().tilesArray[tileNum1].getCollision() || gP.getTilesHandler().tilesArray[tileNum2].getCollision() ){
                    entity.colisionOn = true;
                }
                break;
            case "abajo":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gP.getSizeTile();
                tileNum1 = gP.getTilesHandler().tilesMapCodes[entityLeftCol][entityBottomRow];
                tileNum2 = gP.getTilesHandler().tilesMapCodes[entityRightCol][entityBottomRow];
                if (gP.getTilesHandler().tilesArray[tileNum1].getCollision() || gP.getTilesHandler().tilesArray[tileNum2].getCollision()) {
                    entity.colisionOn = true;
                }
                break;
            case "izquierda":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gP.getSizeTile();
                tileNum1 = gP.getTilesHandler().tilesMapCodes[entityLeftCol][entityTopRow];
                tileNum2 = gP.getTilesHandler().tilesMapCodes[entityLeftCol][entityBottomRow];
                if (gP.getTilesHandler().tilesArray[tileNum1].getCollision() || gP.getTilesHandler().tilesArray[tileNum2].getCollision()) {
                    entity.colisionOn = true;
                }
                break;
            case "derecha":
                entityRightCol = (entityRightWorldX + entity.speed) / gP.getSizeTile();
                tileNum1 = gP.getTilesHandler().tilesMapCodes[entityRightCol][entityTopRow];
                tileNum2 = gP.getTilesHandler().tilesMapCodes[entityRightCol][entityBottomRow];
                if (gP.getTilesHandler().tilesArray[tileNum1].getCollision() || gP.getTilesHandler().tilesArray[tileNum2].getCollision()) {
                    entity.colisionOn = true;
                }   
                break;
        }
    }

    public int checkColisionObj(Entity ent, boolean play){
        int index = 999;
        for (int i = 0; i < gP.getEntities().length; i++) {
            if (gP.getEntities()[i] != null) {
                ent.detectColision.x = ent.worldX + ent.detectColision.x;
                ent.detectColision.y = ent.worldY + ent.detectColision.y;
               gP.getEntities()[i].detectColision.x = gP.getEntities()[i].worldX + gP.getEntities()[i].detectColision.x;
                gP.getEntities()[i].detectColision.y = gP.getEntities()[i].worldY + gP.getEntities()[i].detectColision.y;

                switch(ent.direction) {
                    case "arriba": ent.detectColision.y -= ent.speed; break;
                    case "abajo": ent.detectColision.y += ent.speed; break;
                    case "izquierda": ent.detectColision.x -= ent.speed; break;
                    case "derecha": ent.detectColision.x += ent.speed; break;
                }

                if (ent.detectColision.intersects(gP.getEntities()[i].detectColision)) {
                    if (gP.getEntities()[i].colisionOn) {
                        ent.colisionOn = true;
                    }
                    if (play) {
                        index = i;
                    }
                }

                ent.detectColision.x = ent.detectColisionDefaultX;
                ent.detectColision.y = ent.detectColisionDefaultY;
                gP.getEntities()[i].detectColision.x = gP.getEntities()[i].detectColisionDefaultX;
                gP.getEntities()[i].detectColision.y = gP.getEntities()[i].detectColisionDefaultY;
            }
        }
        return index;
    }

    public int checkEntity(Entity ent, Entity[] trg){
        int index = 999;
        for (int i = 0; i < trg.length; i++) {
            if (trg[i] != null) {
                ent.detectColision.x = ent.worldX + ent.detectColision.x;
                ent.detectColision.y = ent.worldY + ent.detectColision.y;
                trg[i].detectColision.x = trg[i].worldX + trg[i].detectColision.x;
                trg[i].detectColision.y = trg[i].worldY + trg[i].detectColision.y;

                switch (ent.direction) {
                    case "arriba": ent.detectColision.y -= ent.speed; break;
                    case "abajo": ent.detectColision.y += ent.speed; break;
                    case "izquierda": ent.detectColision.x -= ent.speed; break;
                    case "derecha": ent.detectColision.x += ent.speed; break;
                }

                if (ent.detectColision.intersects(trg[i].detectColision)) {
                    if (trg[i] != ent) {
                        ent.colisionOn = true;
                        index = i;
                    }
                }

                ent.detectColision.x = ent.detectColisionDefaultX;
                ent.detectColision.y = ent.detectColisionDefaultY;
                trg[i].detectColision.x = trg[i].detectColisionDefaultX;
                trg[i].detectColision.y = trg[i].detectColisionDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity ent){
        ent.detectColision.x = ent.worldX + ent.detectColision.x;
        ent.detectColision.y = ent.worldY + ent.detectColision.y;

        gP.getPlayer().detectColision.x = gP.getPlayer().worldX + gP.getPlayer().detectColision.x;
        gP.getPlayer().detectColision.y = gP.getPlayer().worldY + gP.getPlayer().detectColision.y;

 switch (ent.direction) {
    case "arriba":
        ent.detectColision.y -= ent.speed;
        break;
    case "abajo":
        ent.detectColision.y += ent.speed;
        break;
    case "izquierda":
        ent.detectColision.x -= ent.speed;
        break;
    case "derecha":
        ent.detectColision.x += ent.speed;
        break;
}
        if (ent.detectColision.intersects(gP.getPlayer().detectColision)) {
            if (gP.getPlayer().colisionOn) {
                ent.colisionOn = true;
            }
        }

        ent.detectColision.x = ent.detectColisionDefaultX;
        ent.detectColision.y = ent.detectColisionDefaultY;
        gP.getPlayer().detectColision.x = gP.getPlayer().detectColisionDefaultX;
        gP.getPlayer().detectColision.y = gP.getPlayer().detectColisionDefaultY;
    }
}
    
