package Entities;

import Graphics.*;
import World.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public static double life = 100;

    public static int ammo = 0;

    //Array de Sprites do jogador virado para direita
    private BufferedImage[] rightPlayer;
    //Array de Sprites do jogador virado para esquerda
    private BufferedImage[] leftPlayer;

    private BufferedImage rightDamagePlayer;
    private BufferedImage leftDamagePlayer;

    public static boolean isDamaged;

    public static boolean hasGun = false;

    public static boolean shooting = false;
    public static boolean shootingMouse = false;

    public int mX = 0;
    public int mY = 0;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        rightDamagePlayer = Game.spriteSheet.getSprite(spriteSize * 4, spriteSize * 0, spriteSize, spriteSize);
        leftDamagePlayer = Game.spriteSheet.getSprite(spriteSize * 4, spriteSize * 2, spriteSize, spriteSize);

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
        if (right && World.isFree((int) (this.getX() + speed), this.getY())) {
            moved = true;
            dir = rightDir;
            setX(x += speed);
        } else if (left && World.isFree((int) (this.getX() - speed), this.getY())) {
            moved = true;
            dir = leftDir;
            setX(x -= speed);
        }
        if (up && World.isFree(this.getX(), (int) (this.getY() - speed))) {
            moved = true;
            setY(y -= speed);
        } else if (down && World.isFree(this.getX(), (int) (this.getY() + speed))) {
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

        if (life <= 0) {
            Game.entities = new ArrayList<Entity>();
            Game.enemies = new ArrayList<Enemy>();
            //Inicializa -spriteSheet- com arquivos de imagens
            Game.spriteSheet = new SpriteSheet(System.getProperty("user.dir") + "/src/main/resources/res/SpritSheet.png");
            //instacia o jogador
            Game.player = new Player(0, 0, 16, 16, Game.spriteSheet.getSprite(0, 0, 16, 16));
            //adiciona uma entidade do tipo jogar a lista de entidades
            Game.entities.add(Game.player);
            //Inicializa o map
            Game.world = new World(System.getProperty("user.dir") + "/src/main/resources/res/MapTest.png");
            life = 100;
            ammo = 0;
            hasGun = false;
            return;
        }

//        if (shooting && hasGun && ammo > 0) {
//            shooting = false;
//            ammo--;
//            int dx;
//            int dy = 0;
//            int px = 0;
//            int py = 8;
//            if (dir == rightDir) {
//                dx = 1;
//                px = 12;
//            } else {
//                dx = -1;
//            }
//            ShootedBullet bullet = new ShootedBullet(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
//            Game.bullets.add(bullet);
//            shooting = false;
//        }

        if (shootingMouse) {
            shootingMouse = false;
            if (hasGun && ammo > 0) {
                ammo--;
                double angle = Math.atan2((this.getY() + 8 - Camera.y) - mY, (this.getX() + 8 - Camera.x) - mX);
                double dx = Math.cos(angle) * -1;
                double dy = Math.sin(angle) * -1;
                int px = 0;
                int py = 8;


                ShootedBullet bullet = new ShootedBullet(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
                Game.bullets.add(bullet);
            }
        }

        collidingWithEntities();

        //Controla a World.Camera seguindo o jogador
        Camera.x = Camera.clamp(x - (Game.WIDTH / 2), World.width * 16 - Game.WIDTH, 0);
        Camera.y = Camera.clamp(y - (Game.HEIGHT / 2), World.height * 16 - Game.HEIGHT, 0);

    }

    private void heal(int heal) {
        if ((heal + life) > 100) {
            life = 100;
        } else {
            life += heal;
        }
    }

    public void collidingWithEntities() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Heal) {
                if (Entity.isColliding(this, current)) {
                    if (life < 100) {
                        Game.entities.remove(i);
                    }
                    heal(20);
                }
            } else if (current instanceof Bullet) {
                if (Entity.isColliding(this, current)) {
                    ammo += 20;
                    Game.entities.remove(i);
                }
            } else if (current instanceof Weapon) {
                if (Entity.isColliding(this, current)) {
                    hasGun = true;
                    Game.entities.remove(i);
                }
            }
        }
    }


    @Override
    public void render(Graphics g) {
        if (!isDamaged) {
            if (dir == rightDir) {
                g.drawImage(rightPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
                if (hasGun) {
                    g.drawImage(Entity.rightPistol, this.getX() + 4 - Camera.x, this.getY() - Camera.y + 2, null);
                }
            } else if (dir == leftDir) {
                g.drawImage(leftPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
                if (hasGun) {
                    g.drawImage(Entity.leftPistol, this.getX() - 4 - Camera.x, this.getY() + 2 - Camera.y, null);
                }
            }
        } else {
            if (dir == rightDir) {
                g.drawImage(rightDamagePlayer, getX() - Camera.x, getY() - Camera.y, null);
                isDamaged = false;
            } else if (dir == leftDir) {
                g.drawImage(leftDamagePlayer, getX() - Camera.x, getY() - Camera.y, null);
                isDamaged = false;
            }
        }
    }

}
