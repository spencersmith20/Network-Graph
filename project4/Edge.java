// inner class Edge contains attributes on each edge of the graph, use w/ Bag

public class Edge{

  // transmission speed of each cable type
  private final double COPPER_SPEED = 230000000;
  private final double OPTICAL_SPEED = 200000000;

  private int bandwidth;      // bandwidth of the path
  private int length;         // length of the path
  private boolean isCopper;   // 0 -> optical, 1 -> copper
  private int from;
  private int to;

  // create a path based on input from network_data1.txt type files
  public Edge(int from, int to, int bandwidth, int length, String type){
    this.from = from; this.to = to;

    this.bandwidth = bandwidth; this.length = length;
    this.isCopper = type.equals("copper");
  }

  // toString method for informative graph output
  public String toString(){
    String BWStr = String.format("%.2f", (bandwidth / 1000.0));
    return "\n" + from + " to " + to + "\tLength = " + length + " m\tBandwidth = " + BWStr + " gigabits/second";
  }

  // return origin node
  public int from(){
    return from;
  }

  // return destination node
  public int to(){
    return to;
  }

  // return bandwidth;
  public int bandwidth(){
    return bandwidth;
  }

  public boolean isCopper(){
    return isCopper;
  }

  // return weight of this edge
  public double weight(){
    double speed;

    // decide which speed to use
    if(isCopper) speed = COPPER_SPEED;
    else speed = OPTICAL_SPEED;

    // weight is the time or latency, (length) / (length / time) = (time)
    return length/speed;
  }
}
