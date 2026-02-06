import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {

    public int front;
    public int last;
    public int capacity;
    public T[] items ;
    public int size;

    public ArrayDeque61B(){
        front =0;
        last = 1;
        capacity = 8;
        size = 0;
        items = (T[]) new Object [capacity];
    }

    public void resize(){
        T[] array = (T[]) new Object [capacity*2];
         System.arraycopy(items,0,array,0, last);
         System.arraycopy(items,front,array,front+capacity,capacity-front);
         front = front+capacity;
         capacity*=2;
         items = array;
    }


    @Override
    public void addFirst(T x) {
        size+=1;
        if(size > capacity){
            resize();
        }
        front = Math.floorMod(front-1,capacity);
        items[front] = x;

    }

    @Override
    public void addLast(T x) {
        size+=1;
        if(size>capacity){
            resize();
        }
        items[Math.floorMod(last-1,capacity)] =x;
        last = Math.floorMod(last+1,capacity);
    }

    @Override
    public List<T> toList() {
       int cur = front;
       List<T> returnList = new ArrayList<>();
       for(int i=0;i<size;i++){
           returnList.add(items[Math.floorMod(front+i,capacity)]);
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
        if(size ==0){
            return null;
        }
        else{
            size --;
            T item = items[front];
            front = Math.floorMod(front+1,capacity);
            return item;
        }


    }

    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }
        else{
            size --;
            last = Math.floorMod(last -1,capacity);
            T item = items[Math.floorMod(last-1,capacity)];
            return item;
        }
    }

    @Override
    public T get(int index) {
        int cur = front;
        cur = Math.floorMod(front+index,capacity);
        return items[cur];
    }

    @Override
    public T getRecursive(int index) {
return get(index);
    }

}
