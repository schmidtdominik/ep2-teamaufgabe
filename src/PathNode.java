import java.util.ArrayList;

public class PathNode implements TrieNode {

    TrieNode[] subnodes = new TrieNode[26];
    private char value;


    public PathNode(char value) {
        this.value = value;
    }

    @Override
    public void put(char[] id, int idIndex, int totalRequests, int requestsSinceSplit) {
        int index = (int) id[idIndex] - 97;

        // add to matched subnode
        if (subnodes[index] != null) {
            subnodes[index].put(id, idIndex+1, totalRequests, requestsSinceSplit);
            return;
        }

        // add to new subnode
        TrieNode node;
        if (idIndex == 7) {
            node = new LeafNode(id, totalRequests, requestsSinceSplit);
        } else {
            node = new PathNode(id[idIndex]);
            node.put(id, idIndex+1, totalRequests, requestsSinceSplit);
        }
        subnodes[index] = node;
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
            if (subnode != null) subnode.debugPrint(depth+1);
        }
    }

    public void ping(char[] id, int idIndex, boolean diverged) {
        int index = (int) id[idIndex] - 97;
        if (!diverged && subnodes[index] != null) {
            subnodes[index].ping(id, idIndex + 1, diverged);
            return;
        }

        for (TrieNode subnode : subnodes) {
            if (subnode != null) {
                id[idIndex] = subnode.getValue();
                subnode.ping(id, idIndex+1, true);
                return;
            }
        }
    }

    @Override
    public boolean remove(String id) {
        return false; // TODO
    }
}
