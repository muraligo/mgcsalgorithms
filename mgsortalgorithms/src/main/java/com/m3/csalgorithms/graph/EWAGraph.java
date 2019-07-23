package com.m3.csalgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        _edges = new Edge[_nedge];
        int ix = 0;
        for (Pair<Integer, Integer> pr : edges) {
            Edge e = new Edge(pr.first(), pr.second());
            _edges[ix++] = e;
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
    public boolean doRanksMatter() { return _ranksMatter; }

    /*
     * Given a connected and undirected graph, a spanning tree of that graph 
     * is a subgraph that is a tree and connects all the vertices together. 
     * A single graph can have many different spanning trees. A minimum 
     * spanning tree (MST) or minimum weight spanning tree for a weighted, 
     * connected and undirected graph is a spanning tree with weight less than 
     * or equal to the weight of every other spanning tree. The weight of a 
     * spanning tree is the sum of weights given to each edge of the spanning tree.
     * A minimum spanning tree has (V – 1) edges where V is the number of vertices 
     * in the given graph.
     */

    /* Below are the steps for finding MST using Kruskal’s algorithm
     * 1. Sort all the edges in non-decreasing order of their weight.
     * 2. Pick the smallest edge. Check if it forms a cycle with the 
     *    spanning tree formed so far. If cycle is not formed, include this edge. 
     *    Else, discard it. (This step uses Union-Find algorithm to detect cycles.)
     * 3. Repeat step#2 until there are (V-1) edges in the spanning tree.
     * This is a Greedy Algorithm in that, it picks the smallest weight edge 
     * that does not cause a cycle in the MST constructed so far.
     * Time Complexity: O(ElogE) or O(ElogV). Sorting of edges takes O(ELogE) time. 
     * After sorting, we iterate through all edges and apply find-union algorithm. 
     * The find and union operations can take atmost O(LogV) time. 
     * So overall complexity is O(ELogE + ELogV) time. 
     * The value of E can be at most O(V2), so O(LogV) are O(LogE) same. 
     * Therefore, overall time complexity is O(ElogE) or O(ElogV)
     */
    public Edge[] minimumSpanningTreeKruskal(int start) {
        final Edge[] result = new Edge[_nvertex]; // This will store the resultant MST
        int e = 0;  // An index variable, used for result[] 
        int i = 0;  // An index variable, used for picking next from sorted edges 
  
        // Step 1:  Sort all the edges in non-decreasing order of their weight. If we are
        // not allowed to change the given graph, we can create a copy of array of edges 
        Arrays.sort(_edges); 

        EdgeSubset[] subsets = createInitializeSubsets(_nvertex);

        int stopwhen = _nvertex - 1; // there should be no more edges than 1 less than the number of vertices
        Queue<Integer> roots = new LinkedList<Integer>();
        while (e < stopwhen) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = _edges[i++];
            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (!isCycle(subsets, next_edge, roots)) {
                result[e++] = next_edge;
                union(subsets, roots.poll(), roots.poll());
            } else { // Else discard the next_edge and clear roots
                roots.poll();
                roots.poll();
            }
        }
        // print the contents of result[] to display 
        // the built MST 
//        System.out.println("Following are the edges in " +  
//                                     "the constructed MST"); 
//        for (i = 0; i < e; ++i) 
//            System.out.println(result[i]);

        clearSubsets(subsets);
        return result;
    }

    public boolean detectCyclesUnionFind(EdgeSubset[] subsets) {
        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        Queue<Integer> roots = new LinkedList<Integer>();
        for (int e = 0; e < _edges.length; ++e) {
            if (isCycle(subsets, _edges[e], roots)) {
                System.out.println("Cycle found");
                return true;
            }
            System.out.println("No cycles so union");
            union(subsets, roots.poll(), roots.poll());
        }
        return false;
    }

    // Find subset of both vertices of the edge, 
    // if both subsets are same, then there is cycle in graph.
    private boolean isCycle(EdgeSubset[] subsets, Edge e, Queue<Integer> roots) {
        System.out.print("Checking edge [" + e + "] ");
        int x = find(subsets, e.first());
        System.out.print("x=[" + x + "] ");
        int y = find(subsets, e.second());
        System.out.print("y=[" + y + "] ");
        roots.offer(x);
        roots.offer(y);
        return (x == y);
    }

    // A utility function to find set of a vertex v
    // (uses path compression technique where we are looking for the root) 
    private int find(EdgeSubset subsets[], int v) { 
        // find root and make root as parent of v (path compression) 
        if (subsets[v].parent() != v) {
            System.out.print("to vertex [" + subsets[v].parent() + "] ");
            subsets[v].parent(find(subsets, subsets[v].parent()));
        }
        System.out.print("Parent found ");
        return subsets[v].parent(); 
    } 
    
    // A function that does union of two sets of x and y
    // (uses union by rank)
    private void union(EdgeSubset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
  
        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank() < subsets[yroot].rank())
            subsets[xroot].parent(yroot);
        else if (subsets[xroot].rank() > subsets[yroot].rank())
            subsets[yroot].parent(xroot);
        // If ranks are same, then make one as root and increment its rank by one
        else {
            subsets[yroot].parent(xroot);
            if (_ranksMatter)
                subsets[xroot].rankInc();
            System.out.println("    Setting parent of xsubset=[" + xroot + "] to ysubset=[" + yroot + "]");
        }
    }

    private EdgeSubset[] createInitializeSubsets(int numvertices) {
        EdgeSubset[] subsets = new EdgeSubset[numvertices];
        // Initialize all subsets as single element sets
        for (int v = 0; v < numvertices; ++v)  {
            subsets[v] = new EdgeSubset(v, 0);
        }
        return subsets;
    }

    private void clearSubsets(EdgeSubset[] subsets) {
        for (int ix = 0; ix < subsets.length; ix++) {
            subsets[ix] = null;
        }
    }

    private class EdgeSubset {
        private Integer _parent;
        private Integer _rank;

        @SuppressWarnings("unused")
        public EdgeSubset() { /* empty */ }
        public EdgeSubset(Integer p, Integer r) {
            _parent = p;
            _rank = r;
        }
        public int parent() { return _parent; }
        public void parent(Integer v) { _parent = v; }
        public int rank() { return _rank; }
        @SuppressWarnings("unused")
        public void rank(Integer v) { _rank = v; }
        public void rankInc() { _rank++; }
    }

    public void clear() {
        for (int ix = 0; ix < _nvertex; ix++) {
        	_adjmatrix[ix].clear();
        	_adjmatrix[ix] = null;
        }
        for (int ix = 0; ix < _nedge; ix++) {
            _edges[ix] = null;
        }
    }

}
