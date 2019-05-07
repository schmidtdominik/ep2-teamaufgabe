public interface TrieNode {

    public char getValue();

    public void put(String id, int totalRequests, int requestsSinceSplit);

    public boolean remove(String id);

    public void debugPrint(int depth);

}
