package Game.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Game.GamePanel;

public class TilesHandler {

    private GamePanel gP;
    private int maxTiles = 10;
    public Tile[] tilesArray;
    public int tilesMapCodes[][];

    public TilesHandler(GamePanel gP) {
        this.gP = gP;
        this.tilesArray = new Tile[maxTiles];
        this.tilesMapCodes = new int[gP.getMaxRenWorld()][gP.getMaxColWorld()];
        getImgTile();
        loadMap("/assets/maps/world01.txt");
    }

    public TilesHandler(GamePanel gP, String map) {
        this.gP = gP;
        this.tilesArray = new Tile[maxTiles];
        this.tilesMapCodes = new int[gP.getMaxRenWorld()][gP.getMaxColWorld()];
        getImgTile();
        loadInitialMap(map);
    }

    public void getImgTile() {
        try {
            tilesArray[0] = new Tile();
            tilesArray[0].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/agua.png")));
            tilesArray[1] = new Tile();
            tilesArray[1].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/arbol.png")));
            tilesArray[2] = new Tile();
            tilesArray[2].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/arena.png")));
            tilesArray[3] = new Tile();
            tilesArray[3].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/muro.png")));
            tilesArray[4] = new Tile();
            tilesArray[4].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/pasto.png")));
            tilesArray[5] = new Tile();
            tilesArray[5].setImage(ImageIO.read(getClass().getResourceAsStream("/assets/tiles/suelo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapRoute) {
        try {
            InputStream map = getClass().getResourceAsStream(mapRoute);
            BufferedReader br = new BufferedReader(new InputStreamReader(map));
            int ren = 0, col = 0;
            while (ren < gP.getMaxRenWorld() && col < gP.getMaxColWorld()) {
                String renDat = br.readLine();
                while (col < gP.getMaxColWorld()) {
                    String codes[] = renDat.split(" ");
                    int code = Integer.parseInt(codes[col]);
                    this.tilesMapCodes[ren][col] = code;
                    col++;
                }
                if (col == gP.getMaxColWorld()) {
                    ren++;
                    col = 0;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadInitialMap(String mapData) {
        try {
            int ren = 0, col = 0;
            for (int i = 0; i < mapData.length(); i++) {
                char c = mapData.charAt(i);

                if (!Character.isDigit(c)) {
                    continue;
                }

                int code = Character.getNumericValue(c);
                this.tilesMapCodes[ren][col] = code;
                col++;

                if (col == gP.getMaxColWorld()) {
                    col = 0;
                    ren++;
                    if (ren == gP.getMaxRenWorld()) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int renWorld = 0, colWorld = 0;
        while (renWorld < gP.getMaxRenWorld() && colWorld < gP.getMaxColWorld()) {
            int tileCode = this.tilesMapCodes[renWorld][colWorld];
            int worldX = colWorld * gP.getSizeTile();
            int worldY = renWorld * gP.getSizeTile();
            int screenX = worldX - gP.getPlayer().getWorldX() + gP.getPlayer().getScreenX();
            int screenY = worldY - gP.getPlayer().getWorldY() + gP.getPlayer().getScreenY();

            if (worldX + gP.getSizeTile() > gP.getPlayer().getWorldX() - gP.getPlayer().getScreenX()
                    && worldX - gP.getSizeTile() < gP.getPlayer().getWorldX() + gP.getPlayer().getScreenX()
                    && worldY + gP.getSizeTile() > gP.getPlayer().getWorldY() - gP.getPlayer().getScreenY()
                    && worldY - gP.getSizeTile() < gP.getPlayer().getWorldY() + gP.getPlayer().getScreenY()) {
                g2.drawImage(tilesArray[tileCode].getImage(), screenX, screenY, gP.getSizeTile(), gP.getSizeTile(), null);
            }

            colWorld++;
            if (colWorld == gP.getMaxColWorld()) {
                colWorld = 0;
                renWorld++;
            }
        }
    }
    public Tile [] getArrayTiles() {
        return this.tilesArray;
    }
    public int getMapCodeTiles(int ren, int col) {
        return this.tilesMapCodes[ren][col];
    }
    public int getMaxTiles() {
        return this.maxTiles;
    }
    public void setMaxTiles(int maxTiles) {
        this.maxTiles = maxTiles;
    }
    public void setArrayTiles(Tile[] tilesArray) {
        this.tilesArray = tilesArray;
    }

    public int getCodigoMapaTiles(int renSuperior, int colIzquierda) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCodigoMapaTiles'");
    }
}
