package World;

import Entities.*;
import Graphics.*;
import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class World {

    private static Tile[] tiles;
    public static int width;
    public static int height;

    final public static int spriteSize = 16;


    /**
     * Metodo responsavel por transformar uma imagem em um array, então percorrer ele e montar o mapa
     * Com baso no código da cor de cada pixel
     * */
    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(new File(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            width = map.getWidth();
            height = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            //Transforma uma cor na imagem em um sprite
            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    int pos = i + (j * map.getWidth());
                    //Filtra os pixiels por cores.
                    //Por padrão o codigo hexadecimal tem 0xFF no inicio dele
                    tiles[pos] = new FloorTile(Tile.tileFloor, i * 16, j * 16);
                    switch (pixels[i + (j * map.getWidth())]) {
                        case 0xFF4800FF:
                            //player
                            Game.player.setX(i * 16);
                            Game.player.setY(j * 16);
                            tiles[pos] = new FloorTile(Tile.tileFloor, i * 16, j * 16);
                            break;
                        case 0xFF4CFF00:
                            //Chão
                            if (Game.random.nextInt(100) < 6) {
                                tiles[pos] = new FloorTile(Tile.grassFlower, i * 16, j * 16);
                            } else {
                                tiles[pos] = new FloorTile(Tile.tileFloor, i * 16, j * 16);
                            }
                            break;
                        case 0xFF000000:
                            //Parede
                            tiles[pos] = new WallTile(Tile.tileWall, i * 16, j * 16);
                            break;
                        case 0xFFFF00DC:
                            //lifePack
                            Game.entities.add(new Heal(i * 16, j * 16, 16, 16, Entity.lifePack));
                            break;
                        case 0xFFFF0000:
                            //Enemy
                            Enemy enemy = new Enemy(i * 16, j * 16, 16, 16, Entity.enemy);
                            Game.entities.add(enemy);
                            Game.enemies.add(enemy);
                            break;
                        case 0xFFFFD800:
                            //bullet
                            Game.entities.add(new Bullet(i * 16, j * 16, 16, 16, Entity.bullet));
                            break;
                        case 0xFF303030:
                            //Gun
                            Game.entities.add(new Weapon(i * 16, j * 16, 16, 16, Entity.rightPistol));
                            break;
                        case 0xFF133F00:
                            tiles[pos] = new WallTile(Tile.tileFloor, i * 16, j * 16);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo improvisado para resetar o mundo
     * */
    public static void restartWorld(int level) {
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        //Inicializa -spriteSheet- com arquivos de imagens
        Game.spriteSheet = new SpriteSheet(System.getProperty("user.dir") + "/src/main/resources/res/SpritSheet.png");
        //instacia o jogador
        Game.player = new Player(0, 0, 16, 16, Game.spriteSheet.getSprite(0, 0, 16, 16));
        //adiciona uma entidade do tipo jogar a lista de entidades
        Game.entities.add(Game.player);
        //Inicializa o map
        Game.world = new World(System.getProperty("user.dir") + "/src/main/resources/res/" + "level_" + level + ".png");
        Game.player.life = 100;
        Game.player.ammo = 0;
        Game.player.hasGun = false;
        return;
    }

    public void render(Graphics g) {
        int xStart = Camera.x / 16;
        int yStart = Camera.y / 16;

        int xFinal = xStart + (Game.WIDTH / 16) + 10;
        int yFinal = yStart + (Game.HEIGHT / 16) + 10;

        for (int i = xStart; i <= xFinal; i++) {
            for (int j = yStart; j <= yFinal; j++) {
                if (i < 0 || j < 0 || i >= width || j >= height) {
                    continue;
                }
                Tile tile = tiles[i + (j * width)];
                tile.render(g);
            }
        }
    }

    /**
     * Verifica se os blocos proximos tem colisão
     */
    public static boolean isFree(double nextX, double nextY) {
        int x1 = (int) (nextX / spriteSize);
        int y1 = (int) (nextY / spriteSize);
        int x2 = (int) ((nextX - 1) / spriteSize);
        int y2 = (int) (nextY / spriteSize);
        int x3 = (int) ((nextX - 1) / spriteSize);
        int y3 = (int) ((nextY - 1) / spriteSize);
        int x4 = (int) (nextX / spriteSize);
        int y4 = (int) ((nextY - 1) / spriteSize);

        return !((tiles[x1 + (y1 * World.width)]) instanceof WallTile ||
                (tiles[x2 + (y2 * World.width)]) instanceof WallTile ||
                (tiles[x3 + (y3 * World.width)]) instanceof WallTile ||
                (tiles[x4 + (y4 * World.width)]) instanceof WallTile);
    }
}


