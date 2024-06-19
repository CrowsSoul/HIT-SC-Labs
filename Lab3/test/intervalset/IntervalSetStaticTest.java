package intervalset;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IntervalSetStaticTest {
	
	@Test
	public void testEmpty()
	{
		assertTrue(IntervalSet.empty().getMap().isEmpty());
	}

}
