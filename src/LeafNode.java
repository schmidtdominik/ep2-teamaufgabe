import java.util.Arrays;

public class LeafNode implements TrieNode {

    private char value;
    public char[] id;

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
    public LeafNode getCollector(char[] id, int idIndex, boolean diverged) {
        return this;
    }

    public int access() {
        accessed = true;
        totalRequests++;
        requestsSinceSplit++;

        //System.out.println("collector accessed: " + new String(id) + " [totalRequests=" + totalRequests + ", requestsSinceSplit=" + requestsSinceSplit + "]");

        if (totalRequests >= 1000) {
            return 1;
        } else if (requestsSinceSplit >= 250) {
            requestsSinceSplit = 0;
            return 2;
        }

        return -1;
    }

    @Override
    public boolean remove(char[] id, int idIndex) {
        return false;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void removeUnused(PathNode root) {
        if (!accessed) {
            root.remove(id, 0);
        }
        accessed = false;
    }
}
