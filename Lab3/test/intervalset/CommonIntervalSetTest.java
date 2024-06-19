package intervalset;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Period;

public class CommonIntervalSetTest {

	//测试构造出的对象与原对象的字段是否等价
	@Test
	public void testCommonIntervalSetCommonIntervalSetOfL() {
		IntervalSet<String> old = new CommonIntervalSet<String>();
		old.insert(0, 10, "A");
		old.insert(12, 15, "B");
		IntervalSet<String> test = new CommonIntervalSet<String>(old);
		
		assertEquals(old.getMap(), test.getMap());
	}

	//测试是否为空
	@Test
	public void testCommonIntervalSet() {
		IntervalSet<String> old = new CommonIntervalSet<String>();
		assertTrue(old.getMap().isEmpty());
	}

	//测试标签不存在的情况下能否插入
	//测试标签存在的情况下能否成功替换
	@Test
	public void testInsert() {
		IntervalSet<String> test = new CommonIntervalSet<String>();
		test.insert(0, 2, "A");
		assertEquals(0, test.start("A"));
		assertEquals(2, test.end("A"));
		
		test.insert(10, 11, "A");
		assertEquals(10, test.start("A"));
		assertEquals(11, test.end("A"));
		
	}

	//测试没有标签的情况
	//测试有标签的情况是否全部包括
	@Test
	public void testLabels() {
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		assertTrue(test.labels().isEmpty());
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(100, 191, "C");
		
		assertEquals(3, test.labels().size());
		assertTrue(test.labels().contains("A"));
		assertTrue(test.labels().contains("B"));
		assertTrue(test.labels().contains("C"));
	}

	//测试不存在该标签，是否返回false
	//测试存在该标签，删除后返回true，并测试是否还存在
	@Test
	public void testRemove() {
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(100, 191, "C");
		
		assertFalse(test.remove("D"));
		
		assertTrue(test.remove("A"));
		assertFalse(test.labels().contains("A"));
	}

	//测试不存在该标签，返回-1
	//测试存在该标签，能够正确返回
	@Test
	public void testStart() {
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(100, 191, "C");
		
		assertEquals(-1, test.start("D"));
		assertEquals(0, test.start("A"));
		assertEquals(8, test.start("B"));
		assertEquals(100, test.start("C"));
	}

	//测试不存在该标签，返回-1
	//测试存在该标签，能够正确返回
	@Test
	public void testEnd() {
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		test.insert(0, 2, "A");
		test.insert(8, 9, "B");
		test.insert(100, 191, "C");
		
		assertEquals(-1, test.end("D"));
		assertEquals(2, test.end("A"));
		assertEquals(9, test.end("B"));
		assertEquals(191, test.end("C"));
	}

	//测试对象中的Map是否符合预期
	@Test
	public void testGetMap() {
		IntervalSet<String> old = new CommonIntervalSet<String>();
		old.insert(0, 10, "A");
		old.insert(12, 15, "B");
		Period p1 = new Period(0, 10);
		Period p2 = new Period(12, 15);
		IntervalSet<String> test = new CommonIntervalSet<String>(old);
		
		assertEquals(2, test.getMap().size());
		assertEquals(p1, test.getMap().get("A"));
		assertEquals(p2, test.getMap().get("B"));
		
	}
	
	//测试能否返回最小的start以及最大的end
	//测试对于空对象能否返回-1
	@Test
	public void testGetStartTime()
	{
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		assertEquals(-1, test.getStartTime());
		
		test.insert(2, 8, "A");
		test.insert(3, 11, "B");
		test.insert(14, 15, "C");
		
		assertEquals(2, test.getStartTime());
	}
	@Test
	public void testGetEndTime()
	{
		IntervalSet<String> test = new CommonIntervalSet<String>();
		
		assertEquals(-1, test.getEndTime());
		
		test.insert(2, 8, "A");
		test.insert(3, 11, "B");
		test.insert(14, 15, "C");
		
		assertEquals(15, test.getEndTime());
	}
}
