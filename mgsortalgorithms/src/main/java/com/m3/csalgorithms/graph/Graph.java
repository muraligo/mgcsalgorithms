package com.m3.csalgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.m3.csalgorithms.Pair;

/**
 * A graph is represented by an Adjacency Matrix implemented as 
 * an Array of LinkedList of nodes
 * (Remember a LinkedList also implements the Queue interface and can be used as such)
 * @author museg
 *
 */
public class Graph {
    private final int _numvertices;
    private final ArrayList<Integer>[] _vertices;
    private final Edge[] _edges;
    private final boolean _ranksMatter;

    @SuppressWarnings("unchecked")
	public Graph(int n, List<Pair<Integer, Integer>> edges) {
        _numvertices = n;
        _vertices = new ArrayList[n];
        _edges = new Edge[edges.size()];
        for (int ix = 0; ix < n; ix++) {
            _vertices[ix] = new ArrayList<Integer>();
        }
        int jx = 0;
        for (Pair<Integer, Integer> edge : edges) {
            _vertices[edge.first()].add(edge.second());
            _edges[jx] = new Edge(edge.first(), edge.second());
            jx++;
        }
        _ranksMatter = false;
    }

    @SuppressWarnings("unchecked")
	public Graph(int n, List<Pair<Integer, Integer>> edges, List<Integer> wghts) {
        _numvertices = n;
        _vertices = new ArrayList[n];
        _edges = new Edge[edges.size()];
        for (int ix = 0; ix < n; ix++) {
            _vertices[ix] = new ArrayList<Integer>();
        }
        int jx = 0;
        for (Pair<Integer, Integer> edge : edges) {
            _vertices[edge.first()].add(edge.second());
            if (wghts != null && !wghts.isEmpty()) {
                _edges[jx] = new Edge(edge.first(), edge.second(), wghts.get(jx));
            } else {
                _edges[jx] = new Edge(edge.first(), edge.second());
            }
            jx++;
        }
        _ranksMatter = true;
    }

    public int numberOfVertices() { return _numvertices; }
    public List<Integer> edgesFromVertex(int r) { return _vertices[r]; }
    public Edge edge(int r) { return _edges[r]; }

    public List<Integer> breadthFirstTraversal(int start) {
        final List<Integer> result = new ArrayList<Integer>();
        final Boolean[] visited = new Boolean[_numvertices];
        for (int ix = 0; ix < _numvertices; ix++) {
            visited[ix] = false;
        }
        visited[start] = true;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(start);
        while (!temp.isEmpty()) {
            Integer nd = temp.remove(0);
            result.add(nd);
            edgesFromVertex(nd).stream().filter(con -> !visited[con]).forEach(con2 -> {
                temp.add(con2);
                visited[con2] = true;
            });
        }
        return result;
    }

    /*
     * Depth First Search has many uses:
     * 1) For an unweighted graph, DFS traversal of the graph produces the 
     *    minimum spanning tree and all pair shortest path tree.
     * 2) Detecting cycle in a graph
     *    A graph has cycle if and only if we see a back edge during DFS. 
     *    So we can run DFS for the graph and check for back edges.
     * 3) Path Finding
     *    We can specialize the DFS algorithm to find a path between 
     *    two given vertices u and z.
     *      i) Call DFS(G, u) with u as the start vertex.
     *     ii) Use a stack S to keep track of the path between the start vertex 
     *         and the current vertex.
     *    iii) As soon as destination vertex z is encountered, return the path 
     *    as the contents of the stack
     * 4) Topological Sorting
     *    Topological Sorting is mainly used for scheduling jobs from the 
     *    given dependencies among jobs. In computer science, applications 
     *    of this type arise in instruction scheduling, ordering of formula 
     *    cell evaluation when recomputing formula values in spreadsheets, 
     *    logic synthesis, determining the order of compilation tasks to 
     *    perform in makefiles, data serialization, and resolving symbol 
     *    dependencies in linkers.
     * 5) To test if a graph is bipartite
     *    We can augment either BFS or DFS when we first discover a new vertex, 
     *    color it opposite its parents, and for each other edge, check it doesn’t 
     *    link two vertices of the same color. The first vertex in any connected 
     *    component can be red or black! See this for details.
     * 6) Finding Strongly Connected Components of a graph A directed graph 
     *    is called strongly connected if there is a path from each vertex 
     *    in the graph to every other vertex. (See this for DFS based algo for 
     *    finding Strongly Connected Components)
     * 7) Solving puzzles with only one solution, such as mazes. (DFS can be 
     *    adapted to find all solutions to a maze by only including nodes on the 
     *    current path in the visited set.)
     */
    public List<Integer> depthFirstTraversal(int start) {
        final List<Integer> result = new ArrayList<Integer>();
        final Boolean[] visited = new Boolean[_numvertices];
        for (int ix = 0; ix < _numvertices; ix++) {
            visited[ix] = false;
        }
        dfsRecurse(start, visited, result);
        return result;
    }

    private void dfsRecurse(int start, final Boolean[] visited, final List<Integer> result) {
        visited[start] = true;
        result.add(start);
        for (Integer n : _vertices[start]) {
            if (!visited[n]) {
                dfsRecurse(n, visited, result);
            }
        }
    }

