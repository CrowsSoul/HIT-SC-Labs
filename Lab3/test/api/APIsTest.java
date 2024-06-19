package api;

import static org.junit.Assert.*;

import org.junit.Test;

import intervalset.CommonIntervalSet;
import intervalset.IntervalSet;
import multiintervalset.CommonMultiIntervalSet;
import multiintervalset.MultiIntervalSet;

public class APIsTest {

	//使用指导书中提供的例子测试相似度
	//这样涵盖了标签不匹配，时间段完全重合 以及 时间段完全包含三种情况
	//则需再提供部分的情况即可

	@Test
	public void testSimilarity() {
		APIs api = new APIs();
		MultiIntervalSet<String> s1 = new CommonMultiIntervalSet<String>();
		MultiIntervalSet<String> s2 = new CommonMultiIntervalSet<String>();
		
		s1.insert(0, 5, "A");
		s1.insert(20, 25, "A");
		s1.insert(10, 20, "B");
		s1.insert(25, 30, "B");
		
		s2.insert(0, 5, "C");
		s2.insert(20, 35, "A");
		s2.insert(10, 20, "B");
		
		assertEquals(15/35, api.Similarity(s1, s2),0);
		
		MultiIntervalSet<String> s3 = new CommonMultiIntervalSet<String>();
		MultiIntervalSet<String> s4 = new CommonMultiIntervalSet<String>();
		
		s3.insert(3, 5, "A");
		s4.insert(1, 4, "A");
		
		assertEquals(1/4, api.Similarity(s3, s4),0);
		
	}

	//测试全部冲突 即返回1
	//测试没有任何冲突 即返回0
	//测试存在部分冲突的情况
	@Test
	public void testCalcConflictRatioIntervalSet() {
		APIs api = new APIs();
		IntervalSet<String> s1 = new CommonIntervalSet<String>();
		s1.insert(0, 1, "A");
		s1.insert(0, 1, "B");
		
		assertEquals(1, api.calcConflictRatio(s1),0);
		
		IntervalSet<String> s2 = new CommonIntervalSet<String>();
		s2.insert(0, 1, "A");
		s2.insert(1, 2, "B");
		
		assertEquals(0, api.calcConflictRatio(s2),0);
		
		IntervalSet<String> s3 = new CommonIntervalSet<String>();
		s3.insert(0, 1, "A");
		s3.insert(0, 1, "B");
		s3.insert(0, 1, "C");
		s3.insert(2, 3, "D");
		s3.insert(3, 4, "E");
		s3.insert(3, 4, "F");
		s3.insert(4, 5, "G");
		
		assertEquals(2/5, api.calcConflictRatio(s3),0);
	}
	@Test
	public void testCalcConflictRatioMultiIntervalSet() {
		APIs api = new APIs();
		MultiIntervalSet<String> s1 = new CommonMultiIntervalSet<String>();
		s1.insert(0, 1, "A");
		s1.insert(0, 1, "B");
		
		assertEquals(1, api.calcConflictRatio(s1),0);
		
		MultiIntervalSet<String> s2 = new CommonMultiIntervalSet<String>();
		s2.insert(0, 1, "A");
		s2.insert(1, 2, "B");
		
		assertEquals(0, api.calcConflictRatio(s2),0);
		
		MultiIntervalSet<String> s3 = new CommonMultiIntervalSet<String>();
		s3.insert(0, 1, "A");
		s3.insert(0, 1, "B");
		s3.insert(0, 1, "C");
		s3.insert(2, 3, "A");
		s3.insert(3, 4, "B");
		s3.insert(3, 4, "C");
		s3.insert(4, 5, "A");
		
		assertEquals(2/5, api.calcConflictRatio(s3),0);
	}

	//测试没有任何空闲时间 即返回0
	//测试存在部分空闲时间的情况
	@Test
	public void testCalcFreeTimeRatioIntervalSet() {
		APIs api = new APIs();
		
		IntervalSet<String> s1 = new CommonIntervalSet<String>();
		s1.insert(0, 1, "A");
		assertEquals(0, api.calcFreeTimeRatio(s1),0);
		
		IntervalSet<String> s2 = new CommonIntervalSet<String>();
		s2.insert(0, 1, "A");
		s2.insert(3, 5, "B");
		s2.insert(2, 4, "C");
		assertEquals(1/5, api.calcFreeTimeRatio(s2),0);
	}
	//测试没有任何空闲时间 即返回0
	//测试存在部分空闲时间的情况
	@Test
	public void testCalcFreeTimeRatioMultiIntervalSet() {
		APIs api = new APIs();
		
		MultiIntervalSet<String> s1 = new CommonMultiIntervalSet<String>();
		s1.insert(0, 1, "A");
		assertEquals(0, api.calcFreeTimeRatio(s1),0);
		
		MultiIntervalSet<String> s2 = new CommonMultiIntervalSet<String>();
		s2.insert(0, 1, "A");
		s2.insert(3, 5, "A");
		s2.insert(2, 4, "C");
		assertEquals(1/5, api.calcFreeTimeRatio(s2),0);
	}

}
