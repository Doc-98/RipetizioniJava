package Insieme;

import Insieme.Astratto.InsiemeAstratto;

import java.util.Iterator;

public class InsiemeConcatenato<T> extends InsiemeAstratto<T> {

    private int size = 0;

    private Node<T> first;

    private Node<T> last;


    @Override
    public void add(T obj) {
        linkLast(obj);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private void linkFirst(T obj) {
        final Node<T> f = first;
        final Node<T> newNode = new Node<>(null, obj, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    private static class Node<T> {
        T item;
        InsiemeConcatenato.Node<T> next;
        InsiemeConcatenato.Node<T> prev;

        Node(InsiemeConcatenato.Node<T> prev, T element, InsiemeConcatenato.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
