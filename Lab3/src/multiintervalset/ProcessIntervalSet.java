package multiintervalset;


import java.util.Map;

import features.IProcessIntervalSet;
import features.NonOverlapMultiIntervalSetImpl;
import intervalset.IntervalSet;
import model.Period;


public class ProcessIntervalSet<L> extends CommonMultiIntervalSet<L>
									implements IProcessIntervalSet
{
	private NonOverlapMultiIntervalSetImpl<L> nonOverlapMultiIntervalSetImpl;
	
    // Abstraction function:
    //   AF(MultiIntervalMap) =	
	//			{[标签1=[起始时间1.1,终止时间1.1],[起始时间1.2,终止时间1.2],...],...}
	//			即允许一个标签绑定多个时间段的集合				
    // Representation invariant:
	//   任何时间段均不可重叠
	// 	 对任何时间段，有end>start>=0
	//   对于标签L，其对应的IntervalSet<Integer>中，越小的标签对应的起始时间越小
    
    // Safety from rep exposure:
    //   所有的字段均为protected final private
    //	 所有需要获取字段信息的方法均使用防御式拷贝处理
    //	 Map中的L类型是不可变的，而IntervalSet已保证其安全性
	
	
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
		//还要验证不可重叠
		assert !checkOverlap();
	}
	
	//构造方法
	public ProcessIntervalSet(IntervalSet<L> initial) {
		super(initial);
		this.nonOverlapMultiIntervalSetImpl = 
				new NonOverlapMultiIntervalSetImpl<L>(this);
	}
	
	public ProcessIntervalSet(){
		this.nonOverlapMultiIntervalSetImpl = 
				new NonOverlapMultiIntervalSetImpl<L>(this);
	}


	@Override
	public boolean checkOverlap() {
		//委派完成
		return nonOverlapMultiIntervalSetImpl.checkOverlap();
	}

	@Override
	public void insert(long start, long end, L label) {
		super.insert(start, end, label);
		checkRep();
	}
	
	@Override
	public boolean remove(L label) {
		checkRep();
		return super.remove(label);
	}
	
	/**
	 * 计算某个标签的总时间
	 * @param label 标签
	 * @return 标签的总时间，若标签不在其中，则返回-1
	 */
	public long sumOfPeriod(L label)
	{
		//判断标签是否在其中
		if(MultiIntervalMap.keySet().contains(label))
		{
			IntervalSet<Integer> set = MultiIntervalMap.get(label);
			Map<Integer, Period> map = set.getMap();
			//若标签没有任何时间段，返回0
			if(map.isEmpty())
			{
				return 0;
			}
			//否则进行计算
			long sum = 0;
			for(Integer i:map.keySet())
			{
				sum += (map.get(i).getEnd()-map.get(i).getStart());
			}
			return sum;
		}
		else {
			return -1;
		}
	}
}
