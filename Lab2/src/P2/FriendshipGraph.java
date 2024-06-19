package P2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import P1.graph.Graph;

/**
 * 社交网络图的实现类
 * 描述人与人之间的社交网络图
 * 抽象空间为一个无向图，顶点为人，边为社交关系
 * 因为是无向图，所以在添加边A--B时需要连续调用两次addEdge()方法
 * 图中的人不可重名
 */
public class FriendshipGraph 
{
	final private Graph<Person> graph = Graph.empty();
	
    // Abstraction function:
    //   AF(graph) =	一个社交网络图
	//					该社交网络图中的边为社交关系
	//					顶点为人
    // Representation invariant:
	//	 图为Graph<Person>类型，满足该类的RI
	//	 图中只能出现无向边 即若有边A--->B，则必有边B--->A
	// 	 图中不可出现指向自己的边，如:A--->A
	//	 图中人不可重名
	// 	 图中所有边的权值为1
    
    // Safety from rep exposure:
    //   所有的字段均为private final
	// 	 graph是可变的，但外部无法直接访问
	//   Person类是不可变的
	//	 所有的观察、修改等方法均调用Graph类的方法，这些方法已经在Graph类中实现防御式拷贝	
    
    // constructor
	// 使用默认的构造方法，即只允许使用提供的其他方法对图进行增删改。
	
	//checkRep
	//使用布尔变量b来控制逻辑
	//在调用addEdge()方法时，使用checkRep(true)，不检验是否均为无向边
	//这是因为addEdge()方法要求添加有向边，需要用户自己遵守类的规约，同时调用两次
	//而当调用其他方法时，使用checkRep(false)，检验是否均为无向边
	private void checkRep(boolean isAddEdge) 
	{
		Set<String> allNames = new HashSet<String>();
		
		//遍历每个顶点
		for(Person source:graph.vertices())
		{
			//检验是否有指向自己的边
			assert !graph.targets(source).keySet().contains(source);
			//检验是否重名
			assert !allNames.contains(source.getName());
			allNames.add(source.getName());
			
			for(Person target:graph.targets(source).keySet())
			{
				//对source--->target边，
				//检验是否存在target--->source边
				if(!isAddEdge)assert graph.targets(target).keySet().contains(source);
				
				//检验权值全部为1
				assert graph.targets(source).get(target) == 1;
			}
		}
	}
	
	/**
	 * 加入顶点(人)
	 * 要求不能出现重名（不同的Person对象但名字相同）
	 * 如果加入了重复的结点（同一个Person对象加入多次），则不操作
	 * @param p p是要加入社交网络图的人(Person对象)
	 */
	public void addVertex(Person p)
	{
		//Person不可变，所以不需要防御式拷贝
		graph.add(p);
		checkRep(false);
	}
	
	/**
	 * 加入单向边（社交关系）
	 * 若添加的边重复，则不做修改
	 * 若p1或p2不存在于图中，则在添加该边的同时添加顶点
	 * 此方法要求只加入一条单向的,从p1到p2的边。因为是社交网络是无向图，所以需要再进行加入p2->p1边的操作
	 * @param p1 边的一个端点
	 * @param p2 边的另一个端点
	 */
	public void addEdge(Person p1,Person p2)
	{
		//默认权值为1
		graph.set(p1, p2, 1);
		checkRep(true);
	}
	
	/**
	 * 获取p1和p2之间的距离
	 * 要求p1和p2必须先被作为顶点加入图中
	 * p1和p2之间的距离定义为二者间的最短路径长。每一条边权值为1
	 * 若p1和p2之间不存在任何路径，则返回-1
	 * @param p1 二者其中一人，对象
	 * @param p2 二者另一个人，对象
	 * @return p1和p2之间的距离，整数
	 * @throws IllegalArgumentException p1或p2未在图中，抛出该异常
	 */
	public int getDistance(Person p1,Person p2) throws IllegalArgumentException
	{
		//首先判断p1和p2是否在图中
		//不允许重名，则根据名字进行判断
		Set<String> allNames = new HashSet<String>();
		for(Person p:graph.vertices())
		{
			allNames.add(p.getName());
		}
		if(!(allNames.contains(p1.getName())&&allNames.contains(p2.getName())))
		{
			throw new IllegalArgumentException("顶点不在图中！");
		}
		
		//否则，两顶点都在图中，先初始化
        Queue<Person> queue = new LinkedList<>();//队列，用于广度优先搜索
        Set<Person> visited = new HashSet<>();//集合，存储已经被搜索过的结点
        queue.offer(p1);
        visited.add(p1);
        int depth = 0;
        while(!queue.isEmpty())
        {
        	int size = queue.size();
        	//遍历当前层的所有结点
        	for(int i=0;i<size;i++)
        	{
        		//从队列中取出结点，若找到目标结点，返回深度，即为距离
        		Person vertex = queue.poll();
        		if(vertex.getName() == p2.getName())
        		{
        			return depth;
        		}
        		//否则，将该节点的所有邻接结点加入
        		for(Person p:graph.targets(vertex).keySet())
        		{
        			//如果该点未访问，则加入
        			if(!visited.contains(p))
        			{
        				queue.offer(p);
        				visited.add(p);
        			}
        		}
        	}
        	//当前层结束后，深度+1
        	depth++;
        }
        //如果队列为空仍然未找到目标节点，说明二者在不同的连通分量中，返回-1
        return -1;
	}
	
	/**
	 * 获取图中的所有顶点
	 * 主要用于测试
	 * @return 所有顶点的集合
	 */
	public Set<Person> vertices()
	{
		checkRep(false);
		return graph.vertices();
	}
	
	/**
	 * 获取某人的所有朋友
	 * 主要用于测试
	 * @param p 人
	 * @return p的所有“朋友”，即邻接顶点
	 */
	public Set<Person> getFriends(Person p)
	{
		checkRep(false);
		return graph.targets(p).keySet();
	}
}
