package World;

import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    final static private int spriteSize = 16;
    //Carrega todos os tiles
    public static BufferedImage tileFloor = Game.spriteSheet.getSprite(spriteSize*0,spriteSize,spriteSize,spriteSize);
    public static BufferedImage grassFlower = Game.spriteSheet.getSprite(spriteSize*2,spriteSize*1,spriteSize,spriteSize);
    public static BufferedImage tileWall = Game.spriteSheet.getSprite(spriteSize,spriteSize,spriteSize,spriteSize);

    private BufferedImage sprite;
    private int x;
    private int y;

    public Tile(BufferedImage sprite, int x, int y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g){
    g.drawImage(sprite,x- Camera.x,y- Camera.y,null);
}

}
