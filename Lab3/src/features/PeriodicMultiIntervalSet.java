package features;

import model.Period;

/**
 * 允许周期性时间段的特征接口
 * 定义方法：对某个时间段定义重复周期
 */
public interface PeriodicMultiIntervalSet 
{
	/**
	 * 对某一时间段设置周期
	 * @param p 时间段
	 * @param length 周期
	 */
	public void makePeriodic(Period p,long length);
	
	/**
	 * 获取某个时间段的周期
	 * @param p 时间段
	 * @return 该时间段的周期，如果没有该时间段，返回0
	 */
	public long getPeriodicLength(Period p);
}
