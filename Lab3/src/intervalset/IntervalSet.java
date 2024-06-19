package intervalset;

import java.util.Map;
import java.util.Set;

import model.Period;

/**
 * 标签与时间段一一对应不可重复的时间段集合ADT
 * 可变
 * 是一个时间段的集合，多个不同的时间段不能有相同的标签。
 * {[标签1=[起始时间1,终止时间1]]，[标签2=[起始时间2,终止时间2]]，...}
 * @param <L> 标签的类型，必须是不可变的
 */
public interface IntervalSet<L> 
{
	/**
	 * 创建一个空对象
	 * @param <L> 标签的类型，不可变
	 * @return 一个空的时间段集合对象
	 */
	public static <L> IntervalSet<L> empty()
	{
		return new CommonIntervalSet<L>();
	}
	
	/**
	 * 在当前对象中插入新的时间段和标签
	 * 如果该标签已经存在，则会将原标签的时间段替换为这一新的
	 * @param start 时间段的起始时间，非负，且小于end
	 * @param end 时间段的终止时间，非负，且大于start
	 * @param label 时间段的标签
	 */
	public void insert(long start,long end, L label);
	
	/**
	 * 获得当前对象中的标签集合
	 * @return 当前对象的所有标签构成的集合，
	 */
	public Set<L> labels();
	
	/**
	 * 从当前对象中移除某个标签所关联的时间段
	 * @param label
	 * @return 如果标签存在，则返回true，若不存在则返回false
	 */
	public boolean remove(L label);
	
	/**
	 * 返回某个标签对应的时间段的开始时间
	 * @param label
	 * @return label标记时间段的起始时间，若不存在，返回-1
	 */
	public long start (L label);
	
	/**
	 * 返回某个标签对应的时间段的终止时间
	 * @param label
	 * @return label标记时间段的终止时间，若不存在，返回-1
	 */
	public long end (L label);
	
	/**
	 * 返回标签对应时间段的所有键值对
	 * @return 标签对应时间段的所有键值对
	 */
	public Map<L, Period> getMap();
	
	/**
	 * 获取起始时间
	 * @return 总时间段的起始时间，即最小的start 若空则返回-1
	 */
	public long getStartTime();
	
	/**
	 * 获取终止时间
	 * @return 总时间段的终止时间，即最大的end 若空则返回-1
	 */
	public long getEndTime();

}
