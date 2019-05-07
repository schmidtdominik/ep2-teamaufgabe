import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulationRunner {

    public static void main(String[] args) {
        PathNode root = new PathNode('\0');

        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/initial-collectors-0.csv"), "UTF-8")) {

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(";");

                char[] id = parts[0].toCharArray();
                int totalRequests = Integer.valueOf(parts[1]);
                int requestsSinceSplit = Integer.valueOf(parts[2]);

                root.put(id, 0, totalRequests, requestsSinceSplit);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/queries-0.txt"), "UTF-8")) {
            while (s.hasNextLine()) {
                char[] id = s.nextLine().toCharArray();
                root.ping(id, 0, false);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        //root.debugPrint(0);
    }
}
