package com.m3.csalgorithms;

public class Pair<S, T> {
    private final S _first;
    private final T _second;

    public Pair(S frst, T scnd) {
        _first = frst;
        _second = scnd;
    }

    public S first() { return _first; }
    public T second() { return _second; }
}
