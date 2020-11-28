package Main.Listeners;

import Main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyListener {

    public static KeyListener keyListener(){
       return new KeyListener() {
            //Metodo que leitura de teclado do java
            public void keyTyped(KeyEvent e) {

            }

            //Metodo que leitura de teclado do java
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    Game.player.right = true;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    Game.player.left = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    Game.player.up = true;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    Game.player.down = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_X) {
                    Game.player.shooting = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Game.restartGame = true;
                }
            }

            //Metodo que leitura de teclado do java
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    Game.player.right = false;
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    Game.player.left = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    Game.player.up = false;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    Game.player.down = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    Game.player.shooting = false;
                }
            }
        };
    }
}
