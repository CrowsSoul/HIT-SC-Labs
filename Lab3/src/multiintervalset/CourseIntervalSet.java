package multiintervalset;

import features.ICourseIntervalSet;
import features.PeriodicMultiIntervalSetImpl;
import intervalset.IntervalSet;
import model.Period;

public class CourseIntervalSet<L> extends CommonMultiIntervalSet<L> 
									implements ICourseIntervalSet
{
	private PeriodicMultiIntervalSetImpl<L> periodicMultiIntervalSetImpl;
    // Abstraction function:
    //   AF(MultiIntervalMap) =	
	//			{[标签1=[起始时间1.1,终止时间1.1],[起始时间1.2,终止时间1.2],...],...}
	//			即允许一个标签绑定多个时间段的集合				
    // Representation invariant:
	//   同一标签的时间段均不可重叠
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
				assert periods.start(i) > periods.end(i-1);
			}
		}
	}
	
	//构造方法
	public CourseIntervalSet(IntervalSet<L> initial) {
		super(initial);
		this.periodicMultiIntervalSetImpl = 
				new PeriodicMultiIntervalSetImpl<L>(this);
	}
	
	public CourseIntervalSet(){
		this.periodicMultiIntervalSetImpl = 
				new PeriodicMultiIntervalSetImpl<L>(this);
	}
	
	@Override
	public void makePeriodic(Period p, long length) {
		//委派完成
		periodicMultiIntervalSetImpl.makePeriodic(p, length);
		checkRep();
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

	@Override
	public long getPeriodicLength(Period p) {
		//委派完成
		return periodicMultiIntervalSetImpl.getPeriodicLength(p);
	}

}
