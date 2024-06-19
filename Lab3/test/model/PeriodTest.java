package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PeriodTest {
	
	//检验构造器生成的对象信息与预期是否相等
	@Test
	public void testPeriod() {
		Period test = new Period(100, 109);
		
		assertEquals(100, test.getStart());
		assertEquals(109, test.getEnd());
	}

	//检验观察器的返回值与预期值是否相等
	@Test
	public void testGetStart() {
		Period test = new Period(100, 109);
		
		assertEquals(100, test.getStart());
	}

	@Test
	public void testGetEnd() {
		Period test = new Period(100, 109);
		
		assertEquals(109, test.getEnd());
	}

	//检验等价性
	//两个对象的信息完全相等，是否等价
	//start或者end不相等，是否等价
	@Test
	public void testEquals() 
	{
		Period test1 = new Period(1, 2);
		Period test2 = new Period(1, 2);
		Period test3 = new Period(1, 8);
		
		assertTrue(test1.equals(test2));
		assertFalse(test1.equals(test3));
	}

}
