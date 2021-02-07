package molarivaccari;

public class Pair<T,K extends Comparable<K>> implements Comparable<Pair<T, K>> {
    T key;
    K value;
    Pair(T key, K value){
        this.key = key;
        this.value = value;
    }

    public int compareTo(Pair<T, K> p) {
        return this.value.compareTo(p.value);
    }
}
