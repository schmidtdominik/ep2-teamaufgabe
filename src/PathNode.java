import java.util.ArrayList;

public class PathNode implements TrieNode {

    ArrayList<TrieNode> subnodes = new ArrayList<>();
    private char value;

    public PathNode(char value) {
        this.value = value;
    }

    @Override
    public void put(String id, int totalRequests, int requestsSinceSplit) {
        char nextValue = id.charAt(0);

        // look for matching subnode
        for (TrieNode subnode : subnodes) {
            if (subnode.getValue() == nextValue) {
                subnode.put(id.substring(1), totalRequests, requestsSinceSplit);
                return;
            }
        }

        // create new node
        TrieNode node;
        if (id.length() == 1) {
            node = new LeafNode(id.charAt(0), totalRequests, requestsSinceSplit);
        } else {
            node = new PathNode(id.charAt(0));
            node.put(id.substring(1), totalRequests, requestsSinceSplit);
        }
        subnodes.add(node);
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
        for (TrieNode subnode : subnodes) {
            subnode.debugPrint(depth+1);
        }
    }
}
