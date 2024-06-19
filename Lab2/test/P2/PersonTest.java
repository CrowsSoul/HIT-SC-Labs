package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testPerson() {
		Person p = new Person("Alice");
		assertEquals("Alice", p.getName());
	}

	@Test
	public void testGetName() {
		Person p = new Person("Alice");
		assertEquals("Alice", p.getName());
	}

}
