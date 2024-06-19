package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessTest {

	
	//检验构造器生成的对象信息与预期是否相等
	@Test
	public void testProcess() {
		Process test = new Process(1, "EldenRing", 9000, 10000);
		
		assertEquals(1, test.getID());
		assertEquals("EldenRing", test.getName());
		assertEquals(9000, test.getMinTime());
		assertEquals(10000, test.getMaxTime());
	}

	//检验观察器的返回值与预期值是否相等
	@Test
	public void testGetID() {
		Process test = new Process(1, "EldenRing", 9000, 10000);
		
		assertEquals(1, test.getID());
	}

	@Test
	public void testGetName() {
		Process test = new Process(1, "EldenRing", 9000, 10000);
		
		assertEquals("EldenRing", test.getName());
	}

	@Test
	public void testGetMinTime() {
		Process test = new Process(1, "EldenRing", 9000, 10000);
		
		assertEquals(9000, test.getMinTime());
	}

	@Test
	public void testGetMaxTime() {
		Process test = new Process(1, "EldenRing", 9000, 10000);
		
		assertEquals(10000, test.getMaxTime());
	}
	
	//检验等价性
	//两个对象的信息完全相等，是否等价
	//start或者end不相等，是否等价
	@Test
	public void testEquals() 
	{
		Process test1 = new Process(1, "EldenRing", 9000, 10000);
		Process test2 = new Process(1, "EldenRing", 9000, 10000);
		Process test3 = new Process(1, "Dark Souls III", 9000, 10000);
		
		assertTrue(test1.equals(test2));
		assertFalse(test1.equals(test3));
	}
	
	

}
