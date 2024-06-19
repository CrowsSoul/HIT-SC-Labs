package features;

/**
 * 不允许空时间段的特征接口
 * 定义检查是否有空时间段的方法
 */
public interface NoBlankIntervalSet 
{
	/**
	 * 检查是否有空时间段
	 * @return 有则为true，否则为false
	 */
	public boolean checkBlank();
}
