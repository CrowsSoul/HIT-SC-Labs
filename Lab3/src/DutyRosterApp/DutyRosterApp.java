package DutyRosterApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import intervalset.DutyIntervalSet;
import model.Employee;


@SuppressWarnings("serial")
public class DutyRosterApp extends JFrame {
	
	static private LocalDate beginDate;
	static private LocalDate endDate;
	static private List<Employee> employeeList = new ArrayList<Employee>();
	static private DutyIntervalSet<Employee> intervalSet = new DutyIntervalSet<Employee>();
	
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("欢迎来到排班管理系统");
    	System.out.println("请选择功能:");
    	System.out.println("1.手动输入");
    	System.out.println("2.从文件读入");
    	int num = Integer.parseInt(scanner.nextLine());
    	if(num == 2)
    	{
    		System.out.println("请确保数据文件在项目的data目录下！");
    		System.out.println("请输入文件名，结尾是.txt");
    		String filePath = "data/";
    		String fileName = scanner.nextLine();
    		filePath = filePath.concat(fileName);
    		String[] content = readFileContent(filePath);
    		parsePeriod(periodContent(content));
    		parseEmployee(employeeContent(content));
    		parseRoster(rosterContent(content));
    		System.out.println("读取完成！");
    		createWindow();
    		scanner.close();
    	}
    	else
    	{
	    	System.out.println("首先，请为排班表设置起始时间和终止时间，格式为yyyy-MM-dd");
	    	System.out.println("起始时间：");
	    	readBeginDate(scanner.nextLine());
	        System.out.println("终止时间：");
	        readEndDate(scanner.nextLine());

	        while(true)
	        {
	            System.out.println("接下来请输入数字以选择功能");
	            System.out.println("1.增加员工");
	            System.out.println("2.删除员工");
	            System.out.println("3.增加排班记录");
	            System.out.println("4.删除排班记录");
	            System.out.println("5.检查排班表是否已满");
	            System.out.println("6.随机生成排班表");
	            System.out.println("7.显示排班表");
	            System.out.println("0.退出");
	            int number = Integer.parseInt(scanner.nextLine());
	            
	            switch (number) {
	            case 1:
	                addEmployee(scanner);
	                break;
	            case 2:
	                deleteEmployee(scanner);
	                break;
	            case 3:
	                addRecord(scanner);
	                break;
	            case 4:
	                deleteRecord(scanner);
	                break;
	            case 5:
	            	checkFilled();
	                break;
	            case 6:
	            	randomGenerate();
	            	break;
	            case 7:
	            	createWindow();
	            	break;
	            case 0:
	            	scanner.close();
	            	System.exit(0);
	            	break;
	            default:
	            	System.exit(0);
	            }
	        }
    	}
    }
    
    private static void readBeginDate(String input) {
        try {
            // 解析用户输入的日期字符串为 LocalDate 对象
            beginDate = LocalDate.parse(input);
            // 打印解析后的 LocalDate 对象
            System.out.println("解析后的日期为：" + beginDate);
        } catch (Exception e) {
        }
	}
    private static void readEndDate(String input) {
        try {
            // 解析用户输入的日期字符串为 LocalDate 对象
            endDate = LocalDate.parse(input);
            // 打印解析后的 LocalDate 对象
            System.out.println("解析后的日期为：" + endDate);
        } catch (Exception e) {
        }
		
	}
    
    //在，返回下标
    //不在，返回-1
    private static int inEmployeeList(String name)
    {
    	int index = -1;
        for (int i = 0; i < employeeList.size(); i++) {
            Employee item = employeeList.get(i);
            if(item.getName().equals(name))
    		{
            	index = i;
    		}
        }
        return index;
    }
    
    private static void addEmployee(Scanner scanner)
    {
    	System.out.println("请输入要添加的员工的相关信息");
    	System.out.println("请输入员工的姓名");
    	String employeeName = scanner.nextLine();
    	System.out.println("请输入员工的职位");
    	String employeePosition = scanner.nextLine();
    	System.out.println("请输入员工的电话号码");
    	String employeePhoneNumber = scanner.nextLine();
    	Employee newEmployee = new Employee(employeeName, employeePosition, employeePhoneNumber);
    	employeeList.add(newEmployee);
    }

    private static void deleteEmployee(Scanner scanner)
    {
    	System.out.println("请输入要删除的员工");
    	String employeeName = scanner.nextLine();
    	int index = inEmployeeList(employeeName);
    	if(index==-1)
    	{
    		System.out.println("员工表中没有该员工");
    		return;
    	}
    	//到这里，说明员工表中有该员工，则需要判断排班表中有没有
    	if(intervalSet.labels().contains(employeeList.get(index)))
    	{
    		System.out.println("排班表中有该员工，请先从排班表中删除！");
    		return;
    	}
    	int i = 0;
        for (i = 0; i < employeeList.size(); i++) {
            Employee item = employeeList.get(i);
            if(item.getName().equals(employeeName))
    		{
            	break;
    		}
        }
        employeeList.remove(i);
        System.out.println("删除成功");
    }
    
    private static void addRecord(Scanner scanner)
    {
    	System.out.println("请输入员工的姓名");
    	String employeeName = scanner.nextLine();
    	int index = inEmployeeList(employeeName);
    	if(index==-1)
    	{
    		System.out.println("员工表中没有该员工！");
    		return;
    	}
    	//此时，说明有,则需要添加。
    	//要求输入起始时间与持续天数
    	System.out.println("请输入对该员工值班安排的起始时间,格式为yyyy-MM-dd");
    	String startDateInput = scanner.nextLine();
    	LocalDate startDate = LocalDate.parse(startDateInput);
    	long start = beginDate.until(startDate, ChronoUnit.DAYS);
    	
    	System.out.println("请输入对该员工值班安排持续时间，单位为天");
    	long length = Long.parseLong(scanner.nextLine());
    	long end = start + length;
    	
    	intervalSet.insert(start, end, employeeList.get(index));
    	System.out.println("添加完成！");
    	
    }
    
    private static void deleteRecord(Scanner scanner)
    {
    	System.out.println("请输入员工的姓名");
    	String employeeName = scanner.nextLine();
    	int index = inEmployeeList(employeeName);
    	if(index==-1)
    	{
    		System.out.println("员工表中没有该员工！");
    		return;
    	}
    	//此时，说明有,则需要删除。
    	//要求输入起始时间与持续天数e
    	intervalSet.remove(employeeList.get(index));
    	System.out.println("删除完成！");
    }
    
    private static void createWindow()
    {
    	List<Object[]> dutyRosterData = new ArrayList<Object[]>();
    	for(Employee e:intervalSet.labels())
    	{
    		long start = intervalSet.start(e);
    		long end = intervalSet.end(e);
    		for(long i = start;i<end;i++)
    		{
    			LocalDate date = beginDate.plusDays(i);
    			Object[] oneRecord = {date,e.getName(),e.getPosition(),e.getPhoneNumber()};
    			dutyRosterData.add(oneRecord);
    		}
    	}
    	
	  SwingUtilities.invokeLater(() -> {
	  	new DutyRosterWindow(dutyRosterData);
	  });
    }
    
    private static void checkFilled()
    {
    	long length = beginDate.until(endDate, ChronoUnit.DAYS);
    	System.out.println("下面的日期还没有被排班:");
    	int emptyDays = 0;
    	for(int i=0;i<=length;i++)
    	{
    		LocalDate emptyDate = beginDate.plusDays(i);
    		//不在其中，说明这一天“空闲”
    		if(!isInPeriod(emptyDate))
    		{
    			System.out.println(emptyDate.toString());
    			emptyDays++;
    		}
    	}
    	length++;
    	System.out.println("没有被排班的时间段所占比例为"+emptyDays+"/"+length);
    }
    
    private static boolean isInPeriod(LocalDate date)
    {
    	//先计算出应该的数
    	long distance = beginDate.until(date, ChronoUnit.DAYS);
    	//进行遍历，判断是否在其中
    	boolean bool = false;
    	for(Employee e:intervalSet.labels())
    	{
    		//时间段左闭右开，所以这样判断
    		if(distance>=intervalSet.start(e)&&distance<intervalSet.end(e))
    		{
    			bool = true;
    		}
    	}
    	return bool;
    }
    
    private static void randomGenerate()
    {
    	//根据员工个数，对时间进行划分
        long min = 1; // 最小值
        long max = beginDate.until(endDate, ChronoUnit.DAYS); // 最大值
        int n = employeeList.size()-1; // 生成的数的个数，为员工数-1


        ArrayList<Long> generatedNumbers = new ArrayList<>();
        Random random = new Random();
        while (generatedNumbers.size() < n) {
            long randomNumber = random.nextInt((int) (max - min + 1)) + min;
            if (!generatedNumbers.contains(randomNumber)) {
                generatedNumbers.add(randomNumber);
            }
        }
        
        generatedNumbers.add((long) 0);
        generatedNumbers.add(beginDate.until(endDate, ChronoUnit.DAYS)+1);
        
        Collections.sort(generatedNumbers);
        
        DutyIntervalSet<Employee> randomIntervalSet = new DutyIntervalSet<Employee>();
        //遍历时间段，加入
        for(int i=0;i<employeeList.size();i++)
        {
        	randomIntervalSet.insert(generatedNumbers.get(i), generatedNumbers.get(i+1), employeeList.get(i));
        }
        
        System.out.println("随机生成完成！");
        
    	List<Object[]> dutyRosterData = new ArrayList<Object[]>();
    	for(Employee e:randomIntervalSet.labels())
    	{
    		long start = randomIntervalSet.start(e);
    		long end = randomIntervalSet.end(e);
    		for(long i = start;i<end;i++)
    		{
    			LocalDate date = beginDate.plusDays(i);
    			Object[] oneRecord = {date,e.getName(),e.getPosition(),e.getPhoneNumber()};
    			dutyRosterData.add(oneRecord);
    		}
    	}
        
	  SwingUtilities.invokeLater(() -> {
	  	new DutyRosterWindow(dutyRosterData);
	  });
    }
    
    private static String[] readFileContent(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder contentBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            
            // 将文件内容分割成行
            String[] lines = contentBuilder.toString().split("\n");
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String employeeContent(String[] fileContent) {
        StringBuilder employeeSection = new StringBuilder();
        boolean inEmployeeSection = false;
        
        for (String line : fileContent) 
        {
            if (line.startsWith("Employee{")) {
                inEmployeeSection = true;
            } else if (inEmployeeSection) {
            	if(line.startsWith("}"))
            	{
            		break;
            	}
            	else 
            	{
            		employeeSection.append(line).append("\n");
				}
            }
        }

        // 去除末尾的换行符
        if (employeeSection.length() > 0) {
            employeeSection.setLength(employeeSection.length() - 1);
        }

        return employeeSection.toString();
    }

    private static String rosterContent(String[] fileContent) {
        StringBuilder rosterSection = new StringBuilder();
        boolean inRosterSection = false;

        for (String line : fileContent) 
        {
            if (line.startsWith("Roster{")) {
            	inRosterSection = true;
            } else if (inRosterSection) {
            	if(line.startsWith("}"))
            	{
            		break;
            	}
            	else 
            	{
            		rosterSection.append(line).append("\n");
				}
            }
        }
        // 去除末尾的换行符
        if (rosterSection.length() > 0) {
            rosterSection.setLength(rosterSection.length() - 1);
        }

        return rosterSection.toString();
    }
    
    private static String periodContent(String[] fileContent)
    {

        for (String line : fileContent) 
        {
            if (line.startsWith("Period")) 
            {
            	return line;

            }
        }
		return null;
    }
    
    private static void parseEmployee(String content) {
        Pattern employeeInfoPattern = Pattern.compile("(\\w+)\\{(.*?),([\\d-]+)\\}");
        Matcher matcher = employeeInfoPattern.matcher(content);
        while (matcher.find()) {
            String name = matcher.group(1);
            String position = matcher.group(2);
            String phoneNumber = matcher.group(3);

            employeeList.add(new Employee(name, position, phoneNumber));
        }
    }
    
    private static void parsePeriod(String content) 
    {
      Pattern periodPattern = Pattern.compile("Period\\{([^{}]+)\\}");
      Matcher matcher = periodPattern.matcher(content);
      while (matcher.find()) {
          String period = matcher.group(1);
          String[] dates = period.split(",");
          String begin = dates[0].trim();
          String end = dates[1].trim();
          beginDate = LocalDate.parse(begin);
          endDate = LocalDate.parse(end);
      }
    }

    private static void parseRoster(String content) {
    	
        Pattern rosterInfoPattern = Pattern.compile("(\\w+)\\{([^{}]+)\\}");
        Matcher matcher = rosterInfoPattern.matcher(content);
        while (matcher.find()) {
            String name = matcher.group(1);
            String dates = matcher.group(2);
            String[] dateParts = dates.split(",");
            String begin = dateParts[0].trim();
            String end = dateParts[1].trim();
            
            LocalDate beginTime = LocalDate.parse(begin);
            LocalDate endTime = LocalDate.parse(end);
            endTime = endTime.plusDays(1);
            
            long startNum = beginDate.until(beginTime, ChronoUnit.DAYS);
            long endNum = beginDate.until(endTime, ChronoUnit.DAYS);
            int index = inEmployeeList(name);
            
            intervalSet.insert(startNum, endNum, employeeList.get(index));
            
           
        }
    }
}

