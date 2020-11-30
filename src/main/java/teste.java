import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class teste {


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
}