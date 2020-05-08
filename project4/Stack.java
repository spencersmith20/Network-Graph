import java.util.Iterator;
public class Stack<T> implements Iterable<T>{
  Node first;
  int N;

  // Node inner class
  private class Node{
    T item;
    Node next;
  }

  // push an item to the stack
  public void push(T t){
    Node oldFirst = first;
    first = new Node(); N++;
    first.item = t; first.next = oldFirst;
  }

  // pop the top item from the stack
  public T pop(){
    T item = first.item;
    first = first.next;
    N--;
    return item;
  }

  // see if the stack is empty
  public boolean isEmpty(){
      return N == 0;
  }

  public Iterator<T> iterator(){
    return new ListIterator();
  }

  private class ListIterator implements Iterator<T>{

    private Node current = first;

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
}
