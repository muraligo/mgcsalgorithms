package com.m3.csalgorithms.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.m3.csalgorithms.Pair;

class GraphTest {

    private Graph g;

    @BeforeEach
    void setUp() throws Exception {
        List<Pair<Integer, Integer>> initial = new ArrayList<Pair<Integer, Integer>>();
        initial.add(new Pair<Integer, Integer>(0, 1));
        initial.add(new Pair<Integer, Integer>(0, 2));
        initial.add(new Pair<Integer, Integer>(1, 2));
        initial.add(new Pair<Integer, Integer>(2, 0));
        initial.add(new Pair<Integer, Integer>(2, 3));
        initial.add(new Pair<Integer, Integer>(3, 3));
        g = new Graph(4, initial);
    }

    @AfterEach
    void tearDown() throws Exception {
        g.clear();
    }

    @Test
    void testBreadthFirstTraversal() {
        List<Integer> bfsres = g.breadthFirstTraversal(2);
        if (bfsres.isEmpty()) {
            System.out.println("Nothing in here");
        } else {
            bfsres.forEach(a -> System.out.print(a.toString() + " "));
            System.out.println();
            assertEquals(2, bfsres.get(0).intValue());
            assertEquals(0, bfsres.get(1).intValue());
            assertEquals(3, bfsres.get(2).intValue());
            assertEquals(1, bfsres.get(3).intValue());
        }
    }

    @Test
    void testDepthFirstTraversal() {
        List<Integer> dfsres = g.depthFirstTraversal(2);
        if (dfsres.isEmpty()) {
            System.out.println("Nothing in here");
        } else {
            dfsres.forEach(a -> System.out.print(a.toString() + " "));
            System.out.println();
        }
        assertEquals(2, dfsres.get(0).intValue());
        assertEquals(0, dfsres.get(1).intValue());
        assertEquals(1, dfsres.get(2).intValue());
        assertEquals(3, dfsres.get(3).intValue());
    }

    @Test
    void testDetectCyclesUnionFind() {
        boolean cyc = g.detectCyclesUnionFind();
        assertTrue(cyc);
    }

}
