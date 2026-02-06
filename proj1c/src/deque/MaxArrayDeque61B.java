package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    public Comparator<T> myComparator;

    public MaxArrayDeque61B(Comparator<T> c){
        myComparator = c;
    }

    public T max(){
        int current = front;
        T item = items[current];
        for( T i:items){
            if(i == null) break;
            if(this.myComparator.compare(item,i)<0){
                item = i;
            }
        }
        return item;
    }

    public T max(Comparator<T> c){
        int current = front;
        T item = items[current];
        for(T i:items){
            if(c.compare(item,i)<0){
                item = i;
            }
        }
        return item;
    }
}
