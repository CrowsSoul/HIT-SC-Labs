package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseTest {
	
	//检验构造器生成对象的各信息与预期是否相等
	@Test
	public void testCourse() {
		Course test = new Course(1, "Chinese", "Jack", "Classroom B");
		
		assertEquals(1, test.getID());
		assertEquals("Chinese", test.getCourseName());
		assertEquals("Jack", test.getTeacherName());
		assertEquals("Classroom B", test.getPosition());
	}

	//检验观察器返回的值与预期值是否相等
	
	@Test
	public void testGetID() {
		Course test = new Course(1, "Chinese", "Jack", "Classroom B");
		
		assertEquals(1, test.getID());
	}

	@Test
	public void testGetCourseName() {
		Course test = new Course(1, "Chinese", "Jack", "Classroom B");
		
		assertEquals("Chinese", test.getCourseName());
	}

	@Test
	public void testGetTeacherName() {
		Course test = new Course(1, "Chinese", "Jack", "Classroom B");
		
		assertEquals("Jack", test.getTeacherName());
	}

	@Test
	public void testGetPosition() {
		Course test = new Course(1, "Chinese", "Jack", "Classroom B");
		
		assertEquals("Classroom B", test.getPosition());
	}
	
	//测试等价方法
	//测试等价的情况
	//测试有任何一个字段不等价的情况
	@Test
	public void testEquals()
	{
		Course test1 = new Course(1, "Chinese", "Jack", "Classroom B");
		Course test2 = new Course(1, "Chinese", "Jack", "Classroom B");
		Course test3 = new Course(1, "Chinese", "Mary", "Classroom B");
		
		assertTrue(test1.equals(test2));
		assertFalse(test1.equals(test3));
	}

}
