package Insieme;

import Insieme.Astratto.InsiemeAstratto;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class InsiemeArray<T> extends InsiemeAstratto<T> {

    Object[] obj;
    int size;


    public InsiemeArray() {
        obj = new Object[10];
        size = 0;
    }

    private InsiemeArray(int size) {
        obj = new Object[size];
        this.size = size;
    }

    public InsiemeArray(T[] obj) {
        this.obj = new Object[obj.length];
        this.size = obj.length;
        for (T o : obj)
            this.add(o);
    }

    @Override
    public void add(Object obj) {
        if(contains((T)obj)) return;
        if(this.obj.length <= size) {
            size = this.obj.length;
            Object[] newObj = new Object[size + 1];
            System.arraycopy(this.obj, 0, newObj, 0, size);
            newObj[size++] = obj;
            this.obj = newObj;
        } else {
            this.obj[size++] = obj;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int cursor;
            int lastRet = -1;

            void Itr() {}

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public T next() {
                int i = cursor;
                if(i >= size) throw new NoSuchElementException();
                cursor = i + 1;
                lastRet = i;
                Object obj = InsiemeArray.this.obj[i];
                return (T) obj;
            }
        };
    }
}
