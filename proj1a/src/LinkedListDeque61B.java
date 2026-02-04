import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{

    public class Node{
        public T item;
        public Node prev;
        public Node next;

        public Node(){
            item = null;
        }

        public Node(T item,Node prev,Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    public Node sentinel;
    public int size;

    public LinkedListDeque61B(){
        sentinel = new Node(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

    }

    @Override
    public void addFirst(T x) {
        Node tem = new Node(x,sentinel,sentinel.next);
        sentinel.next = tem;
        tem.next.prev = tem;
        size ++;
    }

    @Override
    public void addLast(T x) {
        Node tem = new Node(x,sentinel.prev,sentinel);
        sentinel.prev = tem;
        tem.prev.next = tem;
        size ++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node cur = sentinel.next;
        while(cur != sentinel){
            returnList.add(cur.item);
            cur= cur.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T result = sentinel.next.item;
        if(size >0) {
            size--;
        }
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return result;
    }

    @Override
    public T removeLast() {
        T result = sentinel.prev.item;
        if(size > 0) {
            size--;
        }
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return result;

    }

    @Override
    public T get(int index) {
        if(index<0 || index >= size){
        return null;}
        else{
            Node cur = sentinel.next;
            while(index >0){
                index --;
                cur = cur.next;
            }
            return cur.item;
        }

    }

    @Override
    public T getRecursive(int index) {
        if(index <0 || index >= size){
            return null;
        }
        return getRecursiveHelper(sentinel.next,index);
    }
    private T getRecursiveHelper(Node cur,int index){
        if (index == 0){
            return cur.item;
        }
        else{
            return getRecursiveHelper(cur.next,index - 1);
        }
    }

}
