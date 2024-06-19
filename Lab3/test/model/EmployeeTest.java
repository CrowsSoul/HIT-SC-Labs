package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmployeeTest {

	//检验构造器生成的对象信息与预期是否相等
	@Test
	public void testEmployee() {
		Employee test = new Employee("Solaer", "Boss", "182-6345-2978");
		
		assertEquals("Solaer", test.getName());
		assertEquals("Boss", test.getPosition());
		assertEquals("182-6345-2978", test.getPhoneNumber());
	}

	//检验观察器的返回值与预期值是否相等
	@Test
	public void testGetName() {
		Employee test = new Employee("Solaer", "Boss", "182-6345-2978");
		
		assertEquals("Solaer", test.getName());
	}

	@Test
	public void testGetPosition() {
		Employee test = new Employee("Solaer", "Boss", "182-6345-2978");
		
		assertEquals("Boss", test.getPosition());
	}

	@Test
	public void testGetPhoneNumber() {
		Employee test = new Employee("Solaer", "Boss", "182-6345-2978");
		
		assertEquals("182-6345-2978", test.getPhoneNumber());
	}
	
	//测试等价方法
	//测试等价的情况
	//测试有任何一个字段不等价的情况
	@Test
	public void testEquals()
	{
		Employee test1 = new Employee("Solaer", "Boss", "182-6345-2978");
		Employee test2 = new Employee("Solaer", "Boss", "182-6345-2978");
		Employee test3 = new Employee("Solaer", "CEO", "182-6345-2978");
		
		assertTrue(test1.equals(test2));
		assertFalse(test1.equals(test3));
	}

}
