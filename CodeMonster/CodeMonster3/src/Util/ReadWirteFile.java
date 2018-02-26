package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadWirteFile {
	public static void main(String []arg){
		System.out.println(ReadAll());
	}
/*返回本周所有代码量*/
	public static  int ReadAllWeek(){
		String[] numbers=ReadWeek();
		int a=0;
		for(int i=0;i<numbers.length;i++){
			a+=Integer.valueOf(numbers[i]);
		}
		return a;
	}
/*返回本月所有代码量*/
	public static  int ReadAllMonth(){
		String[] numbers=ReadMonth();
		int a=0;
		for(int i=0;i<numbers.length;i++){
			a+=Integer.valueOf(numbers[i]);
		}
		return a;
	}
/*返回今天代码量*/
	public static int ReadToday(){
		int CodeNumber=0;
		try {
			BufferedReader buf=new BufferedReader(new FileReader("today.txt"));
			String a=buf.readLine();
			CodeNumber=Integer.valueOf(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CodeNumber;
	}
/*返回所有代码量*/
	public static int ReadAll(){
		int CodeNumber=0;
		try {
			BufferedReader buf=new BufferedReader(new FileReader("log.txt"));
			String a=buf.readLine();
			CodeNumber=Integer.valueOf(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CodeNumber;
	}
/* 直接改变今天的行数*/
	public static void WriteToday(int number){
		try{
			FileWriter input = new FileWriter("today.txt");
			input.write(String.valueOf(number));
			input.write("\r\n");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			input.write(df.format(new Date()));
			input.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
/* 直接改变总行数	 */
	public static void WriteAll(int number){
		try{
			FileWriter input = new FileWriter("log.txt");
			input.write(String.valueOf(number));
			input.write("\r\n");
			input.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
/* 今天日期加加，判断是否是新的一天，如果是新的一天就将周，月都改变*/
	public static void AddToday(){
		int CodeNumber =ReadToday();
		String date=ReadTodayDate();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String todaydate=df.format(new Date());
			try{
				FileWriter input = new FileWriter("today.txt");
				if(todaydate.equals(date)){
					input.write(String.valueOf(CodeNumber+1));
					AddWeek();
					AddMonth();
				}else{
					input.write("1");
					ChangeWeek(CodeNumber);
					ChangeMonth(CodeNumber);
				}
				input.write("\r\n");
				input.write(todaydate);
				input.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	/*本周最后一天代码行数加1，也就是今天*/
	public static void AddWeek(){
		String[] CodeNumber =ReadWeek();
		try{
		FileWriter input = new FileWriter("week.txt");
			for(int j=0;j<7;j++){
				if(CodeNumber[j]!=null){
					if(j==6){
						int a=Integer.valueOf(CodeNumber[j])+1;
						input.write(String.valueOf(a));
					}else
						input.write(CodeNumber[j]);
						input.write("\r\n");
				}
			}
		input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
/*本月最后一天代码行数加1，也就是今天 */
	public static void AddMonth(){
		String[] CodeNumber =ReadMonth();
		try{
		FileWriter input = new FileWriter("month.txt");
			for(int j=0;j<30;j++){
				if(CodeNumber[j]!=null){
					if(j==29){
						int a=Integer.valueOf(CodeNumber[j])+1;
						input.write(String.valueOf(a));
					}else
						input.write(CodeNumber[j]);
						input.write("\r\n");
				}
			}
		input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
/* 改变当前周最后一天，进行平移*/
	public static void ChangeWeek(int daynumber){
		String[] CodeNumber =ReadWeek();
		String [] NewCodeNumber=new String[7];
		try{
		FileWriter input = new FileWriter("week.txt");
		for(int i=0;i<6;i++){
			NewCodeNumber[i]=CodeNumber[i+1];
		}
		NewCodeNumber[6]=String.valueOf(daynumber);
			for(int j=0;j<7;j++){
				if(NewCodeNumber[j]!=null){
					input.write(NewCodeNumber[j]);
					input.write("\r\n");
				}
			}
		input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/*将文件中最后一天进行改变，全体向前平移 */
	public static void ChangeMonth(int daynumber){
		String[] CodeNumber =ReadMonth();
		String [] NewCodeNumber=new String[30];
		try{
		FileWriter input = new FileWriter("month.txt");
		for(int i=0;i<29;i++){
			NewCodeNumber[i]=CodeNumber[i+1];
		}
		NewCodeNumber[30]=String.valueOf(daynumber);
			for(int j=0;j<30;j++){
				if(NewCodeNumber[j]!=null){
					input.write(NewCodeNumber[j]);
					input.write("\r\n");
				}
			}
		input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
/* 文件中记录的今天的日期 */
	public static String ReadTodayDate(){
		String TodayDate="";
		try {
			BufferedReader buf=new BufferedReader(new FileReader("today.txt"));
			String a=buf.readLine();
			TodayDate=buf.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TodayDate;
	}
/*返回本周七天每天的编码 */
	public static String[]  ReadWeek(){
		String [] CodeNumber=new String[7];
		String line;
		int i=0;
		try {
			BufferedReader buf=new BufferedReader(new FileReader("week.txt"));
			while((line=buf.readLine())!=null){
				CodeNumber[i]=line;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CodeNumber;
	}

/* 返回当月每天的编码*/
	public static  String[]  ReadMonth(){
		String [] CodeNumber=new String[30];
		String line;
		int i=0;
		try {
			BufferedReader buf=new BufferedReader(new FileReader("month.txt"));
			while((line=buf.readLine())!=null){
				CodeNumber[i]=line;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CodeNumber;
	}
}
