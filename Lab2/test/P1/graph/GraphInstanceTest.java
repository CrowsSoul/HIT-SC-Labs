/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
	// Testing strategy
	//  对于图的每个实例方法，需要测试以下几个方面：
	//  1. 空图的情况：测试在没有任何顶点或边的情况下，方法的行为是否符合预期。
	//  2. 添加顶点或边的情况：测试向图中添加新顶点或边的行为，包括添加已存在的顶点或边的情况。
	//  3. 更新边的权重的情况：测试更新已存在的边的权重，以及权重为0的边的情况。
	//  4. 删除顶点的情况：测试删除顶点及其相关的边的行为，包括删除不存在的顶点的情况。
	//  5. 获取顶点集合或相关顶点的情况：测试获取图中顶点集合、某个顶点的源顶点集合和目标顶点集合的行为。
	//  对每个方法的具体测试已经在注释中解释

    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testAdd()
    {
    	//测试空图能否添加顶点A
    	Graph<String> g = emptyInstance();
    	assertTrue(g.add("A"));
    	assertTrue(g.vertices().contains("A"));
    	
    	//测试添加已经存在的顶点A
    	assertFalse(g.add("A"));
    	
    	//测试多个顶点
    	g.add("B");
    	g.add("C");
    	assertTrue(g.vertices().contains("A"));
    	assertTrue(g.vertices().contains("B"));
    	assertTrue(g.vertices().contains("C"));
    }
    
    @Test
    public void testSet()
    {
    	//初始化图
    	Graph<String> g= emptyInstance();
    	g.add("A");
    	g.add("B");
    	g.add("C");
    	
    	//测试添加一条权重非0的边
    	assertEquals(0, g.set("A", "B", 3));
    	//测试该边被成功添加
    	assertTrue(g.targets("A").containsKey("B"));
    	assertTrue(g.targets("A").containsValue(3));
    	assertEquals(Integer.valueOf(3),g.targets("A").get("B"));
    	
    	//测试更新已有的边的权重，返回值是否为先前权重
    	assertEquals(3, g.set("A", "B", 2));
    	//测试该边权重被成功更新
    	assertEquals(Integer.valueOf(2),g.targets("A").get("B"));
    	
    	//测试给定顶点不存在的情况
    	assertEquals(0, g.set("A", "D", 10));
    	//测试顶点被成功添加
    	assertTrue(g.vertices().contains("D"));
    	//测试该边被成功添加
    	assertTrue(g.targets("A").containsKey("D"));
    	assertTrue(g.targets("A").containsValue(10));
    	assertEquals(Integer.valueOf(10),g.targets("A").get("D"));
    	
    	//测试删除某条边
    	g.set("A", "C", 7);
    	//测试返回值
    	assertEquals(7, g.set("A", "C", 0));
    	//测试该边是否被删除
    	assertFalse(g.targets("A").containsKey("C"));
    	
    	//测试添加权重为0的边是否没有修改图
    	g.set("B", "C", 0);
    	assertFalse(g.targets("B").containsKey("C"));
    	
    }
    
    @Test
    public void testRemove()
    {
    	//初始化
    	Graph<String> g = emptyInstance();
    	g.set("A", "B", 2);
    	g.set("A", "C", 3);
    	g.set("A", "D", 7);
    	g.set("E", "A", 9);
    	g.set("F", "A", 6);
    	g.set("G", "A", 2);
    	
    	//测试删除顶点A
    	//测试返回值
    	assertTrue(g.remove("A"));
    	//测试该顶点是否被删除
    	assertFalse(g.vertices().contains("A"));
    	//测试以A为源点的边是否全被删除
    	assertFalse(g.targets("A").containsKey("B"));
    	assertFalse(g.targets("A").containsKey("C"));
    	assertFalse(g.targets("A").containsKey("D"));
    	//测试以A的目标顶点的边是否全被删除
    	assertFalse(g.sources("A").containsKey("E"));
    	assertFalse(g.sources("A").containsKey("F"));
    	assertFalse(g.sources("A").containsKey("G"));
    	
    	//测试删除一个不存在的顶点H
    	//测试返回值
    	assertFalse(g.remove("H"));
    }
    
    @Test
    public void testVertices()
    {
    	//初始化
    	Graph<String> g = emptyInstance();
    	
    	//测试没有顶点时的情况
        assertEquals(Collections.emptySet(), emptyInstance().vertices());
        
        //加入顶点
        g.add("Tom");
        g.add("Lucy");
        g.add("A");
        g.add("B");
        g.add("C");
        
        //测试有顶点的情况
        assertTrue(g.vertices().contains("Tom"));
        assertTrue(g.vertices().contains("Lucy"));
        assertTrue(g.vertices().contains("A"));
        assertTrue(g.vertices().contains("B"));
        assertTrue(g.vertices().contains("C"));
        
        //测试一个没有的顶点
        assertFalse(g.vertices().contains("D"));
    }
    
    @Test
    public void testSources()
    {
    	//初始化
    	Graph<String> g = emptyInstance();
    	g.set("B", "A", 9);
    	g.set("C", "A", 6);
    	g.set("D", "A", 2);
    	
    	//测试顶点A的所有源顶点
    	assertTrue(g.sources("A").containsKey("B"));
    	assertTrue(g.sources("A").containsKey("C"));
    	assertTrue(g.sources("A").containsKey("D"));
    	//测试顶点A为目标顶点的所有边的权重
    	assertEquals(Integer.valueOf(9), g.sources("A").get("B"));
    	assertEquals(Integer.valueOf(6), g.sources("A").get("C"));
    	assertEquals(Integer.valueOf(2), g.sources("A").get("D"));
    }
    
    @Test
    public void testTargets()
    {
    	//初始化
    	Graph<String> g = emptyInstance();
    	g.set("Aa", "Bb", 9);
    	g.set("Aa", "Cc", 6);
    	g.set("Aa", "Dd", 2);
    	//测试顶点A的所有目标顶点
    	assertTrue(g.targets("Aa").containsKey("Bb"));
    	assertTrue(g.targets("Aa").containsKey("Cc"));
    	assertTrue(g.targets("Aa").containsKey("Dd"));
    	//测试顶点A为源顶点的所有边的权重
    	assertEquals(Integer.valueOf(9), g.targets("Aa").get("Bb"));
    	assertEquals(Integer.valueOf(6), g.targets("Aa").get("Cc"));
    	assertEquals(Integer.valueOf(2), g.targets("Aa").get("Dd"));
    }
}
