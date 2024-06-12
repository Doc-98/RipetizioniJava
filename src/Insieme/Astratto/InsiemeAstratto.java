package Insieme.Astratto;

import java.util.*;

public abstract class InsiemeAstratto<T> implements Insieme<T> {


    public int size() {

        int counter = 0;

        for (Iterator<T> iterator = this.iterator(); iterator.hasNext(); iterator.next(), counter++) ;

        return counter;
    }//size


    public void clear() {

        Iterator<T> iterator = this.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }//clear


    public boolean contains(T obj) {

        for (T cursor : this)
            if (cursor.equals(obj)) return true;

        return false;
    }//contains


    public void remove(T obj) {

        Iterator<T> iterator = this.iterator();

        while (iterator.hasNext()) {
            T cursor = iterator.next();
            if (cursor.equals(obj)) {
                iterator.remove();
                return;
            }
        }
    }//remove


    public void addAll(Insieme<T> sottoInsieme) {
        for (T x : sottoInsieme) this.add(x);
    }//addAll


    public void removeAll(Insieme<T> sottoInsieme) {
        for (T x : sottoInsieme) this.remove(x);
    }//removeAll


    public void retainAll(Insieme<T> sottoInsieme) {

        Iterator<T> iterator = this.iterator();

        while (iterator.hasNext()) {
            T cursor = iterator.next();
            if (!sottoInsieme.contains(cursor)) this.remove(cursor);
        }
    }//retainAll


    public String toString() {

        StringBuilder string = new StringBuilder(100);
        Iterator<T> iterator = this.iterator();
        string.append('[');

        while (iterator.hasNext()) {
            string.append(iterator.next());
            if (iterator.hasNext()) string.append(", ");
        }

        string.append(']');

        return string.toString();
    }//toString


    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {

        if (!(obj instanceof Insieme)) return false;
        if (obj == this) return true;

        Insieme<T> insieme = (Insieme<T>) obj;

        if (size() != insieme.size()) return false;
        Iterator<T> iter1 = this.iterator(), iter2 = insieme.iterator();

        while (iter1.hasNext()) {
            if (!iter1.next().equals(iter2.next())) return false;
        }

        return true;
    }//equals


    public int hashCode() {
        final int M = 41;
        int h = 0;
        for (T cursor : this) h = h * M + cursor.hashCode();
        return h;
    }//hashCode
}//Insieme.Astratto.InsiemeAstratto
