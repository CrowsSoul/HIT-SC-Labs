/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //	对于构造方法：
	//	检验能否根据语料库正确生成单词关联图：
	//		检验顶点有指向自己的边的情况
	//		检验有多个桥词的情况
	//		检验顶点标签是否全部为小写
	//   测试异常抛出：
	//		如果未能找到或者读取语料库文件能否抛出指定异常
	// 	对于poem()方法：
	//	检验能否利用单词关联图语料库生成目标诗歌
	//		对于多个桥词的情况，能否选择权重最大的路径插入s
	//		能否保留原语句中的大小写并使桥接词全部小写
	//		单词之间为空格  
	//		需要插入多个单词的情况
	//		不需要插入单词的情况
	//	对于toString()方法：
	// 	检验是否符合预期形式
	//	对于getTargets方法：
	//	该方法主要用于辅助测试，采用与Graph的targets方法类似的策略。
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests
    
    @Test
    public void testGetTargets() throws IOException
    {
    	File t = new File("test/P1/poet/dog-and-cat.txt");
    	GraphPoet poet = new GraphPoet(t);
    	
    	assertTrue(poet.getTargets("the").keySet().contains("red"));
    	assertTrue(poet.getTargets("the").keySet().contains("lazy"));
    	assertTrue(poet.getTargets("the").keySet().contains("yellow"));
    	
    	assertEquals(Integer.valueOf(8), poet.getTargets("the").get("red"));
    	assertEquals(Integer.valueOf(9), poet.getTargets("the").get("lazy"));
    	assertEquals(Integer.valueOf(3), poet.getTargets("the").get("yellow"));
    }
    
    @Test
    public void testGraphPoet() throws IOException
    {
    	//指向自己的边的情况
    	File t1 = new File("test/P1/poet/good.txt");
    	GraphPoet poet1 = new GraphPoet(t1);
    	
    	assertEquals(Integer.valueOf(2), poet1.getTargets("good").get("good"));
    	
    	//异常抛出
    	File t2 = new File("test/P1/poet/non.txt");
    	assertThrows(IOException.class, ()->{new GraphPoet(t2);});
    	
    	//检验剩余情况
    	File t = new File("test/P1/poet/dog-and-cat.txt");
    	GraphPoet poet = new GraphPoet(t);
    	
    	//多个词桥
    	assertEquals(Integer.valueOf(1), poet.getTargets("dog").get("runs"));
    	assertEquals(Integer.valueOf(3), poet.getTargets("dog").get("jumps"));
    	
    	//顶点标签是否全部小写
    	assertTrue(poet.getTargets("over").keySet().contains("the"));
    	assertFalse(poet.getTargets("over").keySet().contains("The"));
    	
    	assertTrue(poet.getTargets("the").keySet().contains("red"));
    	assertFalse(poet.getTargets("the").keySet().contains("Red"));
    }
    
    @Test
    public void testPoem() throws IOException
    {
    	File t = new File("test/P1/poet/dog-and-cat.txt");
    	GraphPoet poet = new GraphPoet(t);
    	
    	//单词之间为空格，下面的用例已经覆盖
    	
    	//测试多个词桥选择权重最大的
    	assertEquals("dog jumps over", poet.poem("dog over"));
    	assertEquals("the lazy cat.", poet.poem("the cat."));
    	
    	//测试大小写相关功能
    	assertEquals("Dog quickly Jumps", poet.poem("Dog Jumps"));
    	assertEquals("the lazy Cat.", poet.poem("the Cat."));
    	
    	//测试需要插入多个单词以及不需要插入单词的情况(over与the之间)
    	assertEquals("dog jumps over the lazy cat.", poet.poem("dog over the cat."));
    }
    
    @Test
    public void testToString() throws IOException
    {
    	File t = new File("test/P1/poet/testString.txt");
    	GraphPoet poet = new GraphPoet(t);
    	//构建目标输出
    	String s = "所有的单词：\n"
    			+ "a,b,c,\n"
    			+ "单词之间的关联边：\n"
    			+ "a:\n"
    			+ "--->b:2\n"
    			+ "b:\n"
    			+ "--->c:1\n"
    			+ "c:\n"
    			+ "--->a:1\n";
    	assertEquals(s, poet.toString());
    }
}
