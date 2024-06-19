package intervalset;

import static org.junit.Assert.*;

import org.junit.Test;

public class DutyIntervalSetTest {
	
	//测试出现空时间段的时候能否返回true
	//测试没有空时间段的是否能否返回false
	@Test
	public void testCheckBlank() {
		DutyIntervalSet<String> test = new DutyIntervalSet<String>();
		
		test.insert(0, 1, "A");
		test.insert(2, 3, "B");
		assertTrue(test.checkBlank());
		
		test.insert(1, 2, "C");
		assertFalse(test.checkBlank());
	}

	//验证没有出现重叠的时候是否返回false
	//验证出现重叠时是否出现断言错误，即返回true
	@Test
	public void testCheckOverlap() {
		DutyIntervalSet<String> test = new DutyIntervalSet<String>();
		
		test.insert(0, 1, "A");
		test.insert(2, 3, "B");
		assertFalse(test.checkOverlap());
		
        assertThrows(AssertionError.class, () -> {
        test.insert(2, 4, "C");
        });
	}

}
