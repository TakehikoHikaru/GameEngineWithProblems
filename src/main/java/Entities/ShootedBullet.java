package Entities;

import World.Camera;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShootedBullet extends Entity{

private double dX;
private double dY;
private double speed =10;

private int currentLife = 0;
private int maxLife = 40;

    public ShootedBullet(int x, int y, int width, int height, BufferedImage sprite,double dX,double dY) {
        super(x, y, width, height, sprite);
        this.dX = dX;
        this.dY = dY;
    }

    @Override
    public void tick() {
        x += dX * speed;
        y += dY * speed;

        currentLife++;
        if (currentLife >= maxLife){
            currentLife = 0;
                Game.bullets.remove(this);
                return;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,5,5);

    }
}
