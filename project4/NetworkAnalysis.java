import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;

public class NetworkAnalysis{

  public static void main(String[] args) throws FileNotFoundException{

    // variables used
    int v1, v2, bandwidth, length, n; String type; String[] dat;

    // read file
    File f = new File("network_data1.txt");
    Scanner scanner = new Scanner(f);

    // get number of vertices
    int v = Integer.valueOf(scanner.nextLine());

    Graph g = new Graph(v);

    // get each vertex in input file
    while (scanner.hasNextLine()){
      dat = scanner.nextLine().split(" ", 5);

      v1 = Integer.parseInt(dat[0]);
      v2 = Integer.parseInt(dat[1]);
      type = dat[2];
      bandwidth = Integer.parseInt(dat[3]);
      length = Integer.parseInt(dat[4]);

      // add each to the graph
      g.add(v1, v2, type, bandwidth, length);
    }

    // continue asking for menu responses until case 4 sets done to true
    scanner = new Scanner(System.in); boolean done = false;
    while(!done){

      System.out.println("\nEnter a key to choose what to do:\n"
      + "1. Find lowest latency path\n"
      + "2. Determine if copper-only connected\n"
      + "3. Determine whether graph would remain connected if any two vertices fail\n"
      + "4. Quit");

      try{
        n = Integer.valueOf(scanner.nextLine());  // if a non-integer is entered, will quit program
      }
      catch(NumberFormatException e){
        System.out.println("You must enter an integer to pick a menu item");
        n = 5;
      }

      switch(n){

        case 1: // get lowest latency path
          System.out.print("\nEnter the two vertices you wish to find the "
            + "lowest latency path between (space separated):");
          dat = scanner.nextLine().split(" ", 2);

          // get vertices for path
          v1 = Integer.valueOf(dat[0]); v2 = Integer.valueOf(dat[1]);

          // instantiate textbook-modified Dijkstra's class
          DijkstraSP dsp = new DijkstraSP(g, v1);

          // print the path to v2 from v1 source vertex & bandwidth/latency info
          dsp.pathTo(v2);

          break;

        case 2: // copper only connected?
          CC cc = new CC(g);
          boolean c = (cc.count() == 1);

          // output result to user
          if (c) System.out.println("\nThe graph is copper only connected!");
          else System.out.println("\nThe graph is not copper only connected!");
          break;

        case 3: // determine if graph would be connected if two points fail

          boolean a = false;
          // first check graph for articulation points
          Biconnected bc = new Biconnected(g, -1);
          if (bc.hasArticulation(g)) a = true;

          // if no single articulation points found, remove vertex from graph,
          // then check that graph for articulation points
          Graph temp = g;

          for (int i = 0; i < g.V(); i++){

            // remove vertex i from the Graph g
            temp = g.remove(i);

            // check this temp graph for biconnectedness
            bc = new Biconnected(temp, i);
            if(bc.hasArticulation(temp)){ a = true; break;}

            // if nothing found, keep moving through graph, removing each vertex
            // and then checking for BC
          }

          // output result to user
          if (a) System.out.println("\nThe graph cannot survive two vertex failures!");
          else System.out.println("\nThe graph can survive two vertex failures!");
          break;

        case 4: // quit the program
          System.out.println("goodbye!"); done = true;
          break;

        default:  // catch bad data
          System.out.println("Please enter a number between 1 and 4");
          break;
      }
    }
    scanner.close();
  }
}