    /*
     * A disjoint-set data structure is a data structure that keeps track 
     * of a set of elements partitioned into a number of disjoint (non-overlapping) 
     * subsets. A union-find algorithm is an algorithm that performs 
     * two useful operations on such a data structure:
     * Find: Determine which subset a particular element is in. 
     *       This can be used for determining if two elements are in the same subset.
     * Union: Join two subsets into a single subset.
     * We can keep track of the subsets in a 1D array, let’s call it parent[].
     * Initially, all slots of parent array are initialized to -1 (means 
     * there is only one item in every subset).
     * For each edge, make subsets using both the vertices of the edge. 
     * If both the vertices are in the same subset, a cycle is found.
     */
    public boolean detectCyclesUnionFind() {
        int i = 0;  // An index variable, used for sorted edges
        // Allocate memory for creating _numvertices subsets
        EdgeSubset subsets[] = new EdgeSubset[_numvertices];
        for (i = 0; i < _numvertices; ++i)
            subsets[i] = new EdgeSubset();
        // Initialize all subsets as single element sets
        for (int v = 0; v < _numvertices; ++v)  {
            subsets[v].parent(v);
            subsets[v].rank(0);
        }
  
        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        for (int ix = 0; ix < _edges.length; ++ix) {
            System.out.print("Checking index [" + ix + "] ");
            int x = find(subsets, _edges[ix].first());
            System.out.print("x=[" + x + "] ");
            int y = find(subsets, _edges[ix].second());
            System.out.print("y=[" + y + "] ");
            if (x == y) {
                System.out.println("Cycle found");
                return true;
            }
            System.out.println("No cycles so union");
            union(subsets, x, y);
        }
        return false;
    }

    // A utility function to find set of an element i 
    // (uses path compression technique) 
    private int find(EdgeSubset subsets[], int i) { 
        // find root and make root as parent of i (path compression) 
        if (subsets[i].parent() != i) {
            System.out.print("to index [" + subsets[i].parent() + "] ");
            subsets[i].parent(find(subsets, subsets[i].parent()));
        }
        System.out.print("Parent found ");
        return subsets[i].parent(); 
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
     * Below are the steps for finding MST using Kruskal’s algorithm
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
        final Edge[] result = new Edge[_numvertices]; // This will store the resultant MST
        int e = 0;  // An index variable, used for result[] 
        int i = 0;  // An index variable, used for sorted edges 
  
        // Step 1:  Sort all the edges in non-decreasing order of their weight. If we are
        // not allowed to change the given graph, we can create a copy of array of edges 
        Arrays.sort(_edges); 
  
        // Allocate memory for creating _numvertices subsets 
        EdgeSubset subsets[] = new EdgeSubset[_numvertices]; 
        for (i = 0; i < _numvertices; ++i) 
            subsets[i] = new EdgeSubset(); 
  
        // Create _numvertices subsets with single elements 
        for (int v = 0; v < _numvertices; ++v)  {
            subsets[v].parent(v);
            subsets[v].rank(0);
        }
  
        i = 0;  // Index used to pick next edge
        // Number of edges to be taken is equal to V-1
        while (e < (_numvertices - 1)) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = _edges[i++];
            int x = find(subsets, next_edge.first());
            int y = find(subsets, next_edge.second());
            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) { 
                result[e++] = next_edge;
                union(subsets, x, y);
            } 
            // Else discard the next_edge 
        } 
  
        // print the contents of result[] to display 
        // the built MST 
//        System.out.println("Following are the edges in " +  
//                                     "the constructed MST"); 
//        for (i = 0; i < e; ++i) 
//            System.out.println(result[i]); 
        return result;
    }

    /*
     * Prim’s algorithm is also a Greedy algorithm. It starts with an empty spanning tree. 
     * The idea is to maintain two sets of vertices. The first set contains the vertices 
     * already included in the MST, the other set contains the vertices not yet included. 
     * At every step, it considers all the edges that connect the two sets, and 
     * picks the minimum weight edge from these edges. After picking the edge, 
     * it moves the other endpoint of the edge to the set containing MST.
     * A group of edges that connects two set of vertices in a graph is called cut 
     * in graph theory. So, at every step of Prim’s algorithm, we find a cut (of 
     * two sets, one contains the vertices already included in MST and other contains 
     * rest of the vertices), pick the minimum weight edge from the cut and 
     * include this vertex to MST Set (the set that contains already included vertices).
     * How does Prim’s Algorithm Work? 
     * The idea behind Prim’s algorithm is simple, a spanning tree means all vertices 
     * must be connected. So the two disjoint subsets (discussed above) of vertices 
     * must be connected to make a Spanning Tree. And they must be connected with the 
     * minimum weight edge to make it a Minimum Spanning Tree.
     */
    public List<Integer> minimumSpanningTreePrim(int start) {
        final List<Integer> result = new ArrayList<Integer>();
        final List<Integer> others = new ArrayList<Integer>();
        for (int ix = 0; ix < _numvertices; ix++) {
            if (!_vertices[ix].isEmpty() && !others.contains(ix) && ix != start)
                others.add(ix);
        }
        result.add(start);
        // TODO fix this
        return result;
    }

    /*
     * Given a graph and a source vertex in the graph, find shortest paths 
     * from source to all vertices in the given graph.
     * Dijkstra’s algorithm is very similar to Prim’s algorithm for minimum 
     * spanning tree. Like Prim’s MST, we generate a SPT (shortest path tree) 
     * with given source as root.
     */
    public List<Integer> shortestPathDjikstra(int start) {
        final List<Integer> result = new ArrayList<Integer>();
        return result;
    }

    class EdgeSubset {
        private Integer _parent;
        private Integer _rank;

        public int parent() { return _parent; }
        public void parent(Integer v) { _parent = v; }
        public int rank() { return _rank; }
        public void rank(Integer v) { _rank = v; }
        public void rankInc() { _rank++; }
    }

    public void clear() {
        for (int ix = 0; ix < 4; ix++) {
        	_vertices[ix].clear();
        	_vertices[ix] = null;
        }
    }
}
