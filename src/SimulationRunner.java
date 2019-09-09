import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SimulationRunner {

    public static void main(String[] args) {
        int minSize = Integer.MAX_VALUE;
        int maxSize = Integer.MIN_VALUE;
        PathNode root = new PathNode('\0');

        int totalNetworkRequests = 0;
        String lastDisabled = "";
        String lastEnabled = "";

        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/initial-collectors-3.csv"), "UTF-8")) {

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

        int currentSize = root.getSize();
        minSize = Math.min(currentSize, minSize);
        maxSize = Math.max(currentSize, maxSize);

        try (Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/data/queries-3.txt"), "UTF-8")) {
            while (s.hasNextLine()) {

                char[] sensorId = s.nextLine().toCharArray();
                LeafNode col = root.getCollector(sensorId, 0, false);
                //System.out.println("requesting sensor: " + new String(sensorId));
                int status = col.access();
                totalNetworkRequests++;

                if (status == 1) {
                    lastDisabled = new String(col.id);
                    root.remove(col.id, 0);
                    //System.out.println("Disabled " + lastDisabled);
                    root.put(sensorId, 0, 0, 0);
                } else if (status == 2) {
                    lastEnabled = new String(sensorId);
                    //System.out.println("Enabled " + lastEnabled);
                    root.put(sensorId, 0, 0, 0);
                    currentSize++;
                }

                if (totalNetworkRequests >= 500000) {
                    totalNetworkRequests = 0;
                    root.removeUnused(root);
                    currentSize = root.getSize();
                }

                minSize = Math.min(currentSize, minSize);
                maxSize = Math.max(currentSize, maxSize);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        System.out.println("\n");
        System.out.println("minimal size of network: " + minSize);
        System.out.println("maximal size of network: " + maxSize);
        System.out.println("last deactivated collector: " + lastDisabled);
        System.out.println("last activated collector: " + lastEnabled);
    }
}
