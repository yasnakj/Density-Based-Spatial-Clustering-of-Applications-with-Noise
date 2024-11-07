/**
 * Yasna Karimi
 * 300312772
 */

 import java.util.LinkedList;

public class Stack<T> {
    private LinkedList<T> list = new LinkedList<>();

    // Method to push an item onto the stack
    public void push(T item) { list.addFirst(item); }

    // Method to pop an item from the stack
    public T pop() { return list.removeFirst(); }

    // Method to check if the stack is empty
    public boolean isEmpty() { return list.isEmpty(); }
}
