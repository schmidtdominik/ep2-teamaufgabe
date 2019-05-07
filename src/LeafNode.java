import java.util.Arrays;

public class LeafNode implements TrieNode {

    private char value;
    private char[] id;

    private int totalRequests;
    private int requestsSinceSplit;
    public boolean accessed;

    public LeafNode(char[] id, int totalRequests, int requestsSinceSplit) {
        this.id = id;
        this.value = id[id.length-1];
        this.totalRequests = totalRequests;
        this.requestsSinceSplit = requestsSinceSplit;
    }

    @Override
    public void put(char[] id, int idIndex, int totalRequests, int requestsSinceSplit) {

    }

    @Override
    public char getValue() {
        return value;
    }

    @Override
    public void debugPrint(int depth) {
        String indent = new String(new char[depth]).replace("\0", "  ");
        System.out.println(indent + value + " {" + this.totalRequests + ", " + this.requestsSinceSplit + "}");
    }

    @Override
    public void ping(char[] id, int idIndex, boolean diverged) {
        accessed = true;
        totalRequests++;
        requestsSinceSplit++;

        System.out.println("sensor: " + Arrays.toString(id));
        System.out.println("collector: " + Arrays.toString(this.id));
        System.out.println(totalRequests);
        System.out.println(requestsSinceSplit);

        /*if (totalRequests >= 1000) {
            // remove this.id
            // put id
        }*/
    }

    @Override
    public boolean remove(String id) {
        return false; // TODO
    }
}
