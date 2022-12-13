package entities;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Entity {

    public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSpritesheet(32, 128, 16, 16);
    public static BufferedImage WEAPON_EN = Game.spritesheet.getSpritesheet(48, 128, 16, 16);
    public static BufferedImage BULLET_EN = Game.spritesheet.getSpritesheet(32, 144, 16, 16);
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSpritesheet(48, 144, 16, 16);


    protected double x;
    protected double y;
    protected int width;
    protected int height;

    private BufferedImage sprite;

    private int maskx, masky, maskwidth, maskheight;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.maskx = 0;
        this.masky = 0;
        this.maskwidth = width;
        this.maskheight = height;
    }

    public void setMask(int maskx, int masky, int maskwidth, int maskheight){
        this.maskx = maskx;
        this.masky = masky;
        this.maskwidth = maskwidth;
        this.maskheight = maskheight;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public static boolean isColliding(Entity e1, Entity e2){
        Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskwidth, e1.maskheight);
        Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskwidth, e2.maskheight);

        return e1Mask.intersects(e2Mask);
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
        //g.setColor(Color.red);
        //g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskwidth, maskheight);
    }

    public void update(){

    }

}
