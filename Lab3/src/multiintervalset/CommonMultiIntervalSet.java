package multiintervalset;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import intervalset.CommonIntervalSet;
import intervalset.IntervalSet;

/**
 * MultiIntervalSet<L>接口的基本实现类
 * 继承接口的规约
 * @param <L> 标签类型
 */
public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {

	protected final Map<L, IntervalSet<Integer>> MultiIntervalMap = 
			new HashMap<L, IntervalSet<Integer>>();
		
    // Abstraction function:
    //   AF(MultiIntervalMap) =	
	//			{[标签1=[起始时间1.1,终止时间1.1],[起始时间1.2,终止时间1.2],...],...}
	//			即允许一个标签绑定多个时间段的集合				
    // Representation invariant:
	//   拥有同一标签的多个时间段不可重叠
	// 	 对任何时间段，有end>start>=0
	//   对于标签L，其对应的IntervalSet<Integer>中，越小的标签对应的起始时间越小
    
    // Safety from rep exposure:
    //   所有的字段均为protected final
    //	 所有需要获取字段信息的方法均使用防御式拷贝处理
    //	 Map中的L类型是不可变的，而IntervalSet已保证其安全性
    
    // constructor
	// 提供以IntervalSet为参数的构造方法
	public CommonMultiIntervalSet(IntervalSet<L> initial)
	{
		for(L label:initial.labels())
		{
			long start = initial.start(label);
			long end = initial.end(label);
			this.insert(start, end, label);
		}
	}
	public CommonMultiIntervalSet()
	{
		
	}
    

	// checkRep
	// 时间段的RI已在Period内部保证。
	private void checkRep()
	{
		for(L label:MultiIntervalMap.keySet())
		{
			IntervalSet<Integer> periods = MultiIntervalMap.get(label);
			int periodCounts = periods.labels().size();
			for(int i = 1;i<periodCounts;i++)
			{
				//验证越小的标签，起始时间越小
				assert periods.start(i) > periods.start(i-1);
				//验证没有重叠的时间段
				assert periods.start(i) >= periods.end(i-1);
			}
		}
	}
	
	@Override
	public void insert(long start, long end, L label) {
		
		//首先判断该标签是否已经存在
		//若存在，则需按照初始时间的顺序添加
		if(MultiIntervalMap.keySet().contains(label))
		{
			//先确定位置
			int target = -1;
			//获取该标签对应的IntervalSet
			IntervalSet<Integer> periods = MultiIntervalMap.get(label);
			
			//进行遍历
			int periodsCounts = periods.labels().size();
			for(int i = 0;i<periodsCounts;i++)
			{
				//第一次遇到比start大的，则i就表示目标位置，用target记录。
				if(start<periods.start(i))
				{
					target = i;
					break;
				}
			}
			
			//如果target的值没有变化，说明start是最大的，直接加入
			if(target==-1)
			{
				periods.insert(start, end, periodsCounts);
				checkRep();
			}
			//否则
			else 
			{
				//然后，从后往前，先取出时间段，然后remove掉，然后再对标签加一后insert
				for(int i = periodsCounts-1;i>=target;i--)
				{
					long tempStart = periods.start(i);
					long tempEnd = periods.end(i);
					periods.remove(i);
					periods.insert(tempStart, tempEnd, i+1);
				}
				//此时，target位置已经空出，插入即可
				periods.insert(start, end, target);
				checkRep();
			}

		}
		//若不存在，则需要置标签为0后添加。
		else 
		{
			IntervalSet<Integer> newPeriods = new CommonIntervalSet<Integer>();
			newPeriods.insert(start, end, 0);
			MultiIntervalMap.put(label, newPeriods);
			checkRep();
		}
	}

	@Override
	public Set<L> labels() {
		checkRep();
		return MultiIntervalMap.keySet();
	}

	@Override
	public boolean remove(L label) {
		if(MultiIntervalMap.keySet().contains(label))
		{
			MultiIntervalMap.remove(label);
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
	public IntervalSet<Integer> intervals(L label) {
		checkRep();
		return new CommonIntervalSet<Integer>(MultiIntervalMap.get(label));
	}

	@Override
	public String toString()
	{
		return MultiIntervalMap.toString();
	}
	@Override
	public long getStartTime() {
		//对每个标签，先获取其intervals，然后调用IntervalSet中的方法即可
		if(!this.MultiIntervalMap.isEmpty())
		{
			Set<Long> startTimes = new HashSet<Long>();
			for(L label:MultiIntervalMap.keySet())
			{
				startTimes.add(MultiIntervalMap.get(label).getStartTime());
			}
			
			return Collections.min(startTimes);
		}
		else 
		{
			return -1;
		}
	}
	@Override
	public long getEndTime() {
		//对每个标签，先获取其intervals，然后调用IntervalSet中的方法即可
		if(!this.MultiIntervalMap.isEmpty())
		{
			Set<Long> endTimes = new HashSet<Long>();
			for(L label:MultiIntervalMap.keySet())
			{
				endTimes.add(MultiIntervalMap.get(label).getEndTime());
			}
			
			return Collections.max(endTimes);
		}
		else 
		{
			return -1;
		}
	}
}
