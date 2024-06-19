package intervalset;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Period;

/**
 * IntervalSet<L>接口的基本实现类
 * 继承接口的规约
 * @param <L> 标签类型
 */
public class CommonIntervalSet<L> implements IntervalSet<L> 
{
	private Map<L, Period> IntervalMap = new HashMap<L, Period>();

    // Abstraction function:
    //   AF(IntervalMap) =	
	//		{[标签1=[起始时间1,终止时间1]]，[标签2=[起始时间2,终止时间2]]，...}
	//		即由标签标记的时间段集合
	//	 标签为key，时间段为value。时间段类型包括start和end
    // Representation invariant:
	// 	 同一标签不可绑定到多个时间段
	//   拥有同一标签的多个时间段不可重叠（由上一条一定能保证）
	// 	 对任何时间段，有end>start>=0
    
    // Safety from rep exposure:
    //   所有的字段均为private
    //	 所有需要获取字段信息的方法均使用防御式拷贝处理
    //	 Map中所有元素的类型为L和Period，均不可变。
    
    // constructor
    //由于所有字段使用final且已被初始化，所以使用默认的构造方法
    //后续使用insert和remove来对集合进行修改即可
	//这里提供一种复制的构造方法，用于防御式拷贝
	public CommonIntervalSet(IntervalSet<L> old)
	{
		if(old == null)
		{
			this.IntervalMap = new HashMap<L, Period>();
		}
		else 
		{
			this.IntervalMap = old.getMap();
		}
	}
	public CommonIntervalSet()
	{
		this.IntervalMap = new HashMap<L, Period>();
	}

    
    // checkRep
	// 使用Map，已经保证key的唯一性，即同一个标签不可绑定到多个时间段
	// Period的ADT已经保证start和end符合RI
	// 所以为空
	private void checkRep()
	{
		
	}
	
	@Override
	public void insert(long start, long end, L label) {
		Period p = new Period(start, end);
		IntervalMap.put(label, p);
		checkRep();
		
	}

	@Override
	public Set<L> labels() {
		checkRep();
		return IntervalMap.keySet();
	}

	@Override
	public boolean remove(L label) {
		if(IntervalMap.containsKey(label))
		{
			IntervalMap.remove(label);
			checkRep();
			return true;
		}
		else 
		{
			checkRep();
			return false;
		}
	}

	@Override
	public long start(L label) {
		if(IntervalMap.containsKey(label))
		{
			checkRep();
			return IntervalMap.get(label).getStart();
		}
		else {
			checkRep();
			return -1;
		}
	}

	@Override
	public long end(L label) {
		if(IntervalMap.containsKey(label))
		{
			checkRep();
			return IntervalMap.get(label).getEnd();
		}
		else {
			checkRep();
			return -1;
		}
	}

	@Override
	public Map<L, Period> getMap() 
	{
		checkRep();
		return new HashMap<L, Period>(IntervalMap);
	}
	
	@Override
	public String toString()
	{
		return IntervalMap.toString();
	}
	@Override
	public long getStartTime() {
		//思路是将所有start加入集合，然后取出最小值
		Set<Long> startTimes = new HashSet<Long>();
		for(L label:IntervalMap.keySet())
		{
			startTimes.add(IntervalMap.get(label).getStart());
		}
		
		//先判断是否为空
		if(!startTimes.isEmpty())
		{
			return Collections.min(startTimes);
		}
		else 
		{
			return -1;
		}
	}
	@Override
	public long getEndTime() {
		//思路是将所有end加入集合，然后取出最大值
		Set<Long> endTimes = new HashSet<Long>();
		for(L label:IntervalMap.keySet())
		{
			endTimes.add(IntervalMap.get(label).getEnd());
		}
		
		//先判断是否为空
		if(!endTimes.isEmpty())
		{
			return Collections.max(endTimes);
		}
		else 
		{
			return -1;
		}
	}

}
