package features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import intervalset.IntervalSet;
import model.Period;
import multiintervalset.MultiIntervalSet;

public class NonOverlapMultiIntervalSetImpl<L> 
				implements NonOverlapMultiIntervalSet
{
	private MultiIntervalSet<L> multiIntervalSet;
	
	//构造方法
	public NonOverlapMultiIntervalSetImpl(MultiIntervalSet<L> multiIntervalSet)
	{
		this.multiIntervalSet = multiIntervalSet;
	}
	
	/**
	 * 该方法仅限MultiIntervalSet使用
	 */
	@Override
	public boolean checkOverlap() {
		//需要将全部的时间段取出再检查
		List<Period> periods = new ArrayList<Period>();
		for(L label:multiIntervalSet.labels())
		{
			IntervalSet<Integer> thisIntervals = multiIntervalSet.intervals(label);
			Map<Integer,Period> thisMap = thisIntervals.getMap();
			for(Integer k:thisMap.keySet())
			{
				periods.add(thisMap.get(k));
			}
		}
		
		//然后开始检查即可
        int n = periods.size();
        //n为0，说明空，直接返回false
        if(n==0)
        {
        	return false;
        }
        //对时间段进行排序，起始时间小的在前面
    	for (int i = 0; i < n - 1; ++i)
    	{
    		//记录有序序列最后一个元素的下标
    		int end = i;
    		//待插入的元素
    		Period temp = periods.get(end+1);
    		//单趟排
    		while (end >= 0)
    		{
    			//比插入的数大就向后移
    			if (temp.getStart() < periods.get(end).getStart())
    			{
    				periods.set(end+1, periods.get(end));
    				end--;
    			}
    			//比插入的数小，跳出循环
    			else
    			{
    				break;
    			}
    		}
    		//tem放到比插入的数小的数的后面
    		periods.set(end+1, temp);
    	}
    	
        //然后检查所有时间段
        for(int i=0;i<n-1;i++)
        {
        	if(periods.get(i).getEnd()>periods.get(i+1).getStart())
        	{
        		return true;
        	}
        }
		return false;
	}

}
