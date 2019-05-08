import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SimulationRunner {

    public static void main(String[] args) {
        PathNode root = new PathNode('\0');

        int totalNetworkRequests = 0;
        char[] lastDisabled = "".toCharArray();
        char[] lastEnabled = "".toCharArray();

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

                char[] sensorId = s.nextLine().toCharArray();
                char[] sensorIdCopy = Arrays.copyOf(sensorId, sensorId.length);
                LeafNode col = root.getCollector(sensorId, 0, false);


                int status = col.access();
                totalNetworkRequests++;

                if (status == 1) {
                    lastDisabled = col.id;
                    root.remove(col.id, 0);
                    lastEnabled = sensorId;
                    root.put(sensorId, 0, 0, 0);
                } else if (status == 2) {
                    lastEnabled = sensorId;
                    root.put(sensorId, 0, 0, 0);
                }

            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        System.out.println(Arrays.toString(lastEnabled));
        System.out.println(Arrays.toString(lastDisabled));
    }
}
