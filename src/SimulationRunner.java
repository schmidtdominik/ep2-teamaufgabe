import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulationRunner {
    public static void main(String[] args) {
        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/initial-collectors-0.csv"), "UTF-8")) {
            // Benutzen Sie das Scanner-Objekt s hier um das Netzwerk zu initialisieren
        } catch (FileNotFoundException e) {
            // initial-collectors-0.csv wurde nicht gefunden
            System.exit(1);
        }
    }
}
