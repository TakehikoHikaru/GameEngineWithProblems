package World;

import java.awt.image.BufferedImage;

import Main.Game;

public class ProceduralGenerator {

    private static int width = 100;
    private static int height = 100;

    static int[] pixels = new int[width * height];

    public static BufferedImage generator() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                if (i == width/2 && j == height/2) {
                    pixels[j * width + i] = 0xFF4800FF;
                }else if(i == (width/2)+1 && j == (height/2)+1){
                    pixels[j * width + i] = 0xFF303030;
                }
                else {
                    int a =Game.random.nextInt(1000);
                    if (a > 500){
                        pixels[j * width + i] = 0xFF4CFF00;
                    }else{
                        int b = Game.random.nextInt(2);
                        if (b == 1) {pixels[j * width + i] = 0xFFFF0000;}
                        if (b == 0) {pixels[j * width + i] = 0xFFFFD800;}
                    }
                    if (i == 0 || j== 0 || i == width - 1|| j == height - 1){
                        pixels[j * width + i] = 0xFF000000;
                    }
                    int ramd = Game.random.nextInt(1000);
                    if(ramd < 40){
                        pixels[j * width + i] = 0xFF000000;
                    }

                }
            }
        }
        BufferedImage pixelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixelImage.setRGB(0, 0, width, height, pixels, 0, width);

        return pixelImage;
    }
}