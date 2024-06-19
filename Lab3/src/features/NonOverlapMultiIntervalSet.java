package features;

public interface NonOverlapMultiIntervalSet {
	/**
	 * 检查MutilIntervalSet中是否出现重叠时间段
	 * @return 出现则true，否则false
	 */
	public boolean checkOverlap();

}
