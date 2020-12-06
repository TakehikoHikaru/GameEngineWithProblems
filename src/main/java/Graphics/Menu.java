package Graphics;

import Main.Game;

import java.awt.*;

import static Main.Enums.GameStates.WorldStatusNormal;

public class Menu {

    private static int WIDTH = Game.WIDTH;
    private static int HEIGHT = Game.HEIGHT;
    private static int SCALE = Game.SCALE;

    public static Boolean start = false;

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("Menu", WIDTH / 3, HEIGHT / 2);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.setColor(Color.white);
        g.drawString("Press Enter to start", WIDTH / 3, HEIGHT / 2 + 70);
    }

    public void tick() {
        if (start){
            Game.WorldStatus = WorldStatusNormal;
            start = false;
        }
    }
}
