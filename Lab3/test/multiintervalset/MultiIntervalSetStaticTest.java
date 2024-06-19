package multiintervalset;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MultiIntervalSetStaticTest {
	@Test
	public void testEmpty()
	{
		assertTrue(MultiIntervalSet.empty().labels().isEmpty());
	}

}
