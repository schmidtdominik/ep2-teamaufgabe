public interface TrieNode {

    public char getValue();

    public void put(char[] id, int idIndex, int totalRequests, int requestsSinceSplit);

    public boolean remove(String id);

    public void debugPrint(int depth);

    public void ping(char[] id, int idIndex, boolean diverged);

}
