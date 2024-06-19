package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph 
{
	private List<Person> vertexList;//顶点集合
	private List<List<Integer>> edgeList;//边集合，二维动态列表
	
	public FriendshipGraph()
	{
		this.vertexList = new ArrayList<Person>();
		this.edgeList = new ArrayList<List<Integer>>();
	}
	
	/**
	 * 加入结点(人)
	 * 要求不能出现重名以及重复加入结点，否则报错
	 * 若出现重名，抛出异常IllegalArgumentException
	 * 若重复加入结点，只提示，程序继续执行
	 * @param p p是要加入社交网络图的人(对象)
	 */
	public void addVertex(Person p)throws IllegalArgumentException
	{
		//判断是否出现重名
		for(Person q:this.vertexList)
		{
			if(p.getName().equals(q.getName()))
			{
				throw new IllegalArgumentException("姓名重复：" + p.getName());
			}
		}
		//判断p是否已经在列表之中
		if(!this.vertexList.contains(p))
		{
			this.vertexList.add(p);
			this.edgeList.add(new LinkedList<Integer>());
		}
		else 
		{
			System.out.println("该顶点"+p.getName()+"已经加入图中！本操作无效");
		}
		
	}
	
	/**
	 * 加入单向边（社交关系）
	 * 要求p1和p2必须先被作为顶点加入图中，p1不可等于p2,且边不可重复加入
	 * 若p1或p2未加入，则抛出异常IllegalArgumentException
	 * 若p1等于p2,则抛出异常IllegalArgumentException
	 * 若添加的边重复，仅提示错误，程序继续执行
	 * 要注意此方法只加入一条单向的,从p1到p2的边。因为是社交网络是无向图，所以需要再进行加入p2->p1边的操作
	 * @param p1 边的一个端点
	 * @param p2 边的另一个端点
	 */
	public void addEdge(Person p1,Person p2)throws IllegalArgumentException
	{
		int index1 = this.vertexList.indexOf(p1);
		int index2 = this.vertexList.indexOf(p2);
		//判断p1和p2是否已经被添加入顶点集
		if(index1 == -1||index2 == -1)
		{
			 throw new IllegalArgumentException("输入错误！不在顶点集合中！");
		}
		//判断p1是否等于p2
		if(index1 == index2)
		{
			throw new IllegalArgumentException("输入错误！不可有指向自己的边！");
		}
		//判断添加的边是否重复
		if(this.edgeList.get(index1).contains(index2))
		{
			System.out.println("边"+p1.getName()+"-"+p2.getName()+"已存在，本操作无效！");
		}
		
		this.edgeList.get(index1).add(index2);
	}
	
	/**
	 * 使用BFS获取p1和p2之间的距离
	 * 要求p1和p2必须先被作为顶点加入图中，若不是，则抛出异常IllegalStateException
	 * p1和p2之间的距离定义为二者间的最短路径长。每一条边权值为1
	 * 若p1和p2之间不存在任何路径，则返回-1
	 * @param p1 二者其中一人，对象
	 * @param p2 二者另一个人，对象
	 * @return p1和p2之间的距离，整数
	 */
	public int getDistance(Person p1,Person p2)throws IllegalStateException
	{
		int index1 = this.vertexList.indexOf(p1);
		int index2 = this.vertexList.indexOf(p2);
		//判断p1和p2是否已经被添加入顶点集
		if(index1 == -1||index2 == -1)
		{
			throw new IllegalStateException("边" + p1.getName() + "-" + p2.getName() + "已存在！");
	    }
		
		//初始化
        Queue<Integer> queue = new LinkedList<>();//队列，用于广度优先搜索
        Set<Integer> visited = new HashSet<>();//集合，存储已经被搜索过的结点
        queue.offer(index1);
        visited.add(index1);
        int depth = 0;
        while(!queue.isEmpty())
        {
        	int size = queue.size();
        	//遍历当前层的所有结点
        	for(int i=0;i<size;i++)
        	{
        		//从队列中取出结点，若找到目标结点，返回深度，即为距离
        		int vertex = queue.poll();
        		if(vertex == index2)
        		{
        			return depth;
        		}
        		
        		//否则，将该节点的所有相邻结点加入
        		for(int j=0;j<this.edgeList.get(vertex).size();j++)
        		{
        			//第j个相邻的结点的下标
        			int newVertex = this.edgeList.get(vertex).get(j);
        			//如果未访问
        			if(!visited.contains(newVertex))
        			{
        				queue.offer(newVertex);
        				visited.add(newVertex);
        			}
        		}
        	}
        	//当前层结束后，深度+1
        	depth++;
        }
        //如果队列为空仍然未找到目标节点，说明二者在不同的连通分量中，返回-1
        return -1;
	}
	
	public static void main(String[] args)
	{
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1
	}

	List<Person> getVertexList() 
	{
		return this.vertexList;
	}
	
	List<List<Integer>> getEdgeList()
	{
		return this.edgeList;
	}

}

