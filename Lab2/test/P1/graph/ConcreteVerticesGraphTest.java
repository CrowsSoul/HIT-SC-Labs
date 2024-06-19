/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   检查返回的结果是否符合规约中的格式
    
    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void testGraphToString()
    {
    	//初始化
    	Graph<String> g = emptyInstance();
    	g.set("A", "B", 2);
    	g.set("A", "C", 3);
    	g.set("A", "D", 7);
    	g.set("B", "A", 9);
    	g.set("B", "C", 5);
    	g.set("D", "C", 8);
    	
    	//目标字符串
    	StringBuilder sb = new StringBuilder();
    	sb.append("顶点："+"B"+"\n");
    	sb.append("--->"+"A"+":"+"9"+"\n");
    	sb.append("--->"+"C"+":"+"5"+"\n");
    	sb.append("顶点："+"A"+"\n");
    	sb.append("--->"+"D"+":"+"7"+"\n");
    	sb.append("--->"+"B"+":"+"2"+"\n");
    	sb.append("--->"+"C"+":"+"3"+"\n");
    	sb.append("顶点："+"C"+"\n");
    	sb.append("顶点："+"D"+"\n");
    	sb.append("--->"+"C"+":"+"8"+"\n");
    	
    	assertEquals(sb.toString(), g.toString());
    	

    }
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   测试构造方法生成的对象中各字段的值是否正确
    //	 测试各观察器方法能否返回正确的值
    //   测试变值器对于不需要修改的情况是否做出修改，需要修改的情况是否正确执行
    //	 测试toString()方法能否生成目标格式的字符串
    
    // tests for operations of Vertex
    
    @Test
    public void testVertex()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertEquals(l, crows.getLabel());
    	assertEquals(10, crows.getWeight("A"));
    	assertEquals(7, crows.getWeight("B"));
    	assertEquals(99, crows.getWeight("C"));
    	
    }
    
    @Test
    public void testGetTargets()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertTrue(crows.getTargets().contains("A"));
    	assertTrue(crows.getTargets().contains("B"));
    	assertTrue(crows.getTargets().contains("C"));
    	
    	
    }
    
    @Test
    public void testGetWeight()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertEquals(10, crows.getWeight("A"));
    	assertEquals(7, crows.getWeight("B"));
    	assertEquals(99, crows.getWeight("C"));
    }
    
    @Test
    public void testHasEdge()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertTrue(crows.hasEdge("A"));
    	assertTrue(crows.hasEdge("B"));
    	assertTrue(crows.hasEdge("C"));
    	assertFalse(crows.hasEdge("D"));
    }
    
    @Test
    public void testAddEdge()
    {
    	Vertex<String> crows = new Vertex<String>("Crows");
    	crows.addEdge("A", 1);
    	crows.addEdge("B", 2);
    	crows.addEdge("C", 3);
    	
    	assertTrue(crows.hasEdge("A"));
    	assertTrue(crows.hasEdge("B"));
    	assertTrue(crows.hasEdge("C"));
    	
    	assertEquals(1, crows.getWeight("A"));
    	assertEquals(2, crows.getWeight("B"));
    	assertEquals(3, crows.getWeight("C"));
    	
    }
    
    @Test
    public void testRemoveEdge()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertEquals(10, crows.removeEdge("A"));
    	assertEquals(0, crows.removeEdge("D"));
    }
    
    @Test public void testSetEdge()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertEquals(10, crows.setEdge("A", 9));
    	assertEquals(9,crows.getWeight("A"));
    	
    	crows.setEdge("D", 1000);
    	assertEquals(7, crows.getWeight("B"));
    	assertEquals(99, crows.getWeight("C"));
    }
    
    @Test
    public void testGetMap()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	assertEquals(o, crows.getMap());
    }
    
    @Test
    public void testVertexToString()
    {
    	String l = "Crows";
    	Map<String, Integer> o = new HashMap<String, Integer>();
    	o.put("A", 10);
    	o.put("B", 7);
    	o.put("C", 99);
    	Vertex<String> crows = new Vertex<String>(l, o);
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("顶点：Crows\n");
    	sb.append("--->A:10\n");
    	sb.append("--->B:7\n");
    	sb.append("--->C:99\n");
    	
    	assertEquals(sb.toString(), crows.toString());
    }
    
    
}
