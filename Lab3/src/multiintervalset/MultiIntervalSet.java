package multiintervalset;

import java.util.Set;

import intervalset.CommonIntervalSet;
import intervalset.IntervalSet;

/**
 * 标签可绑定多个时间段的时间段集合ADT
 * 可变
 * 是一个时间段的集合，多个不同的时间段可以有相同的标签。
 * {[标签1=[起始时间1.1,终止时间1.1],[起始时间1.2,终止时间1.2],...],...}
 * 拥有相同标签的两个时间段不可重叠
 * @param <L> 标签的类型，必须是不可变的
 */
public interface MultiIntervalSet<L>
{
	/**
	 * 创建一个空对象
	 * @param <L> 标签的类型，不可变
	 * @return 一个空的时间段集合对象
	 */
	public static <L> MultiIntervalSet<L> empty()
	{
		return new CommonMultiIntervalSet<L>(new CommonIntervalSet<L>());
	}
	
	/**
	 * 在当前对象中插入新的时间段和标签
	 * @param start 时间段的起始时间，非负，且小于end
	 * @param end 时间段的终止时间，非负，且大于start
	 * @param label 时间段的标签，不能重叠的插入同一标签的时间段
	 */
	public void insert(long start,long end, L label);
	
	/**
	 * 获得当前对象中的标签集合
	 * @return 当前对象的所有标签构成的集合
	 */
	public Set<L> labels();
	
	/**
	 * 从当前对象中移除某个标签所关联的时间段
	 * @param label
	 * @return 如果标签存在，则返回true，若不存在则返回false
	 */
	public boolean remove(L label);
	
	/**
	 * 从当前对象中获取与某个标签所关联的所有时间段
	 * @param label 标签
	 * @return 一个IntervalSet<Integer>类型的时间段集合，
	 * 			其中的时间段按开始时间从小到大的次序排列，标签从0开始。
	 * 			若没有该标签对应的时间段，返回空对象。
	 */
	public IntervalSet<Integer> intervals(L label);
	
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


