package P3;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FriendshipGraphTest {
	
    private FriendshipGraph graph;//用于测试getDistance()

    @Before
    public void setUp() {
        graph = new FriendshipGraph();
    }
    
	/**
	 * 测试addVertex()
	 * 测试顶点集合中顶点数是否与加入顶点集合中的顶点个数相等
	 * 测试被加入的顶点是否在顶点集中
	 * 测试出现重名是否正确抛出异常
	 */
	@Test
	public void testAddVertex() 
	{
		FriendshipGraph graph1 = new FriendshipGraph();
		
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        Person p3 = new Person("Bob");

        graph1.addVertex(p1);
        graph1.addVertex(p2);
        
        //测试顶点数
        assertEquals(2, graph1.getVertexList().size());
        
        //测试顶点是否存在
        assertTrue(graph1.getVertexList().contains(p1));
        assertTrue(graph1.getVertexList().contains(p2));
        
        //测试异常抛出
        assertThrows(IllegalArgumentException.class,  () -> graph1.addVertex(p3));
	}

	/**
	 * 测试addEdge()
	 * 测试边集合的规模是否与顶点数对应
	 * 测试边集合中边数是否与加入边集合中的边个数相等
	 * 测试被加入的边是否在边集中
	 * 测试顶点未加入以及添加指向自己的边时是否正确抛出异常
	 */
	@Test
	public void testAddEdge() 
	{
		FriendshipGraph graph2 = new FriendshipGraph();
		
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        Person p3 = new Person("Jack");
        Person p4 = new Person("Mary");
        Person p5 = new Person("Pory");
        Person p6 = new Person("Lee");
        
        graph2.addVertex(p1);
        graph2.addVertex(p2);
        graph2.addVertex(p3);
        graph2.addVertex(p4);
        graph2.addVertex(p5);
        
        graph2.addEdge(p1, p2);
        graph2.addEdge(p2, p1);
        graph2.addEdge(p2, p3);
        graph2.addEdge(p3, p2);
        graph2.addEdge(p1, p4);
        graph2.addEdge(p4, p1);
        graph2.addEdge(p2, p4);
        graph2.addEdge(p4, p2);
        graph2.addEdge(p3, p5);
        graph2.addEdge(p5, p3);
        graph2.addEdge(p4, p5);
        graph2.addEdge(p5, p4);
        
        //测试规模
        assertEquals(graph2.getEdgeList().size(), graph2.getVertexList().size());
        
        //测试边数
        int sum = 0;
        for(List<Integer> p:graph2.getEdgeList())
        {
        	sum = sum + p.size();
        }
        assertEquals(sum, 12);
        
        //测试边是否存在
        assertTrue(graph2.getEdgeList().get(0).contains(1));
        assertTrue(graph2.getEdgeList().get(1).contains(0));
        assertTrue(graph2.getEdgeList().get(2).contains(4));
        assertTrue(graph2.getEdgeList().get(4).contains(2));
        
        //测试异常抛出
        assertThrows(IllegalArgumentException.class, ()->graph2.addEdge(p1,p6));
        assertThrows(IllegalArgumentException.class, ()->graph2.addEdge(p1,p1));
		
	}

	/**
	 * 测试getDistance()
	 * 测试距离计算是否正确
	 * 测试不在图中的顶点是否抛出 IllegalStateException 异常
	 * 测试不相连的顶点是否返回 -1
	 */
    @Test
    public void testGetDistance() 
    {
        // 添加10个顶点
        Person[] persons = new Person[10];
        for (int i = 0; i < 10; i++) {
            persons[i] = new Person("Person " + (i + 1));
            graph.addVertex(persons[i]);
        }
        Person p10 = new Person("Person10");
        
        // 添加11条边
        addUndirectedEdge(persons[1], persons[2]);
        addUndirectedEdge(persons[2], persons[3]);
        addUndirectedEdge(persons[3], persons[4]);
        addUndirectedEdge(persons[3], persons[5]);
        addUndirectedEdge(persons[1], persons[8]);
        addUndirectedEdge(persons[2], persons[4]);
        addUndirectedEdge(persons[3], persons[1]);
        addUndirectedEdge(persons[4], persons[5]);
        addUndirectedEdge(persons[6], persons[3]);
        addUndirectedEdge(persons[9], persons[7]);
        addUndirectedEdge(persons[7], persons[0]);
        

        // 测试距离计算是否正确
        assertEquals(2, graph.getDistance(persons[2], persons[5]));
        assertEquals(3, graph.getDistance(persons[8], persons[4]));
        assertEquals(0, graph.getDistance(persons[0], persons[0]));

        // 测试不在图中的顶点是否抛出 IllegalStateException 异常
        assertThrows(IllegalStateException.class, () -> graph.getDistance(p10, new Person("Not in graph")));

        // 测试不相连的顶点是否返回 -1
        assertEquals(-1, graph.getDistance(persons[0], persons[6]));
    }
    // 辅助方法，添加无向边
    private void addUndirectedEdge(Person p1, Person p2) 
    {
        graph.addEdge(p1, p2);
        graph.addEdge(p2, p1);
    }

}
