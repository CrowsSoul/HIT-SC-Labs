package multiintervalset;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Period;

public class CourseIntervalSetTest {

	//测试能否成功设置周期长度
	//测试若没有该时间段，是否什么也不做
	@Test
	public void testMakePeriodic() {
		CourseIntervalSet<String> test = new CourseIntervalSet<String>();
		
		test.insert(0, 1, "A");
		Period p = new Period(0, 1);
		test.makePeriodic(p, 10);
		
		assertEquals(10, test.getPeriodicLength(p));
		
		Period q = new Period(1, 2);
		test.makePeriodic(q, 100);
		
		assertEquals(0, test.getPeriodicLength(q));
	}

	//测试能否正确返回周期长度
	//测试若没有该时间段，是否返回0
	@Test
	public void testGetPeriodicLength() {
		CourseIntervalSet<String> test = new CourseIntervalSet<String>();
		
		test.insert(2, 4, "A");
		Period p = new Period(2, 4);
		test.makePeriodic(p, 10);
		
		assertEquals(10, test.getPeriodicLength(p));
		
		Period q = new Period(99, 100);
		test.makePeriodic(q, 100);
		
		assertEquals(0, test.getPeriodicLength(q));
	}

}
