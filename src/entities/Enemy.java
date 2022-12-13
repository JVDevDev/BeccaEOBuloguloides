package entities;

import main.Game;
import main.Sound;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{

    private  double speed = 0.45;

    private int maskx = 3, masky = 3, maskw = 10, maskh = 11;

    private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

    private BufferedImage[] sprites;

    private BufferedImage[] spritesDamage;

    private int life = 10;

    private boolean isDamaged = false;
    private int damageFrames = 10, damageCurrent = 0;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, null);
        sprites = new BufferedImage[2];
        sprites[0] = Game.spritesheet.getSpritesheet(48, 144, 16, 16);
        sprites[1] = Game.spritesheet.getSpritesheet(64, 144, 16, 16);
        spritesDamage = new BufferedImage[2];
        spritesDamage[0] = Game.spritesheet.getSpritesheet(80, 144, 16, 16);
        spritesDamage[1] = Game.spritesheet.getSpritesheet(96, 144, 16, 16);
    }

    public void update() {
        if (this.isCollidinWithPlayer() == false){
            if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
                    && !isColliding((int) (x + speed), this.getY())) {
                x += speed;
            } else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
                    && !isColliding((int) (x - speed), this.getY())) {
                x -= speed;
            }
            if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
                    && !isColliding(this.getX(), (int) (y + speed))) {
                y += speed;
            } else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
                    && !isColliding(this.getX(), (int) (y - speed))) {
                y -= speed;
            }
        } else {
            // fazer o player perder vida
            if (Game.random.nextInt(100) < 35){
                Sound.hitEffect.play();
                Game.player.life -= Game.random.nextInt(3);
                Game.player.isDamaged = true;
            }
        }



        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;
            }
        }
        collidingBullet();
        if (life <= 0){
            destroySelf();
            return;
        }

        if (isDamaged){
            damageCurrent++;
            if (damageCurrent == damageFrames){
                damageCurrent = 0;
                isDamaged = false;
            }
        }
    }

    public void destroySelf(){
        Game.enemies.remove(this);
        Game.entities.remove(this);
    }

    public void collidingBullet(){
        for (int i = 0; i < Game.bulletShoots.size(); i++){
            Entity e = Game.bulletShoots.get(i);
                if (Entity.isColliding(this, e)){
                    Sound.hitEnemy.play();
                    isDamaged = true;
                    life-= Player.ammoDamage;
                    Game.bulletShoots.remove(i);
                    return;
                }
        }
    }

    public boolean isCollidinWithPlayer(){
        Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() +masky, maskw, maskh);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
        return enemyCurrent.intersects(player);
    }

    public boolean isColliding(int xNext, int yNext){
        Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext +masky, maskw, maskh);
        for (int i = 0; i < Game.enemies.size(); i++){
            Enemy e = Game.enemies.get(i);
            if (e == this){
                continue;
            }
            Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() +masky, maskw, maskh);
            if (enemyCurrent.intersects(targetEnemy)){
                return true;
            }

        }

        return false;
    }
    public void render(Graphics g){
        if (!isDamaged){
            g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else {
            g.drawImage(spritesDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }

    }
}
