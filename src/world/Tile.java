package world;

import entities.Camera;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSpritesheet(0,0, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSpritesheet(16,0, 16, 16);
    public static BufferedImage TILE_WALL2 = Game.spritesheet.getSpritesheet(16,16, 16, 16);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}