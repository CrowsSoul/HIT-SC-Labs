package features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import intervalset.IntervalSet;
import model.Period;

public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet 
{
	
	private IntervalSet<L> intervalSet;

	//构造方法
	public NonOverlapIntervalSetImpl(IntervalSet<L> intervalSet)
	{
		this.intervalSet = intervalSet;
	}
	
	/**
	 * 该方法仅限IntervalSet使用
	 */
	@Override
	public boolean checkOverlap() {
		//检查是否重叠，就是按start从小到大排序后看上一个的end是否小于下一个的start
		//首先，取出所有时间段放在一个列表中
		List<Period> periods = new ArrayList<Period>();
		Map<L,Period> map = intervalSet.getMap();
		for(L label:map.keySet())
		{
			periods.add(map.get(label));
		}
		
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
