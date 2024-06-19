package api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;
import model.Period;

/**
 * 与IntervalSet和MultiIntervalSet相关的API
 * 提供三种方法
 */
public class APIs 
{
	/**
	 * 计算两个时间段的总体相似度
	 * @param <L> 标签类型
	 * @param s1 时间段1 不可为空
	 * @param s2 时间段2 不可为空
	 * @return 二者的相似度
	 */
	public <L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2)
	{
		long overlapLength = 0;
		
		//首先要找出二者的所有共同标签
		Set<L> commonLabels = new HashSet<>(s1.labels());
		commonLabels.retainAll(s2.labels());
		
		//再针对所有共同标签，检查各个时间段
		if(!commonLabels.isEmpty())
		{
			for(L label:commonLabels)
			{
				overlapLength += this.getOverlapLength(s1.intervals(label), s2.intervals(label));
			}
		}
		//如果没有共同标签的话，相似度显然为0
		else 
		{
			return 0;
		}
		
		//计算总长度
		long endTime = s1.getEndTime()>s2.getEndTime()
						?s1.getEndTime():s2.getEndTime();
		long startTime = s1.getStartTime()<s2.getStartTime()
				?s1.getStartTime():s2.getStartTime();
		long allLength = endTime - startTime;
		
		return overlapLength/allLength;
		
		
	}

	//辅助方法，获取重叠的总时长
	private <L> long getOverlapLength(IntervalSet<L> s1,IntervalSet<L> s2)
	{
		//对时间段一一遍历，然后再把重叠的部分相加即可
		Map<L,Period> map1 = s1.getMap();
		Map<L,Period> map2 = s2.getMap();
		
		long ret = 0;
		
		for(L label1:map1.keySet())
		{
			long start1 = map1.get(label1).getStart();
			long end1 = map1.get(label1).getEnd();
			
			for(L label2:map2.keySet())
			{
				long start2 = map2.get(label2).getStart();
				long end2 = map2.get(label2).getEnd();
				
				//判断是否重叠
				//1的左侧与2的右侧重叠
				if(start1<=end2&&end1>=end2&&start2<=start1)
				{
					ret = ret + end2 - start1;
				}
				//1的右侧与2的左侧重叠
				else if(start2>=start1&&start2<=end1&&end2>=end1)
				{
					ret = ret + end1 - start2;
				}
				//1完全包含2
				else if(start1<=start2&&end1>=end2)
				{
					ret = ret + end2 - start2;
				}
				//2完全包含1
				else if(start2<=start1&&end2>=end1)
				{
					ret = ret + end1 - start1;
				}
				//否则没有重叠
				//什么也不做
				else 
				{
				
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * 计算时间冲突比例
	 * @param <L> 标签类型
	 * @param set 时间段集合 不能为空
	 * @return 时间冲突比例 属于[0,1]
	 */
	public <L> double calcConflictRatio(IntervalSet<L> set)
	{
		//计算“总长度”
		long length = set.getEndTime() - set.getStartTime();
		
		//遍历所有时间段，发现“冲突”，则将冲突时间段加入集合
		Map<L,Period> map = set.getMap();
		Set<Period> conflictPeriods = new HashSet<Period>();
		for(L label:map.keySet())
		{
			for(L thisLabel:map.keySet())
			{
				//不是同一标签，且Period等价，说明“冲突”，加入集合
				if(!label.equals(thisLabel)&&map.get(label).equals(map.get(thisLabel)))
				{
					int flag = 0;
					if(!conflictPeriods.isEmpty())
					{
						for(Period q:conflictPeriods)
						{
							if(q.equals(map.get(label)))
							{
								//说明已经存在该时间段
								flag = 1;
							}
						}
					}
					if(flag==0)
					{
						conflictPeriods.add(map.get(label));
					}
				}
			}
		}
		
		//最后计算冲突时间段的总时长
		long conflictLength = 0;
		if(!conflictPeriods.isEmpty())
		{
			for(Period p:conflictPeriods)
			{
				conflictLength += p.getEnd() - p.getStart();
			}
			
			return conflictLength/length;
		}
		else 
		{
			return 0;
		}
	}
	
	/**
	 * 计算时间冲突比例
	 * @param <L> 标签类型
	 * @param set 时间段集合 不可为空
	 * @return 冲突比例 属于[0,1]
	 */
	public <L> double calcConflictRatio(MultiIntervalSet<L> set)
	{
		//计算“总长度”
		long length = set.getEndTime() - set.getStartTime();
		//遍历所有时间段，发现“冲突”，则将冲突时间段加入集合
		Set<Period> conflictPeriods = new HashSet<Period>();
		for(L label:set.labels())
		{
			for(L thisLabel:set.labels())
			{
				if(!label.equals(thisLabel))
				{
					//接下来对两个intervalset的时间段进行一一比较
					//等价的时间段会被放入集合
					IntervalSet<Integer> s1 = set.intervals(label);
					IntervalSet<Integer> s2 = set.intervals(thisLabel);
					
					this.findConflictPeriods(s1, s2, conflictPeriods);
				}
			}
		}
		
		//最后计算冲突时间段的总时长
		long conflictLength = 0;
		if(!conflictPeriods.isEmpty())
		{
			for(Period p:conflictPeriods)
			{
				conflictLength += p.getEnd() - p.getStart();
			}
			
			return conflictLength/length;
		}
		else 
		{
			return 0;
		}

	}
	
	//辅助方法
	//用以在两个IntervalSet之中寻找“冲突”时间段并加入set
	private <L> void findConflictPeriods(IntervalSet<L> s1,IntervalSet<L> s2,Set<Period> set)
	{
		Map<L,Period> map1 = s1.getMap();
		Map<L,Period> map2 = s2.getMap();
		
		for(L label1:map1.keySet())
		{
			for(L label2:map2.keySet())
			{
				//若Period等价，说明“冲突”，加入集合
				if(map1.get(label1).equals(map2.get(label2)))
				{
					int flag = 0;
					if(!set.isEmpty())
					{
						for(Period q:set)
						{
							if(q.equals(map1.get(label1)))
							{
								//说明已经存在该时间段
								flag = 1;
							}
						}
					}
					if(flag==0)
					{
						set.add(map1.get(label1));
					}
				}
			}
		}
	}
	
	/**
	 * 计算空闲时间比例
	 * @param <L> 标签类型
	 * @param set 时间段集合，不能为空
	 * @return 空闲时间比例 属于[0,1]
	 */
	public <L> double calcFreeTimeRatio(IntervalSet<L> set)
	{
		//计算“总长度”
		long length = set.getEndTime() - set.getStartTime();
		//首先，取出所有时间段放在一个列表中
		List<Period> periods = new ArrayList<Period>();
		Map<L,Period> map = set.getMap();
		for(L label:map.keySet())
		{
			periods.add(map.get(label));
		}
		
        int n = periods.size();
        //n为0，说明空，直接返回0
        if(n==0)
        {
        	return 0;
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
    	//列表构建完毕后，通过后面的start减去前面的end的方法计算即可
    	long freeLength = 0;
        for(int i=0;i<n-1;i++)
        {
        	if(periods.get(i).getEnd()<periods.get(i+1).getStart())
        	{
        		freeLength += periods.get(i+1).getStart()-periods.get(i).getEnd();
        	}
        }
        
        return freeLength/length;
	}
	
	public <L> double calcFreeTimeRatio(MultiIntervalSet<L> set)
	{
		//计算“总长度”
		long length = set.getEndTime() - set.getStartTime();
		//首先，取出所有时间段放在一个列表中
		List<Period> periods = new ArrayList<Period>();
		//需要对set进行双层嵌套遍历
		for(L label:set.labels())
		{
			Map<Integer,Period> map = set.intervals(label).getMap();
			for(Integer thisLabel:map.keySet())
			{
				periods.add(map.get(thisLabel));
			}
		}
		
        int n = periods.size();
        //n为0，说明空，直接返回0
        if(n==0)
        {
        	return 0;
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
    	//列表构建完毕后，通过后面的start减去前面的end的方法计算即可
    	long freeLength = 0;
        for(int i=0;i<n-1;i++)
        {
        	if(periods.get(i).getEnd()<periods.get(i+1).getStart())
        	{
        		freeLength += periods.get(i+1).getStart()-periods.get(i).getEnd();
        	}
        }
        
        return freeLength/length;
	}
}
