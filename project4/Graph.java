import java.util.Iterator;
import java.lang.Math;

public class Graph{

  private int v;                    // number of vertices
  private int E;                    // number of edges
  public Bag<Edge>[] adj;           // each index in the graph -> Bag of vertices it paths to

  // initialize a graph with v vertices
  @SuppressWarnings("unchecked")
  public Graph(int v){
    this.v = v;

    // each index of edges is a new Bag of Edges
    adj = (Bag<Edge>[]) new Bag[v];
    for (int i = 0; i < v; i++)
      adj[i] = new Bag<Edge>();
  }

  // get an entry in the adjacency list
  public Bag<Edge> adj(int i){
    return adj[i];
  }

  // add an edge to the graph. adds to the Bags attached to both vertices
  public void add(int v1, int v2, String type, int bandwidth, int length){
      adj[v1].add(new Edge(v1, v2, bandwidth, length, type));
      adj[v2].add(new Edge(v2, v1, bandwidth, length, type));
      E++;  // increment edge count
  }

  // add an existent edge to the graph
  public void add(int i, Edge edge){
    adj[i].add(edge);
  }

  // remove an edge from the graph
  public Graph remove(int k){
    Graph g = new Graph(v-1);

    // unless the path includes index being avoided, copy over each Edge, maintaining Bags
    for (int i = 0; i < v; i++){
      if (i == k) continue;

      Iterator<Edge> edges = this.adj(i).iterator();

      // if not dealing with the vertex to be removed, add to the new graph
      while (edges.hasNext()){
        Edge edge = edges.next();
        if ((edge.from() != k) && (edge.to() != k)){

          // change index based on where are in the array
          if (i < k) g.add(i, edge);
          else g.add(i-1, edge);
        }
      }
    }

    // return the edited graph, maintaining the original
    return g;
  }

  // return number of vertices
  public int V(){
    return v;
  }

  // toString method if helpful for grading
  public String toString(){
    String str = "\nv = " + v;
    for (int i = 0; i < v; i++){
      str += adj[i].toString();
    }
    return str;
  }
}
