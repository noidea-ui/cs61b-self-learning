import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    // TODO: Instance variables

    private int[] items;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        items = new int[N];
        for(int i=0;i<N;i++){
            items[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if(v >= items.length){
            throw new IllegalArgumentException("Out of range");
        }

        while(v>=0){
            v=parent(v);
        }
        v= Math.abs(v);
        return v;

    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if(v >= items.length){
            throw new IllegalArgumentException("Out of range");
        }
        return items[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int boss1 = find(v1);
        int boss2 = find(v2);

        return boss1 == boss2;


    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v >= items.length){
            throw new IllegalArgumentException("Out of range");
        }
        List<Integer> path = new ArrayList<>();
        int root = v;
        while(parent(root)>=0){
            path.add(root);
            root = parent(root);
        }
        for(int i: path){
            items[i] = root;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE

        if(v1 == v2){
            return ;
        }

        int root1 = find(v1);
        int root2 = find(v2);

        int size1 = Math.abs(items[root1]);
        int size2 = Math.abs(items[root2]);

        if(size1>size2){
            items[root2] = root1;
            items[root1]-=size2;
        }
        else{
            items[root1] = root2;
            items[root2]-= size1;
        }
    }

}
