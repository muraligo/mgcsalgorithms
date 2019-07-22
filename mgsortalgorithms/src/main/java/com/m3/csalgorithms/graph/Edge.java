package com.m3.csalgorithms.graph;

import com.m3.csalgorithms.Pair;

public class Edge extends Pair<Integer, Integer> implements Comparable<Edge> {
    private final int _weight;

    public Edge(Integer frst, Integer scnd) {
        super(frst, scnd);
        _weight = 0;
    }

    public Edge(Integer frst, Integer scnd, Integer wght) {
        super (frst, scnd);
        _weight = wght;
    }

    public int weight() { return _weight; }
    public Integer other(Integer v) {
        if (!_first.equals(v) && !_second.equals(v)) {
            throw new IllegalArgumentException("[" + v + "] is not a vertex in this edge");
        }
        return (_first.equals(v)) ? _second : _first;
    }

    @Override
    public int compareTo(Edge other) {
    	return (_weight - other._weight);
    }

    @Override
    public String toString() {
        return ("(from[" + _first + "] to [" + _second + "] weight[" + _weight + "]");
    }

}
