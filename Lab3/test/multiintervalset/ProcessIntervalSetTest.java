package multiintervalset;

import static org.junit.Assert.*;

import org.junit.Test;


public class ProcessIntervalSetTest {

	//当没有重叠时是否返回false
	//当重叠时是否会触发断言错误，即返回true
	@Test
	public void testCheckOverlap() {
		ProcessIntervalSet<String> test = new ProcessIntervalSet<String>();
		
		test.insert(0, 1, "A");
		test.insert(2, 3, "B");
		assertFalse(test.checkOverlap());
		
        assertThrows(AssertionError.class, () -> {
        test.insert(2, 4, "C");
        });
	}
	
	//测试没有标签时返回-1
	//测试有标签时能返回正确的值
	@Test
	public void testsumOfPeriod()
	{
		ProcessIntervalSet<String> test = new ProcessIntervalSet<String>();
		
		test.insert(0, 1, "A");
		test.insert(2, 3, "B");
		test.insert(5, 8, "A");
		test.insert(9, 10, "A");
		
		assertEquals(-1, test.sumOfPeriod("C"));
		
		assertEquals(5, test.sumOfPeriod("A"));
	}
	

}
