package TCP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Common.MessageType;
import Common.User;
import Util.ReadWirteFile;

public class ClientConServerThread extends Thread {
	public static Socket s;
	public static User user=null;
	public static User newuser = new User();

	public ClientConServerThread() {}

	public ClientConServerThread(Socket s) {
		this.s = s;
	}
	// 发送消息模块封装
	public static void Send_Message(Object o) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 一系列定义发送消息包**/
	/* 请求添加好友 */
	public static void Send_Message_addFriend(String FriendName) {
		System.out.println(newuser);
		newuser.setMessage_type(MessageType.message_addFriend);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setFriendname(FriendName);
		Send_Message(newuser);
	}
	/* 请求删除好友*/
	public static void Send_Message_deleteFriend(String FriendName) {
		User newuser = new User();
		newuser.setMessage_type(MessageType.message_deleteFriend);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setFriendname(FriendName);
		Send_Message(newuser);
	}
	/* 要求服务器返回我今天的代码量 之后通过IO改变*/
	public static  void Send_Message_fortoday() {
		newuser.setMessage_type(MessageType.message_fortoday);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* 写本地今天数据写到服务器去 */
	public static  void Send_Message_writetoday() {
		newuser.setMessage_type(MessageType.message_writetoday);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setTodayNumber(ReadWirteFile.ReadToday());
		Send_Message(newuser);
	}
	/* 要求服务器返回本周代码 */
	public static  void Send_Message_forthisweek() {
		newuser.setMessage_type(MessageType.message_forthisweek);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* 写服务器去改变本周代码量*/
	public static  void Send_Message_writethisweek() {
		newuser.setMessage_type(MessageType.message_writethisweek);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setWeekNumber(ReadWirteFile.ReadAllWeek());
		Send_Message(newuser);
	}
	/* 请求返回本月数据*/
	public  static void Send_Message_forthisMonth() {
		newuser.setMessage_type(MessageType.message_forthisMonth);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* 写这个月，服务器端记得改变数据*/
	public static  void Send_Message_writethisMonth() {
		newuser.setMessage_type(MessageType.message_writethisMonth);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setMonthNumber(ReadWirteFile.ReadAllMonth());
		Send_Message(newuser);
	}
	/* 查总代码量 */
	public static  void Send_Message_fortotalNumber() {
		newuser.setMessage_type(MessageType.message_forAll);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* 写总代码量*/
	public static  void Send_Message_writetotalNumber() {
		newuser.setMessage_type(MessageType.message_writeAll);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setTotalNumber(ReadWirteFile.ReadAll());
		Send_Message(newuser);
	}
	/* 请求返回总排名*/
	public  static void Send_Message_forAllsort() {
		newuser.setMessage_type(MessageType.message_forAllsort);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* 请求返回今天排名*/
	public  static void Send_message_forDaysort() {
		newuser.setMessage_type(MessageType.message_forDaysort);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	public void run() {
		// 一堆判断
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object o = ois.readObject();
				User user = (User) o;
				/**
				 *添加删除好友方面的操作由本地直接完成，服务器端直接改数据，只有请求排名的时候才返回一个服务器端的好友列表数据
				 */
				//只有本周以及当日的代码量允许改变
				if (user.getMessage_type().equals(MessageType.message_fortoday)) {
					this.user.setTodayNumber(user.getTodayNumber());
					ReadWirteFile.WriteToday(user.getTodayNumber());
				}
				if (user.getMessage_type().equals(MessageType.message_forthisweek)) {
					this.user.setWeekNumber(user.getWeekNumber());
				}
				if (user.getMessage_type().equals(MessageType.message_forthisMonth)) {
					this.user.setMonthNumber(user.getMonthNumber());
				}
				if (user.getMessage_type().equals(MessageType.message_forAll)) {
					this.user.setTotalNumber(user.getTotalNumber());
					if(ReadWirteFile.ReadAll()<user.getTotalNumber()){//除非你比我大我再重新写，或者我把自己的代码量先备份了
					ReadWirteFile.WriteAll(user.getTotalNumber());
					}
				}
				if (user.getMessage_type().equals(MessageType.message_forAllsort)) {
					this.user.setFriends_Code_list(user.getFriends_Code_list());
				}
				if (user.getMessage_type().equals(MessageType.message_forDaysort)) {
					this.user.setFriends_Code_list(user.getFriends_Code_list());
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
}
