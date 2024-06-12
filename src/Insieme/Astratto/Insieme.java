package Insieme.Astratto;

public interface Insieme<T> extends Iterable<T>{
    int size();
    void clear();
    boolean contains(T x);
    void add(T x);
    void remove(T x);
    void addAll( Insieme<T> i );
    void removeAll( Insieme<T> i );
    void retainAll( Insieme<T> i );
}//
