/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF（graph） =	单词关联图
    //					graph中的顶点表示单词，边表示相邻单词的出现次数
    // Representation invariant:
    //   图中权值为正整数
    // 	 单词中没有大写字母，但允许出现标点符号
    // Safety from rep exposure:
    //   字段为private final
    //	 getTargets()方法调用了Graph的targets()方法，已进行防御式拷贝
    //	 其余方法接收或者返回不可变类型
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException 
    {
		BufferedReader r = new BufferedReader(new FileReader(corpus));
		String line;//读入的每一行
		List<String> allWords = new ArrayList<String>();//存放所有单词的列表
		//当读到文件末尾时，.readLine会返回null.
        while ((line = r.readLine()) != null) 
        {
            String[] words = line.split("[ \\n\\t]");
            
            // 处理每个分割得到的单词
            for (String word : words) 
            {
            	//转换为小写
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    allWords.add(word);
                }
            }
        }
        r.close();
        // 遍历列表，每次取出两个相邻的单词
        for (int i = 0; i < allWords.size() - 1; i++) 
        {
            String word1 = allWords.get(i);
            String word2 = allWords.get(i + 1);
            
            //调用set函数
            int w = graph.set(word1, word2, 1);
          //如果返回值为0，说明没有该边，置为1
            if(w==0)
            {
            	graph.set(word1, word2, 1);
            }
            //否则已经有了该边，则置为先前的权值加1
            else 
            {
            	graph.set(word1, word2, w+1);
			}
        }
        checkRep();

    }
    
    // checkRep
    private void checkRep()
    {
        Pattern pattern = Pattern.compile("[A-Z]");
    	for(String v:graph.vertices())
    	{
    		//判断没有大写字母
    		Matcher matcher = pattern.matcher(v);
    		assert !matcher.find();
    		
    		//判断权值大于0
    		for(Integer weight:graph.targets(v).values())
    		{
    			assert weight > 0;
    		}
    	}
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	//对输入字符串的单词进行提取
        String[] inputWords = input.split("[ \\n\\t]");
        //要输出的字符串
        StringBuilder output = new StringBuilder();
        for(int i = 0;i<inputWords.length-1;i++)
        {
        	//原始的单词
        	String thisRawWord = inputWords[i];
        	String nextRawWord = inputWords[i+1]; 
        	//转化为小写
        	String thisWord = thisRawWord.toLowerCase();
        	String nextWord = nextRawWord.toLowerCase();
        	
        	//开始寻找thisWord和nextWord之间的“桥词”
        	String middleWord = "";//要插入的“桥词”
        	int maxLength = 0;//最长的路径长
        	for(String middle: graph.targets(thisWord).keySet())
        	{
        		for(String end: graph.targets(middle).keySet())
        		{
        			//确定了middle为一个桥词
        			if(end.equals(nextWord))
        			{
        				//计算路经长
        				int thisLength = graph.targets(thisWord).get(middle)+
        							 graph.targets(middle).get(nextWord);
        				//若大于，则更新middleWord和maxLength
        				if(thisLength > maxLength)
        				{
        					middleWord = middle;
        					maxLength = thisLength;
        				}
        			}
        		}
        	}
        	//非空，则选中桥词，插入
        	if(!middleWord.isEmpty())
        	{
        		output.append(thisRawWord+" "+middleWord+" ");
        	}
        	//否则，没有桥词，用空格分隔
        	else 
        	{
        		output.append(thisRawWord+" ");
			}
        }
        //最后，加入末尾单词
        output.append(inputWords[inputWords.length-1]);
        checkRep();
        return output.toString();
    }
    
    /**
     * 获取vertex的所有出边，包括终点和权值
     * 该方法主要用于测试 
     */
    public Map<String,Integer> getTargets(String vertex)
    {
    	checkRep();
    	return graph.targets(vertex);
    }
    
    // toString()
    @Override public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("所有的单词：\n");
    	for(String v:graph.vertices())
    	{
    		sb.append(v+",");
    	}
    	sb.append("\n单词之间的关联边：\n");
    	for(String v:graph.vertices())
    	{
    		sb.append(v+":\n");
            for (Map.Entry<String, Integer> entry : this.getTargets(v).entrySet()) 
            {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                sb.append("--->" + key + ":" + value + "\n");
            }
    	}
    	checkRep();
    	return sb.toString();
    }
}
