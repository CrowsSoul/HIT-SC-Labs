package features;

import java.util.HashMap;
import java.util.Map;

import intervalset.IntervalSet;
import model.Period;
import multiintervalset.MultiIntervalSet;

public class PeriodicMultiIntervalSetImpl<L> implements PeriodicMultiIntervalSet
{
	private MultiIntervalSet<L> multiIntervalSet;
	private Map<Period,Long> periodics = new HashMap<Period, Long>();
	
	public PeriodicMultiIntervalSetImpl(MultiIntervalSet<L> multiIntervalSet)
	{
		this.multiIntervalSet = multiIntervalSet;
	}
	
	/**
	 * 仅限MultiIntervalSet类型使用
	 * 如果时间段不存在，则不做任何事
	 */
	@Override
	public void makePeriodic(Period p, long length) {
		//判断时间段是否存在
		int flag = 0;
		for(L label:multiIntervalSet.labels())
		{
			IntervalSet<Integer> thisIntervals = multiIntervalSet.intervals(label);
			Map<Integer, Period> thisMap = thisIntervals.getMap();
			for(Integer k:thisMap.keySet())
			{
				if(p.equals(thisMap.get(k)))
				{
					//说明存在
					flag = 1;
				}
			}
		}
		
		if(flag==0)
		{
			//不存在，则什么也不做
			return;
		}
		//存在，则存储周期长度
		periodics.put(p, length);
		
	}

	@Override
	public long getPeriodicLength(Period p) {
		//判断时间段是否存在
		int flag = 0;
		for(L label:multiIntervalSet.labels())
		{
			IntervalSet<Integer> thisIntervals = multiIntervalSet.intervals(label);
			Map<Integer, Period> thisMap = thisIntervals.getMap();
			for(Integer k:thisMap.keySet())
			{
				if(p.equals(thisMap.get(k)))
				{
					//说明存在
					flag = 1;
				}
			}
		}
		
		if(flag==0)
		{
			//不存在，则返回0
			return 0;
		}
		//存在，则返回周期长度
		return periodics.get(p);
	}

}
