package World;

public class Camera {

    public static int x = 0;
    public static int y = 0;

    public static int clamp(int atual, int Max, int Min) {
        if (atual < Min) {
            atual = Min;
        }
        if (atual > Max) {
            atual = Max;
        }
        return atual;
    }

}
