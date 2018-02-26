package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Common.MessageType;
import Common.User;
import Util.ReadWirteFile;
public class ClientOnServer {
	public  Socket s;
	public void ClientOnServer(){}
	/**
	 * 传入一个User对象就会将它发送给服务器
	 */
	public  boolean  sendLoginInfoToServer(Object o){
		boolean b=false;
		try {
			s=new Socket("localhost",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			System.out.println("发送成功");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			User ms=(User)ois.readObject();
			System.out.println("接收到消息包");
			//这里就是验证用户登录的地方
			if(ms.getMessage_type().equals(MessageType.message_login_success))//表示登录成功,将用户的数据保存到子线程中的数据域中，同时将代码相关信息也进行保存
			{
				System.out.print("接收到成功登陆的消息");
				b=true;
				ClientConServerThread ccst=new ClientConServerThread(s);
				ccst.user=ms;
				ccst.user.setTodayNumber(ReadWirteFile.ReadToday());
				ccst.user.setWeekNumber(ReadWirteFile.ReadAllWeek());
				ccst.user.setMonthNumber(ReadWirteFile.ReadAllMonth());
				ccst.user.setTotalNumber(ReadWirteFile.ReadAll());
				System.out.println("服务器端返回的数据包"+"姓名"+ccst.getName()+"注册时间"+ccst.user.getRegisterTime()+
						"用户ID"+ccst.user.getUserID()+
						"本月行数"+ccst.user.getMonthNumber()+
						"今天行数"+ccst.user.getTodayNumber()+
						"本周行数"+ccst.user.getWeekNumber()+
						"总行数"+ccst.user.getTotalNumber());
				ccst.start();
			}else{
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return b;
	}

	public  boolean sendRegisteInfoToServer(Object o){
		boolean b=false;
		try {
			s=new Socket("localhost",9999);
			//将注册信息进行发送
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			System.out.println("执行1");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			System.out.println("执行2");
			User ms=(User)ois.readObject();
			if(ms.getMessage_type().equals("2"))//表示注册成功
			{
				b=true;
				ClientConServerThread ccst=new ClientConServerThread(s);
				ccst.user=ms;
				ccst.user.setTodayNumber(ReadWirteFile.ReadToday());
				ccst.user.setWeekNumber(ReadWirteFile.ReadAllWeek());
				ccst.user.setMonthNumber(ReadWirteFile.ReadAllMonth());
				ccst.user.setTotalNumber(ReadWirteFile.ReadAll());
				System.out.println("服务器端返回的数据包"+"姓名"+ccst.getName()+"注册时间"+ccst.user.getRegisterTime()+
						"用户ID"+ccst.user.getUserID()+
						"本月行数"+ccst.user.getMonthNumber()+
						"今天行数"+ccst.user.getTodayNumber()+
						"本周行数"+ccst.user.getWeekNumber()+
						"总行数"+ccst.user.getTotalNumber());
				//启动该通讯线程
				ccst.start();
			}else{
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
