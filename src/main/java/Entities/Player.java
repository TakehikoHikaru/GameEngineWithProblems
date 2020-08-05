package Entities;

import Graphics.Game;
import World.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    //Constante que define o tamanho padrão da sprite
    final private int spriteSize = 16;

    public boolean right, up, left, down;
    public double speed = 2;

    //Controla a direção do personagem parado
    final int rightDir = 0;
    final int leftDir = 1;
    int dir = rightDir;

    private int frames = 0;
    //Controla a velocidade da animação de caminhar
    private int maxFrames = 5;
    private int index = 0;
    private int maxIndex = 4;

    private boolean moved = false;

    //Array de Sprites do jogador virado para direita
    private BufferedImage[] rightPlayer;
    //Array de Sprites do jogador virado para esquerda
    private BufferedImage[] leftPlayer;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];

        for (int i = 0; i < rightPlayer.length; i++) {
            rightPlayer[i] = Game.spriteSheet.getSprite(spriteSize * i, spriteSize * 0, spriteSize, spriteSize);
        }
        for (int i = 0; i < leftPlayer.length; i++) {
            leftPlayer[i] = Game.spriteSheet.getSprite(spriteSize * i, spriteSize * 2, spriteSize, spriteSize);
        }

    }

    @Override
    public void tick() {
        moved = false;
        if (right && World.isFree((int)(this.getX()+ speed), this.getY())){
            moved = true;
            dir = rightDir;
            setX(x += speed);
        } else if (left && World.isFree((int)(this.getX() - speed), this.getY())) {
            moved = true;
            dir = leftDir;
            setX(x -= speed);
        }
        if (up && World.isFree(this.getX(),(int)(this.getY() - speed))) {
            moved = true;
            setY(y -= speed);
        } else if (down && World.isFree(this.getX(),(int)(this.getY()+speed))) {
            moved = true;
            setY(y += speed);
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                index++;
                if (index == maxIndex) {
                    index = 0;
                }
            }
        }

        //Controla a World.Camera seguindo o jogador
        Camera.x = Camera.clamp(x - (Game.WIDTH / 2), World.width * 16 - Game.WIDTH, 0);
        Camera.y = Camera.clamp(y - (Game.HEIGHT / 2), World.height * 16 - Game.HEIGHT, 0);

    }

    String texto;

    @Override
    public void render(Graphics g) {
        if (dir == rightDir) {
            g.drawImage(rightPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
        } else if (dir == leftDir) {
            g.drawImage(leftPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
        }
    }

}
