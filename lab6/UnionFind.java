public class UnionFind {

    // TODO - Add instance variables?
    public Int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        parent = new int[n];
        for (int i = 0; i < n; i += 1){
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex >= length.unionFind || vertex < 0) {
            throw new IllegalArgumentException("vertex is not a valid index")
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        // we can use the root to restore the -1 * size, instead of using an array size[].
        return -1 * parent(find(v1));
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        if !connected(v1, v2) {
            if (sizeOF(v1) > sizeOf(v2)) {
                parent[find(v1)] -= sizeOf(v2);
                parent[v2] = find(v1);
            } else {
                parent[find(v2) -= sizeOf(v1)];
                parent[v1] = find(v2);
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        validate(vertex);

        // find the root of the vertex
        int root = vertex;
        while (parent(root) >= 0) {
            root = parent(root);
        }


        // path-compression
        int tmp;
        while (parent(vertex) != root) {
            tmp = parent(vertex);
            parent[vertxe] = root;
            vertex = tmp;
        }

        return root;

}
