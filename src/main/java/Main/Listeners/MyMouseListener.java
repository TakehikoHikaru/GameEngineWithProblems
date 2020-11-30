package Main.Listeners;

import Main.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener {

    public static MouseListener createMouseListener() {
        return new MouseListener() {

            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                Game.player.shootingMouse = true;
                Game.player.mX = (e.getX() / Game.SCALE);
                Game.player.mY = (e.getY() / Game.SCALE);
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        };
    }
}
