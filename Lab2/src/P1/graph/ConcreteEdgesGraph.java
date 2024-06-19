/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices,edges) =	一个带顶点标签的加权有向图
    //							图的顶点集合为vertices
    //   						图的边集合为edges
    //							边集中的每个元素都表示起点为source，终点为target，权重为weight的有向边
    // Representation invariant:
    //   vertices为String的集合
    //	 edges为Edge类的集合
    //	 edges中的所有顶点必须包含在vertices中
    //	 edges中的所有边的权重为正整数
    //	 edges中不能有重复标签的顶点，所以采用集合类
    
    // Safety from rep exposure:
    //   所有的字段均为private final
    //	 所有需要获取字段信息的方法均使用防御式拷贝处理
    //	 所有字段的元素类型为String和Edge，均为不可变类型
    
    // constructor
    //由于所有字段使用final且已被初始化，所以使用默认的构造方法
    //对图的构建使用提供的add()和set()方法即可。
    
    // checkRep
    private void checkRep() 
    {
		for(Edge<L> e:this.edges)
		{
			assert e.getWeight() > 0;
			assert vertices.contains(e.getSource());
			assert vertices.contains(e.getTarget());
		}
	}
    @Override public boolean add(L vertex) {
    	if(vertices.contains(vertex))
    	{
    		checkRep();
    		return false;
    	}
    	else 
    	{
    		vertices.add(vertex);
    		checkRep();
    		return true;
		}
    }
    
    @Override public int set(L source, L target, int weight) {
    	
    	//权重非0
    	//这里不用考虑小于0的情况，交给Edge类的checkRep完成
        if(weight != 0)
        {
        	Edge<L> e = new Edge<L>(source, target, weight);
        	//先判断是需要增加边还是修改边
        	//该边不存在，则添加
        	if(!this.edges.contains(e))
        	{
        		edges.add(e);
        		//边不存在，意味着顶点可能不存在，则需检验顶点
        		if(!vertices.contains(source)) this.vertices.add(source);
        		if(!vertices.contains(target)) this.vertices.add(target);
        		checkRep();
        		return 0;
        	}
        	//否则，该边存在，需要修改该边的权重
        	//但由于Edge类型不可变，所以需要先删除该边，然后再添加
        	else
        	{
        		//要返回的先前权值
        		int past_weight = 0;
        		Iterator<Edge<L>> iterator = edges.iterator();
        		//使用迭代器遍历edges，找到对应边删除
        		while(iterator.hasNext())
        		{
        			Edge<L> p = iterator.next();
        			if(p.equals(e))
        			{
        				past_weight = p.getWeight();
        				iterator.remove();
        			}
        		}
        		//添加该边
        		edges.add(e);
        		checkRep();
        		return past_weight;
        	}
        }
        //否则，进行删除操作
        else
        {
        	Edge<L> e = new Edge<L>(source, target, 1);
    		//要返回的先前权值
    		int past_weight = 0;
    		Iterator<Edge<L>> iterator = edges.iterator();
    		//使用迭代器遍历edges，找到对应边删除
    		while(iterator.hasNext())
    		{
    			Edge<L> p = iterator.next();
    			if(p.equals(e))
    			{
    				past_weight = p.getWeight();
    				iterator.remove();
    			}
    		}
        	checkRep();
        	return past_weight;
        }
    }
    
    @Override public boolean remove(L vertex) {
        //首先判断该顶点是否存在
    	if(!vertices.contains(vertex))
    	{
    		checkRep();
    		return false;
    	}
    	//否则，该点存在
    	//寻找与该点相关的边，全部删除
    	else 
    	{
    		Iterator<Edge<L>> iterator = edges.iterator();
    		//使用迭代器遍历edges，找到与vertex关联的所有边然后删除
    		while(iterator.hasNext())
    		{
    			Edge<L> e = iterator.next();
    			if(e.getSource().equals(vertex)||e.getTarget().equals(vertex))
    			{
    				iterator.remove();
    			}
    		}
    		//最后在vertices中删除该顶点
    		vertices.remove(vertex);
    		checkRep();
    		return true;
		}
    }
    
    @Override public Set<L> vertices() {
    	//防御式拷贝，防止表示泄露
    	checkRep();
        return new HashSet<L>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<L, Integer>();
        for(Edge<L> e:edges)
        {
        	if(e.getTarget().equals(target))
        	{
        		sources.put(e.getSource(), e.getWeight());
        	}
        }
        checkRep();
      //防御式拷贝，防止表示泄露
        return new HashMap<L, Integer>(sources);
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<L, Integer>();
        for(Edge<L> e:edges)
        {
        	if(e.getSource().equals(source))
        	{
        		targets.put(e.getTarget(), e.getWeight());
        	}
        }
        checkRep();
      //防御式拷贝，防止表示泄露
        return new HashMap<L, Integer>(targets);
    }
    
    // toString()
    /**
     * 字符串形式描述加权有向图
     * @return sb.toString() 返回形式化的描述，其格式为：
     * 顶点集：顶点标签1,顶点标签2,...,
     * 边集：
     * edge1:source--->target, weight=权重大小
     * edge2:source--->target, weight=权重大小
     * ...
     */
    @Override public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("顶点集：");
    	for(L v:vertices)
    	{
    		sb.append(v+",");
    	}
    	sb.append("\n边集：\n");
    	for(Edge<L> e:edges)
    	{
    		sb.append(e.toString()+"\n");
    	}
    	return sb.toString();
    }
    
}


/**
 * specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 每个Edge对象表示一条带有标记顶点的加权有向边
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // fields
	private L source;
	private L target;
	private int weight;
    
    // Abstraction function:
    //   AF(source,target,weight) =	一条起点为source，终点为target
	//								权重为weight的加权有向边。
	//		
    // Representation invariant:
    //   source和target均为字符串
	//	 weight为正整数
    // Safety from rep exposure:
    //   所有字段均为private
	//	 字段的类型为String和int，不可变。
    
    //  constructor
	/**
	 * Edge对象的构造方法
	 * @param s 起点的标签，是一个字符串
	 * @param t 终点的标签，是一个字符串
	 * @param w 这条边的权重，是一个正整数
	 */
    public Edge(L s,L t,int w)throws IllegalArgumentException
    {
		this.source = s;
		this.target = t;
		this.weight = w;
		checkRep();
    }
    // checkRep
    private void checkRep()throws IllegalArgumentException
    {
    	assert this.weight > 0;
    }
    // methods
    /**
     * 获取边的起点
     * @return 边的起点
     */
    public L getSource()
    {
    	return this.source;
    }
    /**
     * 获取边的终点
     * @return 边的终点
     */
    public L getTarget()
    {
    	return this.target;
    }
    /**
     * 获取边的权重
     * @return 边的权重
     */
    public int getWeight()
    {
    	return this.weight;
    }
    
    // toString()
    @Override public String toString()
    {
    	return "edge:"+source+"--->"+target+", weight="+weight;
    }
    // 重写equals()
    /**
     * 判断是否为同一条边
     * @return 若起点和终点相等则为同一条边，权重可不等
     */
    @Override public boolean equals(Object obj)
    {
    	if(!(obj instanceof Edge))
    	{
    		return false;
    	}
    	else 
    	{
    		Edge<?> other = (Edge<?>) obj;
			return this.source.equals(other.source) &&
			       this.target.equals(other.target);
		}
    }
}
