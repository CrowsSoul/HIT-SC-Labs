package ProcessScheduleApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import intervalset.IntervalSet;
import model.Period;
import model.Process;
import multiintervalset.ProcessIntervalSet;

public class ProcessScheduleApp 
{
	private static Map<Process, Long> processMap = new HashMap<Process, Long>();
	private static long now;
	private static ProcessIntervalSet<Process> set = new ProcessIntervalSet<Process>();
	
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("欢迎来到进程调度管理系统");
    	System.out.println("首先，请增加一组进程");
    	int flag = 0;
    	while(flag == 0)
    	{
    		System.out.println("进程添加：请输入数字选择功能");
    		System.out.println("1.增加一个进程");
    		System.out.println("2.增加完毕");
    		int number = Integer.parseInt(scanner.nextLine());
    		switch(number){
    		case 1:
    			addProcess(scanner);
    			break;
    		case 2:
    			flag = 1;
    			break;
    		default:
    			
    		}
    	}
    	System.out.println("进程已经添加完毕，请选择调度方法");
    	now = 0;
    	System.out.println("1.随机调度");
    	System.out.println("2.最优进程优先");
    	int number = Integer.parseInt(scanner.nextLine());
    	if(number == 1)
    	{
    		while(true)
    		{
        		System.out.println("进程调度：随机调度");
        		System.out.println("1.启用随机调度");
        		System.out.println("2.显示当前调度结果");
        		System.out.println("0.终止程序");
        		int num = Integer.parseInt(scanner.nextLine());
        		switch(num){
        		case 1:
        			//如果要选择进程
        			if(!doNothing())
        			{
        				Process chosenProcess = randomChoose();
        				if(chosenProcess != null)
        				{
        					//说明选择了进程
        					//接下来执行即可
        					doProcess(chosenProcess);
        				}
        				else 
        				{
							//此时说明进程已经全部终止
        					//提示，并结束
        					System.out.println("进程已经全部终止！");
						}
        			}
        			break;
        		case 2:
        			show();
        			break;
        		case 0:
        			System.exit(0);
        			break;
        		default:
        		}
    		}
    	}
    	else if(number == 2)
    	{
    		while(true)
    		{
        		System.out.println("进程调度：最优进程优先");
        		System.out.println("1.启用最优调度");
        		System.out.println("2.显示当前调度结果");
        		System.out.println("0.终止程序");
        		int num = Integer.parseInt(scanner.nextLine());
        		switch(num){
        		case 1:
        			//如果要选择进程
        			if(!doNothing())
        			{
        				Process chosenProcess = bestChoose();
        				if(chosenProcess != null)
        				{
        					//说明选择了进程
        					//接下来执行即可
        					doProcess(chosenProcess);
        				}
        				else 
        				{
							//此时说明进程已经全部终止
        					//提示，并结束
        					System.out.println("进程已经全部终止！");
						}
        			}
        			break;
        		case 2:
        			show();
        			break;
        		case 0:
        			System.exit(0);
        			break;
        		default:
        		}
    		}
    	}
    	else 
    	{
			System.exit(1);
		}

    }
    
    private static void addProcess(Scanner scanner)
    {
    	System.out.println("进行进程的输入...");
    	System.out.println("请输入进程的ID");
    	long ID = Long.parseLong(scanner.nextLine());
    	System.out.println("请输入进程的名称");
    	String name = scanner.nextLine();
    	System.out.println("请输入进程的最短执行时间");
    	long minTime = Long.parseLong(scanner.nextLine());
    	System.out.println("请输入进程的最长执行时间");
    	long maxTime = Long.parseLong(scanner.nextLine());
    	Process newProcess = new Process(ID, name, minTime, maxTime);
    	processMap.put(newProcess, maxTime);
    	System.out.println("该进程输入完毕！");
    }
    
    //调度开始时进行的选择：什么也不做还是执行进程
    //什么也不做，返回true
    //执行进程，返回false
    private static boolean doNothing()
    {
        // 创建一个随机数生成器
    	long seed1 = System.currentTimeMillis();
        Random random1 = new Random(seed1);
        // 生成随机数，范围在0到99之间（包括0和99）
        int randomNumber = random1.nextInt(100);
        // 30%的几率什么也不做
        boolean result = randomNumber < 30 ? true : false;
        //如果什么也不做
        //则只对now进行修改，即随机生成一段时间，进行添加
        //生成范围为50-100
        if(result)
        {
        	long seed2 = System.currentTimeMillis();
            Random random2 = new Random(seed2);
            int emptyLength = random2.nextInt(51)+50;
            now += emptyLength;
            System.out.println("不执行任何进程");
            System.out.println("持续时间为"+emptyLength);
            System.out.println("当前时间为"+now);
        }
        else 
        {
        	System.out.println("准备执行进程...");
		}
        return result;
    }
    
    //随机选择一个进程
    //如果进程全部结束，返回null
    private static Process randomChoose()
    {
    	//首先，将所有未结束的进程加入List
    	List<Process> possibleProcess = new ArrayList<Process>();
    	for(Process p:processMap.keySet())
    	{
    		if(processMap.get(p)!=-1)
    		{
    			possibleProcess.add(p);
    		}
    	}
    	//此时判断是否全部结束
    	if(!possibleProcess.isEmpty())
    	{
    		//从列表中随机选择一个进程
        	long seed = System.currentTimeMillis();
            Random random = new Random(seed);
            int index = random.nextInt(possibleProcess.size());
    		Process chosenProcess = possibleProcess.get(index);
    		return chosenProcess;
    	}
    	else
    	//到这里说明进程已经全部结束
    	{
			return null;
		}
    }
    
    //执行进程
    private static void doProcess(Process p) 
    {
		long leftTime = processMap.get(p);
    	long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        //执行时间至少是1
		long doTime = (long)random.nextInt((int) leftTime)+1;
		//执行后，记录到set中，并更新now
		set.insert(now, now+doTime, p);
		now += doTime;
		//接下来判断是否执行完毕
		//首先计算该进程已经执行的总时间
		long sumTime = set.sumOfPeriod(p);
		if(sumTime>=p.getMinTime()&&sumTime<=p.getMaxTime())
		{
			//说明执行完毕
			//然后将Map中的value变为-1
			processMap.put(p, (long) -1);
			
			//进行说明
			System.out.println("选择执行进程:"+p.getName());
			System.out.println("执行时间:"+doTime);
			System.out.println("该进程被执行完毕！");
		}
		else 
		{
			//说明未执行完毕，即还有剩余时间
			long newLeftTime = p.getMaxTime()-sumTime;
			//将Map中的value更新
			processMap.put(p, newLeftTime);
			
			//进行说明
			System.out.println("选择执行进程:"+p.getName());
			System.out.println("执行时间:"+doTime);
			System.out.println("该进程至多还需时间"+newLeftTime);
		}
		System.out.println("当前时间:"+now);
		
	}
    
    //显示当前时刻前的调度结果
    private static void show()
    {
    	//首先显示当前时刻
    	System.out.println("---===当前时刻:"+now+"===---");
    	//实际上就是对set进行输出
    	for(Process p:processMap.keySet())
    	{
    		long sumLength = 0;
    		if(processMap.get(p)==-1)
    		{
    			//说明执行结束
    			System.out.println("进程:"+p.getName()+" 状态:执行结束");
    		}
    		else 
    		{
    			System.out.println("进程:"+p.getName()+" 状态:未执行结束");
			}
    		
    		//对进程的所有时间段进行输出
    		IntervalSet<Integer> periods = set.intervals(p);
    		Map<Integer,Period> periodMap = periods.getMap();
    		for(Integer i = 0;i<periodMap.size();i++)
    		{
    			long start = periodMap.get(i).getStart();
    			long end = periodMap.get(i).getEnd();
    			long length = end - start;
    			System.out.println("    "+"["+start+"--->"+end+"]"+" 耗时:"+length);
    			sumLength += length;
    		}
    		System.out.println("***该进程总耗时:"+sumLength+"***");
    		System.out.println("-------------------------");
    	}
    }
    
    //最优选择
    private static Process bestChoose()
    {
    	//直接从map中选择剩余时间最小的进程即可
    	//首先，将所有未结束的进程加入List
    	List<Process> possibleProcess = new ArrayList<Process>();
    	for(Process p:processMap.keySet())
    	{
    		if(processMap.get(p)!=-1)
    		{
    			possibleProcess.add(p);
    		}
    	}
    	//此时判断是否全部结束
    	if(!possibleProcess.isEmpty())
    	{
    		//说明有剩余，进行最优选择
    		int index = 0;
    		long min = 999999999;
    		//对List中的进程进行遍历
    		//选择value最小的进程
    		for(int i=0;i<possibleProcess.size();i++)
    		{
    			Process thisProcess = possibleProcess.get(i);
    			if(processMap.get(thisProcess)<min)
    			{
    				min = processMap.get(thisProcess);
    				index = i;
    			}
    		}
    		//返回value最小的进程即可
    		return possibleProcess.get(index);
    	}
    	else
    	//到这里说明进程已经全部结束
    	{
			return null;
		}
    	
    }

}
