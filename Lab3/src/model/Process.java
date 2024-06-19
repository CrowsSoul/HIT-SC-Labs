package model;

/**
 * 进程类
 * 不可变
 */
public class Process 
{
	private long ID;
	private String name;
	private long minTime;
	private long maxTime;
	
    // Abstraction function:
    //   AF(ID,name,minTime,maxTime) = 一个进程{ID，名称，最短执行时间，最长执行时间}
    // Representation invariant:
    //   maxTime > minTime > 0
    
    // Safety from rep exposure:
    //   所有的字段均为private
	//	 只提供观察器方法和构造方法
	//   String和long类型，不可变
	
	//构造方法
	/**
	 * 进程类构造方法
	 * @param ID 进程ID
	 * @param name 进程名称
	 * @param minTime 进程最短时间，大于0
	 * @param maxTime 进程最长时间，大于minTime
	 */
	public Process(long ID,String name,long minTime,long maxTime)
	{
		this.ID = ID;
		this.name = name;
		this.minTime = minTime;
		this.maxTime = maxTime;
	}
	
	//checkRep
	//最短进程时间小于最长进程时间，但都大于0
	private void checkRep() 
	{
		assert minTime < maxTime;
		assert minTime > 0;
	}
	
	/**
	 * 获取进程ID
	 * @return 进程ID
	 */
	public long getID()
	{
		checkRep();
		return ID;
	}
	
	/**
	 * 获取进程名称
	 * @return 进程名称
	 */
	public String getName()
	{
		checkRep();
		return name;
	}
	
	/**
	 * 获取最短执行时间
	 * @return 最短执行时间
	 */
	public long getMinTime()
	{
		checkRep();
		return minTime;
	}
	
	/**
	 * 获取最长执行时间
	 * @return 最长执行时间
	 */
	public long getMaxTime()
	{
		checkRep();
		return maxTime;
	}
	
	@Override
	public boolean equals(Object obj)
	{
    	if(!(obj instanceof Process))
    	{
    		return false;
    	}
    	else 
    	{
    		Process other = (Process) obj;
    		return this.ID == other.getID()&&
    				this.name.equals(other.getName())&&
    				this.minTime == other.getMinTime()&&
    				this.maxTime == other.getMaxTime();
		}
	}
}
