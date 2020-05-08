// adapted from textboook code

public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components

    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {

            // check every unmarked vertex via dfs()
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    // depth-first search for an EdgeWeightedGraph
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;

        for (Edge e : G.adj(v)) {
            // if the edge is not copper, keep moving
            if (!e.isCopper()) continue;

            // if the edge is copper, check the vertex it moves to if its marked
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);    // if unmarked, dfs()
            }
        }
    }

    // return number of connected components
    public int count() {
        return count;
    }
}
