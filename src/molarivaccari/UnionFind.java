package molarivaccari;

import java.util.ArrayList;
import java.util.List;

public class UnionFind<T extends Comparable<T>> {
    List<List<T>> Sets;

    UnionFind() {
        Sets = new ArrayList<>();
    }

    public int makeSet(T obj) {
        if(find(obj) != null)
            return -1;

        List<T> newset = new ArrayList<>();
        newset.add(obj);
        Sets.add(newset);
        return 0;
    }

    public T find(T obj) {
        for(List<T> set : Sets){
            if(set.contains(obj))
                return set.get(0);
        }
        return null;
    }

    public int union(T o1, T o2) {
        List<T> set1 = null;
        List<T> set2 = null;

        for (List<T> set : Sets) {
            T tmp = set.get(0);
            if (tmp.compareTo(o1) == 0)
                set1 = set;
            if (tmp.compareTo(o2) == 0)
                set2 = set;
        }

        if (set1 != null && set2 != null) {
            List<T> joined = new ArrayList<>(set1);
            for (T obj : set2)
                if (!joined.contains(obj))
                    joined.add(obj);

            Sets.remove(set1);
            Sets.remove(set2);
            Sets.add(joined);
        } else {
            return -1;
        }

        return 0;
    }
}