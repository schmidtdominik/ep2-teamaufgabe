public class LeafNode implements TrieNode {

    private char value;

    private int totalRequests;
    private int requestsSinceSplit;
    public boolean accessed;

    public LeafNode(char value, int totalRequests, int requestsSinceSplit) {
        this.value = value;
        this.totalRequests = totalRequests;
        this.requestsSinceSplit = requestsSinceSplit;
    }

    @Override
    public void put(String id, int totalRequests, int requestsSinceSplit) {

    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public char getValue() {
        return value;
    }

    @Override
    public void debugPrint(int depth) {
        String indent = new String(new char[depth]).replace("\0", "  ");
        System.out.println(indent + value);
    }
}
