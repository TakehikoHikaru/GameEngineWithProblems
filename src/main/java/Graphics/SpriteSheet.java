package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage spriteSheet;

    /**
     * Metodo construtor que pega o caminho da imagem atravez do path
     */
    public SpriteSheet(String path) {
        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filtra e pega só uma parte da imagem
     */
    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }
}
