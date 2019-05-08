public interface TrieNode {

    public char getValue();

    public void put(char[] id, int idIndex, int totalRequests, int requestsSinceSplit);

    public boolean remove(char[] id, int idIndex);

    public void debugPrint(int depth);

    public LeafNode getCollector(char[] id, int idIndex, boolean diverged);

}
