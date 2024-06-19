/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // test other vertex label types in Problem 3.2
    @Test
  //使用Integer类型
    public void testInteger()
    {
    	Graph<Integer> g = Graph.empty();
    	g.set(1, 2, 10);
    	g.set(1, 3, 5);
    	
    	assertTrue(g.vertices().contains(1));
    	assertTrue(g.vertices().contains(2));
    	assertTrue(g.vertices().contains(3));
    	
    	assertEquals(Integer.valueOf(10), g.targets(1).get(2));
    	assertEquals(Integer.valueOf(5), g.targets(1).get(3));
    }
    
    @Test
    //使用Double类型
    public void testDouble() 
    {
        Graph<Double> graph = Graph.empty();
        graph.set(1.5, 2.5, 10);
        graph.set(1.5, 3.5, 5);

        assertTrue(graph.vertices().contains(1.5));
        assertTrue(graph.vertices().contains(2.5));
        assertTrue(graph.vertices().contains(3.5));

        assertEquals(Integer.valueOf(10), graph.targets(1.5).get(2.5));
        assertEquals(Integer.valueOf(5), graph.targets(1.5).get(3.5));
    }

    @Test
    //使用Boolean类型
    public void testBoolean() {
        Graph<Boolean> graph = Graph.empty();
        graph.set(true, false, 10);
        graph.set(true, true, 5);

        assertTrue(graph.vertices().contains(true));
        assertTrue(graph.vertices().contains(false));

        assertEquals(Integer.valueOf(10), graph.targets(true).get(false));
        assertEquals(Integer.valueOf(5), graph.targets(true).get(true));
    }
    
}
