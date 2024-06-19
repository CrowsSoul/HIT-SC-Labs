package features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import intervalset.IntervalSet;
import model.Period;

public class NoBlankIntervalSetImpl<L> implements NoBlankIntervalSet
{
	private IntervalSet<L> intervalSet;
	
	//构造方法，接收IntervalSet
	public NoBlankIntervalSetImpl(IntervalSet<L> intervalSet)
	{
		this.intervalSet = intervalSet;
	}
	
	/**
	 * 该方法仅限于检查IntervalSet
	 */
	@Override
	public boolean checkBlank() 
	{
		//检查是否有空，就是看所有的时间段是否“首尾相接”
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

        //然后检查所有时间段是否“首尾相接”
        for(int i=0;i<n-1;i++)
        {
        	if(periods.get(i).getEnd()!=periods.get(i+1).getStart())
        	{
        		return true;
        	}
        }
		return false;
	}
	
}
