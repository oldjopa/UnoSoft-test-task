public class DSU {
    private final int[] parent;

    public DSU(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx != ry) parent[ry] = rx;
    }
}