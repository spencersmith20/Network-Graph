// textbook code

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    // Computes a shortest-paths tree from the source vertex {@code s} to every other
    // vertex in the edge-weighted digraph {@code G}.
    public DijkstraSP(Graph G, int s) {

        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e);
        }
    }

    // relax edge e and update pq if changed
    private void relax(Edge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {

            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

            if (pq.contains(w))
              pq.decreaseKey(w, distTo[w]);
            else
              pq.insert(w, distTo[w]);
        }
    }

    public boolean hasPathTo(int v) {
      return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // print the path to the vertex v from the source vertex
    public void pathTo(int v) {
      if (!hasPathTo(v)) return;
      Stack<Edge> path = new Stack<Edge>();

      // initialize return values
      double lat = 0; int minBW = edgeTo[v].bandwidth();

      for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {

        // latenct & bandwidth checks
        lat += e.weight();
        if (e.bandwidth() < minBW) minBW = e.bandwidth();

        // push the edge to the stack
        path.push(e);
      }

      // print path of vertices
      for (Edge e : path){
        System.out.print(e.toString());
      }

      // print results
      String latStr = String.format("%.2f", lat * Math.pow(10,9));
      String BWStr = String.format("%.2f", minBW / 1000.0);
      System.out.println("\nMinimum Bandwidth: " + BWStr
            + " gigabits/second \nTotal Latency: " +  latStr + " nanoseconds");

  }
}
