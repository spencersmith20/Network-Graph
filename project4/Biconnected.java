// based off of textbook code
public class Biconnected {
    private int[] low;
    private int[] pre;
    private int cnt;
    private boolean[] articulation;

    // constructor for using dfs for biconnected check
    // int k added for the case of removing a vertex THEN checking for biconnectedness
    // k is the vertex that was removed from the graph, so must be careful not to access that index
    public Biconnected(Graph G, int k) {

        low = new int[G.V()]; pre = new int[G.V()];
        articulation = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++){
            low[v] = -1; pre[v] = -1;
        }

        // check for single articlation points
        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1) dfs(G, v, v, k);
    }

    private void dfs(Graph G, int u, int v, int k) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];

        for (Edge e : G.adj(v)) {

          // get child index, be careful of removed edges by using k
          int w; if (e.to() > k) w = e.to() - (k+1);
          else w = e.to();
          if (v > k) v -= (k+1);

          if (pre[w] == -1) {

              // run dfs on children
              children++;
              dfs(G, v, w, k);

              // convert back to numbers not used for recursion
              if (e.to() > k) w = e.to() - (k+1);
              else w = e.to();
              if (v > k) v -= (k+1);

              // update low number
              low[v] = Math.min(low[v], low[w]);

              // non-root of DFS is an articulation point if low[w] >= pre[v]
              if (low[w] >= pre[v] && u != v){
                  articulation[v] = true;
                  return;
                }
          }

          // update low number - ignore reverse of edge leading to v
          else if (w != u)
              low[v] = Math.min(low[v], pre[w]);
      }

      // root of DFS is an articulation point if it has more than 1 child
      if (u == v && children > 1)
          articulation[v] = true;
  }

  // check if the graph has any articualtion points
  public boolean hasArticulation(Graph g){
    for (int i = 0; i < g.V(); i++)
      if (articulation[i]) return true;
    return false;
  }
}
