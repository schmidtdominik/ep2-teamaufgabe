import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulationRunner {
    public static void main(String[] args) {
        PathNode root = new PathNode('\0');

        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/initial-collectors-0.csv"), "UTF-8")) {
            // Benutzen Sie das Scanner-Objekt s hier um das Netzwerk zu initialisieren

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(";");

                String id = parts[0];
                int totalRequests = Integer.valueOf(parts[1]);
                int requestsSinceSplit = Integer.valueOf(parts[2]);

                root.put(id, totalRequests, requestsSinceSplit);
            }
        } catch (FileNotFoundException e) {
            // initial-collectors-0.csv wurde nicht gefunden
            System.exit(1);
        }

        root.debugPrint(0);
    }
}
