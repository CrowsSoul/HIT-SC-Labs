/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   测试toString()方法的结果是否符合规约中的格式
    
    //  tests for ConcreteEdgesGraph.toString()
    @Test
    public void testConcreteEdgesGraphToString()
    {
    	//初始化图
    	Graph<String> g = emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.add("C");
    	g.add("D");
    	g.set("A", "B", 10);
    	g.set("A", "C", 5);
    	g.set("D", "B", 2);
    	//构造目标字符串
    	StringBuilder sb = new StringBuilder();
    	sb.append("顶点集：");
    	sb.append("A,B,C,D,");
    	sb.append("\n边集：\n");
    	sb.append("edge:A--->B, weight=10\n");
    	sb.append("edge:A--->C, weight=5\n");
    	sb.append("edge:D--->B, weight=2\n");
    	//测试是否相等
    	assertEquals(sb.toString(), g.toString());
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   测试观察器方法能否返回正确的值
    //   测试构造器方法能否对各字段正确赋值
    //	 测试toString方法能否返回目标格式的字符串
    //	 测试equals方法能否判断两条边的等价性
    // tests for operations of Edge
    
    @Test
    public void testEdge()
    {
    	Edge<String> e = new Edge<String>("Tom", "Lucy", 10000);
    	assertEquals("Tom", e.getSource());
    	assertEquals("Lucy", e.getTarget());
    	assertEquals(10000, e.getWeight());
    }
    @Test
    public void testGetSource()
    {
    	Edge<String> e = new Edge<String>("A", "B", 10);
    	assertEquals("A", e.getSource());
    }
    
    @Test
    public void testGetTarget()
    {
    	Edge<String> e = new Edge<String>("A", "B", 10);
    	assertEquals("B", e.getTarget());
    }
    
    @Test
    public void testGetWeight()
    {
    	Edge<String> e = new Edge<String>("A", "B", 10);
    	assertEquals(10, e.getWeight());
    }
    
    @Test
    public void testEquals()
    {
    	Edge<String> e1 = new Edge<String>("A", "B", 10);
    	Edge<String> e2 = new Edge<String>("A", "B", 2);
    	Edge<String> e3 = new Edge<String>("A", "C", 10);
    	assertTrue(e1.equals(e2));
    	assertFalse(e1.equals(e3));
    }
    
    @Test
    public void testEdgeToString()
    {
    	Edge<String> e = new Edge<String>("Harbin", "Jinan", 200);
    	StringBuilder sb = new StringBuilder();
    	sb.append("edge:Harbin--->Jinan, weight=200");
    	assertEquals(sb.toString(), e.toString());
    }
}
