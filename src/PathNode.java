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

    public LeafNode getCollector(char[] id, int idIndex, boolean diverged) {
        int index = (int) id[idIndex] - 97;
        if (!diverged && subnodes[index] != null) {
            return subnodes[index].getCollector(id, idIndex + 1, diverged);
        }

        for (TrieNode subnode : subnodes) {
            if (subnode != null) {
                // id[idIndex] = subnode.getValue(); char arrac nicht ändern!
                return subnode.getCollector(id, idIndex+1, true);
            }
        }

        System.out.println("oje gibt ein problem hier kann nicht erreicht werden");
        return null; // sollte nicht erreicht werden können
    }

    @Override
    public boolean remove(char[] id, int idIndex) {
        int index = (int) id[idIndex] - 97;
        boolean preserveBranch = subnodes[index].remove(id, idIndex+1);

        if (!preserveBranch) {
            subnodes[index] = null;
        }

        for (TrieNode subnode : subnodes) {
            if (subnode != null) {
                preserveBranch = true;
                break;
            }
        }

        return preserveBranch;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (TrieNode subnode : subnodes) {
            if (subnode != null) {
                size += subnode.getSize();
            }
        }
        return size;
    }

    @Override
    public void removeUnused(PathNode root) {
        for (TrieNode subnode : subnodes) {
            if (subnode != null) {
                subnode.removeUnused(root);
            }
        }
    }
}
