package com.m3.csalgorithms.graph;

import java.util.ArrayList;
import java.util.List;

import com.m3.csalgorithms.Pair;

/**
 * An Edge-Weighted Adjacency representation of a Graph is an array of vertices 
 * where the value of each element (vertex) in the array is a List of Edges 
 * where either of the vertices is the index of the array.
 * 
 * @author museg
 *
 */
public class EWAGraph {
    private final int _nvertex;
    private final int _nedge;
    private final List<Edge>[] _adjmatrix;
    private final Edge[] _edges;
    private final boolean _ranksMatter;

    @SuppressWarnings("unchecked")
    public EWAGraph(int n, List<Pair<Integer, Integer>> edges) {
        _nvertex = n;
        _nedge = edges.size();
        _adjmatrix = new ArrayList[n];
        _edges = new Edge[edges.size()];
        for (Pair<Integer, Integer> pr : edges) {
            Edge e = new Edge(pr.first(), pr.second());
            if (_adjmatrix[pr.first()] == null) {
                _adjmatrix[pr.first()] = new ArrayList<Edge>();
            }
            _adjmatrix[pr.first()].add(e);
            if (_adjmatrix[pr.second()] == null) {
                _adjmatrix[pr.second()] = new ArrayList<Edge>();
            }
            _adjmatrix[pr.second()].add(e);
        }
        _ranksMatter = false;
    }

    @SuppressWarnings("unchecked")
    public EWAGraph(int n, List<Pair<Integer, Integer>> edges, List<Integer> wghts) {
        _nvertex = n;
        _nedge = edges.size();
        _adjmatrix = new ArrayList[n];
        _edges = new Edge[edges.size()];
        int jx = 0;
        for (Pair<Integer, Integer> pr : edges) {
            Edge e = null;
            if (wghts != null && !wghts.isEmpty()) {
                e = new Edge(pr.first(), pr.second());
            } else {
                e = new Edge(pr.first(), pr.second(), wghts.get(jx));
            }
            jx++;
            if (_adjmatrix[pr.first()] == null) {
                _adjmatrix[pr.first()] = new ArrayList<Edge>();
            }
            _adjmatrix[pr.first()].add(e);
            if (_adjmatrix[pr.second()] == null) {
                _adjmatrix[pr.second()] = new ArrayList<Edge>();
            }
            _adjmatrix[pr.second()].add(e);
        }
        _ranksMatter = true;
    }

    public List<Edge> edgesWith(int v) { return _adjmatrix[v]; }
}
