package features;

/**
 * 不允许有重叠时间段的接口
 * 定义检查是否有重叠时间段的方法
 */
public interface NonOverlapIntervalSet {

	/**
	 * 检查IntervalSet中是否出现重叠时间段
	 * @return 出现则true 否则false
	 */
	public boolean checkOverlap();
	
}
