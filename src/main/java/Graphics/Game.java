package Graphics;

import Entities.*;
import World.*;
import javafx.scene.transform.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {


    // Janela
    public static JFrame frame;
    // Tamanho e Escala da Janela
    public static final int WIDTH = 400;
    public static final int HEIGHT = 260;
    private final int SCALE = 2;
    //
    private Thread thread;
    // Verifica se o jogo esta rodando
    private boolean isRunning = true;
    //Layer do fundo da tela
    private BufferedImage image;

    //Arquivos com as Sprites do jogo
    public static SpriteSheet spriteSheet;

    //Lista de Entidades já adicionadas
    public static List<Entity> entities;

    //Lista todas a Entidades de inimigos
    public static List<Enemy> enemies;

    public static List<ShootedBullet> bullets;

    //Objeto do jogador
    public static Player player;

    //Estancia o mundo
    public static World world;

    public static Random random;

    public UI ui;

    public static int currentLevel = 1;
    public int maxLevel = 2;

    private boolean restartGame = false;

    final public static String WorldStatusMenu = "Menu";
    final public static String WorldStatusGameOver = "Game_Over";
    final public static String WorldStatusNormal = "Game_Normal";
    public static String WorldStatus = WorldStatusMenu;

    public Menu menu;

    public Game() throws IOException {
        random = new Random();
        //Habilita o keyListener nessa classe
        addKeyListener(this);
        addMouseListener(this);

        menu = new Menu();

        //seta o tamanho da janela
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();

        ui = new UI();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //Inicaliza -entities-
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        //Inicializa -spriteSheet- com arquivos de imagens
        spriteSheet = new SpriteSheet(System.getProperty("user.dir") + "/src/main/resources/res/SpritSheet.png");
        //instacia o jogador
        player = new Player(0, 0, 16, 16, spriteSheet.getSprite(0, 0, 16, 16));
        //adiciona uma entidade do tipo jogar a lista de entidades
        entities.add(player);

        bullets = new ArrayList<ShootedBullet>();
        //Inicializa o map
        world = new World(System.getProperty("user.dir") + "/src/main/resources/res/level_1.png");
    }

    private void createFolder() throws IOException {
        String FileFolder = System.getenv("APPDATA") + "\\" + "TestGame";
        System.out.println(System.getProperty("user.dir"));
        String os = System.getProperty("os.name").toUpperCase();
        if (os.contains("WIN")) {
            FileFolder = System.getenv("APPDATA") + "\\" + "TestGame";
            System.out.println("Found windows");
        }
        if (os.contains("MAC")) {
            FileFolder = System.getProperty("user.home") + "/Library/Application " + "TestGame"
                    + "TestGame";
            System.out.println("Found mac");
        }
        if (os.contains("NUX")) {
            FileFolder = System.getProperty("user.dir") + ".Launcher";
            System.out.println("Found linux");
        }

        File directory = new File(FileFolder);

        if (directory.exists()) {
            System.out.println("Found folder");
        }

        if (directory.exists() == false) {
            directory.mkdir();
            System.out.println("Could not find folder so created it");
        }

        Path source = Paths.get(System.getProperty("user.dir") + "/src/main/resources/res/MapTest.png");
        Path dest = Paths.get(System.getenv("APPDATA") + "\\" + "TestGame\\Map.png");

        Files.copy(source, dest);
    }

    /**
     * Inicializa configurações da janela
     */
    public void initFrame() {
        //cria a janela
        frame = new JFrame("ProjectFac");
        //adiciona as configurações na janela
        frame.add(this);
        //Set configurações da janela
        frame.setResizable(false);
        frame.pack();
        frame.requestFocus();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void Start() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void Stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //Inicia a janela
        Game game = new Game();
        //inicia o jogo
        game.Start();
    }

    public void tick() {
        if (WorldStatus == WorldStatusNormal) {
            //executa o metodo tick para cada Entidade
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                e.tick();
            }
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).tick();
            }

            if (enemies.size() == 0) {
                currentLevel++;
                if (currentLevel > maxLevel) {
                    currentLevel = 1;
                }

                World.restartWorld(currentLevel);
            }
        }else if(WorldStatus == WorldStatusMenu){
            menu.tick();
        }
        if (WorldStatus == WorldStatusGameOver && restartGame){
            restartGame = false;
            WorldStatus = WorldStatusNormal;
            World.restartWorld(currentLevel);
        }
    }

    public void render() {

            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                return;
            }
            //set o layer do fundo;
            Graphics g = image.getGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        if(WorldStatus == WorldStatusNormal) {
            //Executa o metodo de render do map
            world.render(g);

            //Executa o metodo render para cada Entidade
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                e.render(g);
            }
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).render(g);
            }
            ui.render(g);
        }else if(WorldStatus == WorldStatusMenu){
            menu.render(g);
        }
        //renderiza Strings na tela
//        g.setFont(new Font("Arial", Font.BOLD, 10));
//        g.setColor(Color.white);
//        g.drawString("Fps:", 10, 10);




        if (WorldStatus == WorldStatusGameOver){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(0,0,WIDTH* SCALE,HEIGHT*SCALE);
            g.setFont(new Font("arial" , Font.BOLD , 50));
            g.setColor(Color.red);
            g.drawString("You Died",WIDTH/3, HEIGHT/2);
            g.setFont(new Font("arial" , Font.BOLD , 20));
            g.setColor(Color.white);
            g.drawString("Press Enter",WIDTH/3, HEIGHT/2 + 70);
        }

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }

    /**
     * Logica de limitação de FPS pegando o tempo to Sistema e coparando
     * com a ultima atualização para detectar quando atualizar o tick o render
     */
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS:" + frames);
                frames = 0;
                timer += 1000;
            }
        }
        Stop();
    }

    //Metodo que leitura de teclado do java
    public void keyTyped(KeyEvent e) {

    }

    //Metodo que leitura de teclado do java
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shooting = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            restartGame = true;
        }
    }

    //Metodo que leitura de teclado do java
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shooting = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.shootingMouse = true;
        player.mX = (e.getX() / SCALE);
        player.mY = (e.getY() / SCALE);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
