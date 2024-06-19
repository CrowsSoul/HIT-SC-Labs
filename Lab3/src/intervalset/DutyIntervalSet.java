package intervalset;

import java.util.Set;

import features.IDutyIntervalSet;
import features.NoBlankIntervalSetImpl;
import features.NonOverlapIntervalSetImpl;

/**
 * 面向值班表应用的ADT
 * 继承特征接口：时间段不可重叠，没有空时间段
 * @param <L> 标签类型
 */
public class DutyIntervalSet<L> extends CommonIntervalSet<L>
								implements IDutyIntervalSet
{
	private NonOverlapIntervalSetImpl<L> nonOverlapIntervalSetImpl;
	private NoBlankIntervalSetImpl<L> noBlankIntervalSetImpl;
	
    // Abstraction function:
    //   AF(IntervalMap) =	
	//		{[标签1=[起始时间1,终止时间1]]，[标签2=[起始时间2,终止时间2]]，...}
	//		即由标签标记的时间段集合
	//	 标签为key，时间段为value。时间段类型包括start和end
    // Representation invariant:
	// 	 同一标签不可绑定到多个时间段
	//   时间段不可重叠
	//   没有空时间段
	// 	 对任何时间段，有end>start>=0
    
    // Safety from rep exposure:
    //   所有的字段均为private
    //	 所有需要获取字段信息的方法均使用防御式拷贝处理
    //	 Map中所有元素的类型为L和Period，均不可变。
    
    // constructor
	//构造方法
	//确立委派关系
	public DutyIntervalSet()
	{
		super();
		this.nonOverlapIntervalSetImpl = new NonOverlapIntervalSetImpl<L>(this);
		this.noBlankIntervalSetImpl = new NoBlankIntervalSetImpl<L>(this);
	}
	
	//checkRep
	//只需检查是否存在重叠
	private void checkRep()
	{
		assert !checkOverlap();
	}
	
	@Override
	public boolean checkBlank() {
		//委派完成
		return noBlankIntervalSetImpl.checkBlank();
	}

	@Override
	public boolean checkOverlap() {
		//委派完成
		return nonOverlapIntervalSetImpl.checkOverlap();
	}
	
	@Override
	public void insert(long start, long end, L label) {
		super.insert(start, end, label);
		checkRep();
	}
	
	@Override
	public Set<L> labels() {
		checkRep();
		return super.labels();
	}
	
	@Override
	public boolean remove(L label) {
		checkRep();
		return super.remove(label);
	}
	
	@Override
	public long start(L label) {
		checkRep();
		return super.start(label);
	}
	
	@Override
	public long end(L label) {
		checkRep();
		return super.end(label);
	}
	

}
