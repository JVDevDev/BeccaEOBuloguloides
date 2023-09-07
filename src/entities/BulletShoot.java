package entities;

import main.Game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletShoot extends Entity{

    private double dx;
    private double dy;
    private double spd = 4;

    private int life = 30, curlife = 0;

    public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
    }

    public void update(){
        x += dx * spd;
        y += dy * spd;

        curlife++;
        if (curlife == life){
            Game.bulletShoots.remove(this);
            return;
        }
    }

    public void render(Graphics g){
        g.setColor(new Color(64, 64, 64));
        g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
    }
}