package molarivaccari;

import movida.commons.Person;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Sorting {
    static boolean isOrdered(ArrayOrdinato A) {
        for (int i = 1; i < A.size(); i++)
            if (A.getNode(i).compareTo(A.getNode(i - 1), A.order) < 0)
                return false;

        return true;
    }

    static public void selectionSort(ArrayOrdinato A) {
        if (isOrdered(A))
            return;

        Node current;
        Node min = A.getNode(0);
        int min_i = 0;
        for (int i = 0; i < A.size(); i++) {
            for (int j = i; j < A.size(); j++) {
                if (j == i) {
                    min = A.getNode(j);
                    min_i = j;
                    continue;
                }
                current = A.getNode(j);
                if (current.compareTo(min, A.order) < 0) {
                    min = current;
                    min_i = j;
                }
            }
            A.swapNodes(i, min_i);
            min_i = -1;
            min = null;
            if (isOrdered(A))
                return;
        }
    }

    public static void heapSort(ArrayOrdinato A) {
        if (isOrdered(A))
            return;

        int n = A.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(A, n, i);

        for (int i = A.size() - 1; i > 0; i--) {
            A.swapNodes(0, i);
            heapify(A, i, 0);
        }
    }

    private static void heapify(ArrayOrdinato A, int n, int i) {
        int max = i;
        int left = max * 2 + 1;
        int right = max * 2 + 2;

        if (left < n && A.getNode(left).compareTo(A.getNode(max), A.order) > 0)
            max = left;

        if (right < n && A.getNode(right).compareTo(A.getNode(max), A.order) > 0)
            max = right;

        if (max != i) {
            A.swapNodes(i, max);
            heapify(A, n, max);
        }
    }

    //ArrayList<Pair<Person, Integer>>
/*
    static boolean isOrdered(ArrayList<Pair<Person, Integer>> A) {
        for (int i = 1; i < A.size(); i++)
            if (A.get(i).compareTo(A.get(i - 1)) < 0)
                return false;

        return true;
    }

    static public void selectionSort(ArrayList<Pair<Person, Integer>> A) {
        if (isOrdered(A))
            return;

        Pair<Person, Integer> current;
        Pair<Person, Integer> min = A.get(0);
        int min_i = 0;
        for (int i = 0; i < A.size(); i++) {
            for (int j = i; j < A.size(); j++) {
                if (j == i) {
                    min = A.get(j);
                    min_i = j;
                    continue;
                }
                current = A.get(j);
                if (current.compareTo(min) < 0) {
                    min = current;
                    min_i = j;
                }
            }
            Pair<Person, Integer> tmp = A.get(i);
            A.set(i, A.get(min_i));
            A.set(min_i, tmp);
            min_i = -1;
            min = null;
            if (isOrdered(A))
                return;
        }
    }

    public static void heapSort(ArrayList<Pair<Person, Integer>> A) {
        if (isOrdered(A))
            return;

        int n = A.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(A, n, i);

        for (int i = A.size() - 1; i > 0; i--) {
            Pair<Person, Integer> tmp = A.get(0);
            A.set(0, A.get(i));
            A.set(i, tmp);
            heapify(A, i, 0);
        }
    }

    private static void heapify(ArrayList<Pair<Person, Integer>> A, int n, int i) {
        int max = i;
        int left = max * 2 + 1;
        int right = max * 2 + 2;

        if (left < n && A.get(left).compareTo(A.get(max)) > 0)
            max = left;

        if (right < n && A.get(right).compareTo(A.get(max)) > 0)
            max = right;

        if (max != i) {
            Pair<Person, Integer> tmp = A.get(i);
            A.set(i, A.get(max));
            A.set(max, tmp);
            heapify(A, n, max);
        }
    }
*/
    static <T extends Comparable<T>> boolean isOrdered(ArrayList<T> A) {
        for (int i = 1; i < A.size(); i++)
            if (A.get(i).compareTo(A.get(i - 1)) < 0)
                return false;

        return true;
    }

    static public <T extends Comparable<T>> void selectionSort(ArrayList<T> A) {
        if (isOrdered(A))
            return;

        T current;
        T min = A.get(0);
        int min_i = 0;
        for (int i = 0; i < A.size(); i++) {
            for (int j = i; j < A.size(); j++) {
                if (j == i) {
                    min = A.get(j);
                    min_i = j;
                    continue;
                }
                current = A.get(j);
                if (current.compareTo(min) < 0) {
                    min = current;
                    min_i = j;
                }
            }
            T tmp = A.get(i);
            A.set(i, A.get(min_i));
            A.set(min_i, tmp);
            min_i = -1;
            min = null;
            if (isOrdered(A))
                return;
        }
    }

    public static <T extends Comparable<T>> void heapSort(ArrayList<T> A) {
        if (isOrdered(A))
            return;

        int n = A.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(A, n, i);

        for (int i = A.size() - 1; i > 0; i--) {
            T tmp = A.get(0);
            A.set(0, A.get(i));
            A.set(i, tmp);
            heapify(A, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(ArrayList<T> A, int n, int i) {
        int max = i;
        int left = max * 2 + 1;
        int right = max * 2 + 2;

        if (left < n && A.get(left).compareTo(A.get(max)) > 0)
            max = left;

        if (right < n && A.get(right).compareTo(A.get(max)) > 0)
            max = right;

        if (max != i) {
            T tmp = A.get(i);
            A.set(i, A.get(max));
            A.set(max, tmp);
            heapify(A, n, max);
        }
    }
}