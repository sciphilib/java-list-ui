package com.nstu.lab1.data.factories;

import java.util.Comparator;

public interface IFactory<T> {
    T create(Object... args);
    T createRandom();
    Comparator<T> getComparator();
}
