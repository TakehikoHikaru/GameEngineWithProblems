package Graphics;

import Entities.Player;

import java.awt.*;

public class UI {

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(4, 10, 50, 4);
        g.setColor(Color.red);
        g.fillRect(4, 10, (int) ((Game.player.life / 100) * 50), 4);
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.BOLD,9));
        g.drawString((int) Game.player.life + "/100", 4, 8);

        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.BOLD,10));
        g.drawString("Munição: "+Game.player.ammo,Game.WIDTH - 80,10);

    }
}
