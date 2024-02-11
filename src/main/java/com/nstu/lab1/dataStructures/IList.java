package com.nstu.lab1.dataStructures;

import java.util.Comparator;

public interface IList<T> {
    void add(T data);
    void add (T data, int index);
    void remove(int index);
    void forEach(Action<T> a);
    void sort(Comparator<T> comp);
    T get(int index);
    int size();
}
