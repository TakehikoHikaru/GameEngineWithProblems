package Entities;

import Graphics.Game;
import World.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    private final int spriteSize = 16;

    private double speed = 1;
    public boolean right, up, left, down;

    private int frames = 0;
    //Controla a velocidade da animação de caminhar
    private int maxFrames = 20;
    private int index = 0;
    private int maxIndex = 1;

    private BufferedImage sprites[];


    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, null);
        sprites = new BufferedImage[4];
        sprites[0] = Game.spriteSheet.getSprite(spriteSize * 0, spriteSize * 3, spriteSize, spriteSize);
        sprites[1] = Game.spriteSheet.getSprite(spriteSize * 1, spriteSize * 3, spriteSize, spriteSize);
        sprites[2] = Game.spriteSheet.getSprite(spriteSize * 0, spriteSize * 3, spriteSize, spriteSize);
        sprites[3] = Game.spriteSheet.getSprite(spriteSize * 1, spriteSize * 3, spriteSize, spriteSize);

    }

    @Override
    public void tick() {


        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index == 3) {
                index = 0;
            }
        }
        if (!isCollidingWithPlayer()) {
            if ((int) x < Game.player.getX() && World.isFree((getX() + speed), this.getY()) && !isColliding((int) (getX() + speed), this.getY())) {
                x += speed;
            } else if ((int) x > Game.player.getX() && World.isFree((getX() - speed), this.getY()) && !isColliding((int) (getX() - speed), this.getY())) {
                x -= speed;
            }
            if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (this.getY() + speed)) && !isColliding(this.getX(), (int) (this.getY() + speed))) {
                y += speed;
            } else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (this.getY() - speed)) && !isColliding(this.getX(), (int) (this.getY() - speed))) {
                y -= speed;
            }
        } else {
            if (Game.random.nextInt(100) < 15) {
                Game.player.life -= 10;
            }
            if (Game.player.life <= 0) {
                System.exit(1);
            }
        }

    }

    public boolean isCollidingWithPlayer() {
        Rectangle currentEnemy = new Rectangle(this.getX(), this.getY(), World.spriteSize, World.spriteSize);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
        return currentEnemy.intersects(player);
    }

    /**
     * Verifica colisoes entre inimigos
     */
    public boolean isColliding(int nextX, int nextY) {
        Rectangle currentEnemy = new Rectangle(nextX, nextY, World.spriteSize, World.spriteSize);

        for (int i = 0; i < Game.enemies.size(); i++) {
            Enemy e = Game.enemies.get(i);
            if (e == this) {
                continue;
            }
            Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.spriteSize, World.spriteSize);
            if (currentEnemy.intersects(targetEnemy)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
