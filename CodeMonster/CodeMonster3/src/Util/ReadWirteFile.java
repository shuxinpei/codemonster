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
/*���ر������д�����*/
	public static  int ReadAllWeek(){
		String[] numbers=ReadWeek();
		int a=0;
		for(int i=0;i<numbers.length;i++){
			a+=Integer.valueOf(numbers[i]);
		}
		return a;
	}
/*���ر������д�����*/
	public static  int ReadAllMonth(){
		String[] numbers=ReadMonth();
		int a=0;
		for(int i=0;i<numbers.length;i++){
			a+=Integer.valueOf(numbers[i]);
		}
		return a;
	}
/*���ؽ��������*/
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
/*�������д�����*/
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
/* ֱ�Ӹı���������*/
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
/* ֱ�Ӹı�������	 */
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
/* �������ڼӼӣ��ж��Ƿ����µ�һ�죬������µ�һ��ͽ��ܣ��¶��ı�*/
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
	/*�������һ�����������1��Ҳ���ǽ���*/
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
/*�������һ�����������1��Ҳ���ǽ��� */
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
/* �ı䵱ǰ�����һ�죬����ƽ��*/
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
	/*���ļ������һ����иı䣬ȫ����ǰƽ�� */
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
/* �ļ��м�¼�Ľ�������� */
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
/*���ر�������ÿ��ı��� */
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

/* ���ص���ÿ��ı���*/
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
