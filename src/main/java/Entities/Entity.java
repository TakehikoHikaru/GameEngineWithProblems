package Entities;


import Main.Game;
import World.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    final static private int spriteSize = 16;
    //Carrega todas as entidades
    final public static BufferedImage lifePack = Game.spriteSheet.getSprite(spriteSize * 0, spriteSize * 4, spriteSize, spriteSize);
    final public static BufferedImage enemy = Game.spriteSheet.getSprite(spriteSize * 0, spriteSize * 3, spriteSize, spriteSize);
    final public static BufferedImage damagedEnemy = Game.spriteSheet.getSprite(spriteSize*2,spriteSize*3,spriteSize,spriteSize);
    final public static BufferedImage rangeEnemy = Game.spriteSheet.getSprite(spriteSize*3,spriteSize*3,spriteSize,spriteSize);
    final public static BufferedImage leftPistol = Game.spriteSheet.getSprite(spriteSize * 1, spriteSize * 5, spriteSize, spriteSize);
    final public static BufferedImage rightPistol = Game.spriteSheet.getSprite(spriteSize * 0, spriteSize * 5, spriteSize, spriteSize);
    final public static BufferedImage bullet = Game.spriteSheet.getSprite(spriteSize * 1, spriteSize * 4, spriteSize, spriteSize);

    //Possições da Entidade
    protected int x;
    protected int y;
    //Tamanho da Sprite
    protected int width;
    protected int height;
    //Sprite
    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
    }

    public void tick() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static boolean isColliding(Entity e1,Entity e2){
        Rectangle rectE1 = new Rectangle(e1.getX(),e1.getY(), spriteSize, spriteSize);
        Rectangle rectE2 = new Rectangle(e2.getX(),e2.getY(), spriteSize, spriteSize);
        return rectE1.intersects(rectE2);
    }
}
