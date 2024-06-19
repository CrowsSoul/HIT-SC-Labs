package P2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;


public class FriendshipGraphTest {
	
    private FriendshipGraph graph;//用于测试getDistance()

    @Before
    public void setUp() {
        graph = new FriendshipGraph();
    }
    
    // Testing strategy 
	//	 测试addVertex()
	//	 	测试顶点集合中顶点数是否与加入顶点集合中的顶点个数相等
	//	 	测试被加入的顶点是否在顶点集中
	//	 测试addEdge()
	//	 	测试被加入的边是否在图中
	//	 	测试顶点不存在时，添加边后该顶点是否已被添加
	//	 	测试重复加入边是否修改了图
	//	 测试getDistance()
	//	 	测试距离计算是否正确
	//	 	测试不在图中的顶点是否抛出 IllegalStateException 异常
	//	 	测试不相连的顶点是否返回 -1
    
	@Test
	public void testAddVertex() 
	{
		FriendshipGraph graph1 = new FriendshipGraph();
		
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");

        graph1.addVertex(p1);
        graph1.addVertex(p2);
        
        //测试顶点数
        assertEquals(2, graph1.vertices().size());
        
        //测试顶点是否存在
        assertTrue(graph1.vertices().contains(p2));
        assertTrue(graph1.vertices().contains(p1));
        
	}

	@Test
	public void testAddEdge() 
	{
		FriendshipGraph graph2 = new FriendshipGraph();
		
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        Person p3 = new Person("Jack");
        Person p4 = new Person("Mary");
        Person p5 = new Person("Pory");
        
        
        //测试边是否存在（双向）
        graph2.addVertex(p1);
        graph2.addVertex(p2);
        graph2.addEdge(p1, p2);
        graph2.addEdge(p2, p1);
        assertTrue(graph2.getFriends(p1).contains(p2));
        assertTrue(graph2.getFriends(p2).contains(p1));
        
        //测试顶点不存在的情况
        graph2.addEdge(p2, p3);
        graph2.addEdge(p3, p2);
        assertTrue(graph2.vertices().contains(p3));
        
        graph2.addEdge(p4, p5);
        graph2.addEdge(p5, p4);
        assertTrue(graph2.vertices().contains(p4));
        assertTrue(graph2.vertices().contains(p5));
        
        //测试重复加入边的情况
        graph2.addEdge(p1, p2);
        graph2.addEdge(p2, p1);
        assertTrue(graph2.getFriends(p1).contains(p2));
        assertTrue(graph2.getFriends(p2).contains(p1));
        
		
	}

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
        assertThrows(IllegalArgumentException.class, () -> graph.getDistance(p10, persons[0]));
        assertThrows(IllegalArgumentException.class, () -> graph.getDistance(p10, new Person("Not in graph")));

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
