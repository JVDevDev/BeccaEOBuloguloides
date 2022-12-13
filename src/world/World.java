package world;

import entities.*;

import graficos.Spritesheet;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static main.Game.player;

public class World {

    public static Tile[] tiles;
    public static int WIDTH,HEIGHT;
    public static final int TILE_SIZE = 16;
    public World(String path) {

        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int xx = 0; xx < map.getWidth(); xx++){
                for (int yy = 0; yy < map.getHeight(); yy++){
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    if (pixelAtual == 0xFF000000){
                        //Chão - Floor
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else if (pixelAtual == 0xFFFFFFFF){
                        //Parede - Wall
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL);
                    } else if (pixelAtual == 0xFFa1a1a1){
                        //Parede - Wall
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL2);
                    } else if (pixelAtual == 0xFF10289f){
                        //player
                        player.setX(xx * 16);
                        player.setY(yy * 16);
                    } else if(pixelAtual  == 0xFFb41010) {
                        //Enemy
                        Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
                        Game.entities.add(en);
                        Game.enemies.add(en);
                    } else if (pixelAtual == 0xFFd9cb15){
                        //Weapon
                        Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
                    } else if(pixelAtual == 0xFF941b91){
                        //Bullet
                        Bullet bullet = new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN);
                        bullet.setMask(3, 5, 10, 3);
                        Game.entities.add(bullet);
                    } else if(pixelAtual == 0xFF3ab425){
                        //life
                        Lifepack lifepack = new Lifepack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN);
                        lifepack.setMask(2, 2, 13, 6);
                        Game.entities.add(lifepack);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(int xnext, int ynext){
        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;

        int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = ynext / TILE_SIZE;

        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xnext + TILE_SIZE - 1)  / TILE_SIZE;
        int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public static void restartGame(String newWorld){
        Game.entities.clear();
        Game.enemies.clear();
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 16, 16, Game.spritesheet.getSpritesheet(32, 0, 16, 16));
        Game.entities.add(player);
        Game.world = new World("/" + newWorld);
        return;
    }

    public void render(Graphics g){
        int xstart = Camera.x / 16;
        int ystart = Camera.y / 16;

        int xfinal = xstart + Game.WIDTH / 16;
        int yfinal = ystart + Game.HEIGHT / 16;

        for (int xx = xstart; xx <= xfinal; xx++){
            for (int yy = ystart; yy <= yfinal; yy++){
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT){
                    continue;
                }
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }
}
