package P1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * boolean isLegalMagicSquare(String fileName)
  boolean generateMagicSquare(int n)
  void main(String[] args)
 * 
 */
public class MagicSquare {
	private static String txtFilePath = "src/P1/txt/";

	public static Boolean isLegalMagicSquare(String fileName)
	{
		//由txt文件夹地址以及txt文件名得到txt文件的相对路径
		String txtPath = MagicSquare.txtFilePath.concat(fileName);
		int[][] numbers = null;
		try 
		{
			numbers = readFromFile(txtPath);
		} 
		catch (IOException e1) 
		{
			System.out.println("文件读取错误！");
			return false;
		}
		catch (ArrayIndexOutOfBoundsException e2)
		{
			System.out.println("非法输入：读入的并非矩阵！(元素缺失或者行列不相等)");
			return false;
		}
		catch (NumberFormatException e3)
		{
			System.out.println("非法输入：元素并非整数或分隔符并非'\\t'!");
			return false;
		}
		//此时以验证输入的矩阵是合法的，则先打印矩阵。
		printSquare(numbers);
		//下面开始验证行列对角线元素之和是否相等
		//sum变量存储元素之和，以第一行为标准;thisSum存储需检验的元素和。
		int sum = 0;
		int thisSum = 0;
		int n = numbers.length;
		for(int j=0;j<n;j++)
		{
			sum += numbers[0][j];
		}
		//对每行进行检验
		for(int i=1;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				thisSum += numbers[i][j];
			}
			if(thisSum != sum)
			{
				return false;
			}
			thisSum = 0;
		}
		//对每列进行检验
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				thisSum += numbers[j][i];
			}
			if(thisSum != sum)
			{
				return false;
			}
			thisSum = 0;
		}
		//对两条对角线进行检验
		for(int i=0;i<n;i++)
		{
			thisSum += numbers[i][i];
		}
		if(thisSum != sum)
		{
			return false;
		}
		thisSum = 0;
		for(int i=0;i<n;i++)
		{
			thisSum += numbers[0+i][n-1-i];
		}
		if(thisSum != sum)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 文件读取：从txt文件中读取矩阵信息，转化为int类型的二维数组
	 * @param txtPath
	 * @return 矩阵元素数组numbers
	 * @throws IOException
	 */
	public static int[][] readFromFile(String txtPath) throws IOException,ArrayIndexOutOfBoundsException,NumberFormatException
	{
		BufferedReader r = new BufferedReader(new FileReader(txtPath));
		String line = r.readLine();
		String[] numbersString = line.split("\t");
		//获取数组的规模为n*n矩阵
		int n = numbersString.length;
		//初始化该矩阵
		int[][] numbers = new int[n][n];
		for(int i=0;i<n;i++)
		{
			numbers[0][i] = Integer.parseInt(numbersString[i]);
		}
		for(int i=1;i<n;i++)
		{
			line = r.readLine();
			numbersString = line.split("\t");
			for(int j=0;j<n;j++)
			{
				numbers[i][j] = Integer.parseInt(numbersString[j]);
			}
			
		}
		return numbers;
	}
	
	/**
	 * 矩阵打印函数
	 * @param numbers
	 */
	public static void printSquare(int[][] numbers)
	{
		int n = numbers.length;
		System.out.println("数组如下：");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.printf("%-4d ",numbers[i][j]);
			}
			System.out.printf("\n");
		}
	}
	
	/**
	 * 奇数阶幻方生成函数
	 * 采用奇数阶幻方生成算法(Merzirac法)
	 * 该矩阵的元素为1到n^2
	 * @param n 矩阵的阶数
	 * @return 是否成功生成
	 */
	public static boolean generateMagicSquare(int n) 
	{
		try 
		{
			int[][] magic = new int[n][n];
			//开始时，在第1行的中间位置放1
			int row = 0, col = n / 2, i, j, square = n * n;
			for (i = 1; i <= square; i++) 
			{
				magic[row][col] = i;
				//如果右上方的数填完(共填n个)，则向下一格继续填写
				if (i % n == 0)
				row++;
				//如果没有填完，则在右上方继续填
				else 
				{
					//如果是第一行，则行下标转移到最后一行的相应位置
					if (row == 0)
					row = n - 1;
					//否则，向上移动一行
					else
					row--;
					//如果是最后一列，则列下标转移到第一列的相应位置
					if (col == (n - 1))
					col = 0;
					//否则，向右移动一列
					else
					col++;
				}
			}
			//由此，n*n个元素填写完毕，下面打印该矩阵
			for (i = 0; i < n; i++) 
			{
				for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
				System.out.println();
			}
			//将矩阵存入6.txt
			writeArrayToTxt(magic, "src/P1/txt/6.txt");
			return true;
		} 
		catch (ArrayIndexOutOfBoundsException e1) 
		{
			System.out.println("数组越界异常：输入的n为偶数！");
			return false;
		}
		catch (NegativeArraySizeException e2)
		{
			System.out.println("数组规模负数异常：输入的n为负数！");
			return false;
		}
	}
	
	/**
	 * txt文件写入函数
	 * 将生成的矩阵写入指定txt文件
	 * @param array 要写入的整数数组
	 * @param filePath txt文件的地址
	 */
    public static void writeArrayToTxt(int[][] array, String filePath) 
    {
    	FileWriter writer = null;
        try 
        {
        	writer = new FileWriter(filePath);
            for (int i = 0; i < array.length; i++) 
            {
                for (int j = 0; j < array[i].length; j++) 
                {
                    writer.write(array[i][j] + "\t");
                }
                writer.write("\n"); // 写入换行符
            }
            writer.close(); // 这里一定要及时关闭文件流，否则文件内容为空！
            System.out.println("数组已成功写入文件：" + filePath);
        } 
        catch (IOException e) 
        {
            System.out.println("写入文件时出错：" + e.getMessage());
        }
    }
    
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入矩阵存储文件名");
		String fileName = scanner.nextLine();
		if(isLegalMagicSquare(fileName))
		{
			System.out.println("该文件存储的是Magic Square！");
		}
		else 
		{
			System.out.println("该文件存储的不是Magic Square！");
		}
		scanner.close();
	}
}
