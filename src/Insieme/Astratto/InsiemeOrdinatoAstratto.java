package Insieme.Astratto;

import java.util.*;

public abstract class InsiemeOrdinatoAstratto<T extends Comparable<? super T>>
        extends InsiemeAstratto<T>
        implements InsiemeOrdinato<T> {

    public abstract InsiemeOrdinatoAstratto<T> create();

    public T first() {
        return this.iterator().next();
    }//first

    public T last() {

        Iterator<T> iterator = this.iterator();
        T obj = null;

        while (iterator.hasNext()) {
            obj = iterator.next();
            if (!iterator.hasNext()) break;
        }

        if (obj == null) throw new NoSuchElementException();

        return obj;
    }//last

    public InsiemeOrdinatoAstratto<T> headSet(T obj) {

        InsiemeOrdinatoAstratto<T> sottoInsieme = create();

        for (T cursore : this) {
            if (cursore.compareTo(obj) < 0) sottoInsieme.add(cursore);
            else break;
        }

        return sottoInsieme;
    }//headSet


    public InsiemeOrdinatoAstratto<T> tailSet(T obj) {

        InsiemeOrdinatoAstratto<T> sottoInsieme = create();

        for (T cursore : this)
            if (cursore.compareTo(obj) >= 0) sottoInsieme.add(cursore);

        return sottoInsieme;
    }//tailSet
}//InsiemeOrdinatoAstratto
