package multiintervalset;

import static org.junit.Assert.*;

import org.junit.Test;

import intervalset.CommonIntervalSet;
import intervalset.IntervalSet;

public class CommonMultiIntervalSetTest {

	//测试目标对象与参数中的信息是否一致
	//包括标签个数、每个标签是否相同
	//标签对应的时间段是否完全相同
	@Test
	public void testCommonMultiIntervalSet() {
		IntervalSet<String> old = new CommonIntervalSet<String>();
		old.insert(0, 10, "A");
		old.insert(12, 15, "B");
		MultiIntervalSet<String> test = new CommonMultiIntervalSet<String>(old);
		
		assertEquals(2, test.labels().size());
		assertTrue(test.labels().contains("A"));
		assertTrue(test.labels().contains("B"));
		
		assertEquals(0,test.intervals("A").start(0));
		assertEquals(12,test.intervals("B").start(0));
		assertEquals(10,test.intervals("A").end(0));
		assertEquals(15,test.intervals("B").end(0));
	}

	//测试标签不存在的情况下能否插入
	//测试对同一标签能否插入多个不重叠的时间段
	@Test
	public void testInsert() {
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		test.insert(100, 191, "A");
		assertEquals(100, test.intervals("A").start(0));
		assertEquals(191, test.intervals("A").end(0));
		
		test.insert(0, 2, "B");
		test.insert(3, 4, "B");
		assertEquals(0, test.intervals("B").start(0));
		assertEquals(2, test.intervals("B").end(0));
		assertEquals(3, test.intervals("B").start(1));
		assertEquals(4, test.intervals("B").end(1));
	}

	//测试无标签返回空集合
	//测试存在一个标签和重复标签是能否正确返回
	@Test
	public void testLabels() {
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		assertTrue(test.labels().isEmpty());
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(3, 4, "B");
		
		assertTrue(test.labels().contains("A"));
		assertTrue(test.labels().contains("B"));
		
	}

	//测试不存在该标签是否返回false
	//测试该标签只有一个时间段能否被移除，返回true
	//测试该标签有多个时间段能否全被移除，返回true
	@Test
	public void testRemove() {
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(3, 4, "B");
		
		assertFalse(test.remove("C"));
		
		assertTrue(test.remove("A"));
		assertFalse(test.labels().contains("A"));
		assertTrue(test.intervals("A").getMap().isEmpty());
		
		assertTrue(test.remove("B"));
		assertFalse(test.labels().contains("B"));
		assertTrue(test.intervals("B").getMap().isEmpty());
		
	}

	//测试不存在该标签则返回空对象
	//测试该标签只有一个时间段的情况
	//测试改标签有多个时间段的情况下，是否按照初始时间从小到大排序
	@Test
	public void testIntervals() {
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(3, 4, "B");
		test.insert(11, 100, "B");
		
		assertTrue(test.intervals("C").getMap().isEmpty());
		
		assertEquals(0, test.intervals("A").start(0));
		assertEquals(2, test.intervals("A").end(0));
		
		assertEquals(3, test.intervals("B").start(0));
		assertEquals(4, test.intervals("B").end(0));
		assertEquals(8, test.intervals("B").start(1));
		assertEquals(9, test.intervals("B").end(1));
		assertEquals(11, test.intervals("B").start(2));
		assertEquals(100, test.intervals("B").end(2));
	}

	//测试能否返回最小的start以及最大的end
	//测试对于空对象能否返回-1
	@Test
	public void testGetStartTime()
	{
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		assertEquals(-1, test.getStartTime());
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(3, 4, "B");
		test.insert(11, 100, "B");
		
		assertEquals(0, test.getStartTime());
	}
	@Test
	public void testGetEndTime()
	{
		MultiIntervalSet<String> test = MultiIntervalSet.empty();
		
		assertEquals(-1, test.getEndTime());
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(3, 4, "B");
		test.insert(11, 100, "B");
		
		assertEquals(100, test.getEndTime());
	}
}
