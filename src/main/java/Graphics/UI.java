package Graphics;

import Entities.Player;

import java.awt.*;

public class UI {

    public void render(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(10,10,50,8);
        g.setColor(Color.red);
        g.fillRect(10,10, (int)((Game.player.life/100)*50),8);
    }
}
