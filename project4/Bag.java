// somewhat adapted from textbook code
import java.util.Iterator;

public class Bag<T> implements Iterable<T>{

  private Node first;

  // inner Node class. for NetworkAnalysis, T is of type Edge
  private class Node{
    T item;
    Node next;
  }

  // add an item to the bag. since order is irrelevant, make new item first ref
  public void add(T item){
    Node oldFirst = first;
    first = new Node();
    first.item = item;
    first.next = oldFirst;
  }

  public Iterator<T> iterator(){
    return new ListIterator();
  }

  private class ListIterator implements Iterator<T>{
    Node current = first;

    public boolean hasNext(){
      return current != null;
    }

    public void remove(){}

    public T next(){
      T item = current.item;
      current = current.next;
      return item;
    }
  }

  public String toString(){
    String str = "";
    Iterator<T> li = this.iterator();
    while (li.hasNext())
      str += li.next();
    return str;
  }
}
