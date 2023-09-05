package entities;

import main.Game;
import main.Sound;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left, up, down;
    public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
    public int dir = down_dir;
    public double speed = 1.0;


    //Renderização
    private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage[] upPlayer;
    private BufferedImage[] downPlayer;

    private BufferedImage[] rightDamage;
    private BufferedImage[] leftDamage;
    private BufferedImage[] upDamage;
    private BufferedImage[] downDamage;

    private BufferedImage[] rightArmed;
    private BufferedImage[] leftArmed;
    private BufferedImage[] upArmed;
    private BufferedImage[] downArmed;

    private BufferedImage[] rightArmedDamage;
    private BufferedImage[] leftArmedDamage;
    private BufferedImage[] upArmedDamage;
    private BufferedImage[] downArmedDamage;


    //criando ações
    private boolean arma = false;
    public int ammo = 0;
    public static int ammoDamage = 2;
    public boolean isDamaged = false;
    private int damageFrames = 0;
    public boolean shoot = false, mouseShoot = false;
    public double life = 30, maxLife = 30;
    public int mx, my;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        //player normal
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        upPlayer = new BufferedImage[4];
        downPlayer = new BufferedImage[4];

        //player tomando dano
        rightDamage = new BufferedImage[4];
        leftDamage = new BufferedImage[4];
        upDamage = new BufferedImage[4];
        downDamage = new BufferedImage[4];

        //player armado
        rightArmed = new BufferedImage[4];
        leftArmed = new BufferedImage[4];
        upArmed = new BufferedImage[4];
        downArmed = new BufferedImage[4];

        //player armado tomando dano
        rightArmedDamage = new BufferedImage[4];
        leftArmedDamage = new BufferedImage[4];
        upArmedDamage = new BufferedImage[4];
        downArmedDamage = new BufferedImage[4];

        //player normal
        for (int i = 0; i < 4; i++){
            rightPlayer[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 32, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            leftPlayer[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 48, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            upPlayer[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 16, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            downPlayer[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 0, 16, 16);
        }

        //player tomando dano
        for (int i = 0; i < 4; i++){
            rightDamage[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 96, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            leftDamage[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 112, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            upDamage[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 80, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            downDamage[i] = Game.spritesheet.getSpritesheet(32 + (i * 16), 64, 16, 16);
        }

        //player armado
        for (int i = 0; i < 4; i++){
            rightArmed[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 32, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            leftArmed[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 48, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            upArmed[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 16, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            downArmed[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 0, 16, 16);
        }

        //player armado tomando dano
        for (int i = 0; i < 4; i++){
            rightArmedDamage[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 96, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            leftArmedDamage[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 112, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            upArmedDamage[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 80, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            downArmedDamage[i] = Game.spritesheet.getSpritesheet(96 + (i * 16), 64, 16, 16);
        }
    }

    public void update(){
        moved = false;
        if (right && World.isFree((int) (x + speed), this.getY())){
            moved = true;
            dir = right_dir;
            x += speed;
        } else if (left && World.isFree((int) (x - speed), this.getY())){
            moved = true;
            dir = left_dir;
            x -= speed;
        }
        if (up && World.isFree(this.getX(), (int) (y - speed))){
            moved = true;
            dir = up_dir;
            y -= speed;
        } else if (down && World.isFree(this.getX(), (int) (y + speed))){
            moved = true;
            dir = down_dir;
            y += speed;
        }

        if (moved){
            frames++;
            if (frames == maxFrames){
                frames = 0;
                index++;
                if (index > maxIndex){
                    index = 0;
                }
            }
        }

        checkCollisionGun();
        if (life < maxLife){
            checkCollisionLifePack();
        }
        checkCollisionAmmo();

        if (isDamaged){
            this.damageFrames++;
            if (this.damageFrames == 10){
                this.damageFrames = 0;
                isDamaged = false;
            }
        }

        if (shoot){
            shoot = false;
            if (arma && ammo > 0) {
                ammo--;
                int dx = 0;
                int dy = 0;
                int px = 0;
                int py = 0;
                if (dir == right_dir) {
                    px = 12;
                    py = 6;
                    dx = 1;
                } else if (dir == left_dir) {
                    px = 1;
                    py = 7;
                    dx = -1;
                }
                if (dir == up_dir) {
                    px = 8;
                    py = -2;
                    dy = -1;
                } else if (dir == down_dir) {
                    py = 7;
                    px = 5;
                    dy = 1;
                }

                BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
                Game.bulletShoots.add(bulletShoot);
                Sound.shoot.play();
            }
        }

        if (mouseShoot){
            mouseShoot = false;
            if (arma && ammo > 0) {
                ammo--;
                int px = 0;
                int py = 0;
                double angle = 0;

                if (dir == right_dir) {
                    px = 12;
                    py = 6;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
                    if (angle < -0.7 && angle >= -3.15){
                        angle = -0.7;
                    }
                    if (angle > 0.7 && angle <= 3.15){
                        angle = 0.7;
                    }
                } else if (dir == left_dir) {
                    px = 1;
                    py = 7;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
                    if (angle > -2.3 && angle <= 0){
                        angle = -2.3;
                    }
                    if (angle < 2.3 && angle >= 0){
                        angle = 2.3;
                    }
                }
                if (dir == up_dir) {
                    px = 8;
                    py = -2;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
                    if (angle > -0.7 && angle <= 1.5){
                        angle = -0.7;
                    }
                    if (angle < -2.3 && angle > -3.15 || angle >= 1.5){
                        angle = -2.3;
                    }
                } else if (dir == down_dir) {
                    py = 7;
                    px = 5;
                    angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
                    if (angle < 0.7 && angle >= -1.5){
                        angle = 0.7;
                    }
                    if (angle > 2.3 && angle < 3.15 || angle <= -1.5){
                        angle = 2.3;
                    }
                }
                double dx = Math.cos(angle);
                double dy = Math.sin(angle);

                BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx,   dy);
                Game.bulletShoots.add(bulletShoot);
                Sound.shoot.play();
            }
        }

        if(life <= 0){
            //Game Over!
            life = 0;
            Game.gameState = "GAME_OVER";
        }

        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

    }
    public void checkCollisionGun(){
        for (int i = 0;  i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Weapon){
                if (Entity.isColliding(this, atual)){
                    arma = true;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void checkCollisionAmmo(){
        for (int i = 0;  i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Bullet){
                if (Entity.isColliding(this, atual)){
                    Sound.municao.play();
                    ammo += 5;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void checkCollisionLifePack(){
        for (int i = 0;  i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Lifepack){
                if (Entity.isColliding(this, atual)){
                    Sound.vida.play();
                    life += 3;
                    if(life > maxLife){
                        life = maxLife;
                    }
                    Game.entities.remove(atual);
                }
            }
        }
    }


    public void render(Graphics g){
        if (!isDamaged){
            if (dir == up_dir){
                if (arma){
                    g.drawImage(upArmed[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            } else if (dir == down_dir){
                if (arma){
                    g.drawImage(downArmed[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }
            if (dir == right_dir){
                if (arma){
                    g.drawImage(rightArmed[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            } else if (dir == left_dir){
                if (arma){
                    g.drawImage(leftArmed[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }
        } else {
            if (dir == up_dir){
                if (arma){
                    g.drawImage(upArmedDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(upDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            } else if (dir == down_dir){
                if (arma){
                    g.drawImage(downArmedDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(downDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }
            if (dir == right_dir){
                if (arma){
                    g.drawImage(rightArmedDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(rightDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            } else if (dir == left_dir){
                if (arma){
                    g.drawImage(leftArmedDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                } else {
                    g.drawImage(leftDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                }
            }
        }
    }
}