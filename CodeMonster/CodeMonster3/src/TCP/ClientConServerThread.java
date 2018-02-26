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
	// ������Ϣģ���װ
	public static void Send_Message(Object o) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** һϵ�ж��巢����Ϣ��**/
	/* ������Ӻ��� */
	public static void Send_Message_addFriend(String FriendName) {
		System.out.println(newuser);
		newuser.setMessage_type(MessageType.message_addFriend);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setFriendname(FriendName);
		Send_Message(newuser);
	}
	/* ����ɾ������*/
	public static void Send_Message_deleteFriend(String FriendName) {
		User newuser = new User();
		newuser.setMessage_type(MessageType.message_deleteFriend);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setFriendname(FriendName);
		Send_Message(newuser);
	}
	/* Ҫ������������ҽ���Ĵ����� ֮��ͨ��IO�ı�*/
	public static  void Send_Message_fortoday() {
		newuser.setMessage_type(MessageType.message_fortoday);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* д���ؽ�������д��������ȥ */
	public static  void Send_Message_writetoday() {
		newuser.setMessage_type(MessageType.message_writetoday);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setTodayNumber(ReadWirteFile.ReadToday());
		Send_Message(newuser);
	}
	/* Ҫ����������ر��ܴ��� */
	public static  void Send_Message_forthisweek() {
		newuser.setMessage_type(MessageType.message_forthisweek);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* д������ȥ�ı䱾�ܴ�����*/
	public static  void Send_Message_writethisweek() {
		newuser.setMessage_type(MessageType.message_writethisweek);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setWeekNumber(ReadWirteFile.ReadAllWeek());
		Send_Message(newuser);
	}
	/* ���󷵻ر�������*/
	public  static void Send_Message_forthisMonth() {
		newuser.setMessage_type(MessageType.message_forthisMonth);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* д����£��������˼ǵøı�����*/
	public static  void Send_Message_writethisMonth() {
		newuser.setMessage_type(MessageType.message_writethisMonth);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setMonthNumber(ReadWirteFile.ReadAllMonth());
		Send_Message(newuser);
	}
	/* ���ܴ����� */
	public static  void Send_Message_fortotalNumber() {
		newuser.setMessage_type(MessageType.message_forAll);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* д�ܴ�����*/
	public static  void Send_Message_writetotalNumber() {
		newuser.setMessage_type(MessageType.message_writeAll);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		newuser.setTotalNumber(ReadWirteFile.ReadAll());
		Send_Message(newuser);
	}
	/* ���󷵻�������*/
	public  static void Send_Message_forAllsort() {
		newuser.setMessage_type(MessageType.message_forAllsort);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	/* ���󷵻ؽ�������*/
	public  static void Send_message_forDaysort() {
		newuser.setMessage_type(MessageType.message_forDaysort);
		newuser.setUserName(user.getUserName());
		newuser.setUserID(user.getUserID());
		Send_Message(newuser);
	}
	public void run() {
		// һ���ж�
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object o = ois.readObject();
				User user = (User) o;
				/**
				 *���ɾ�����ѷ���Ĳ����ɱ���ֱ����ɣ���������ֱ�Ӹ����ݣ�ֻ������������ʱ��ŷ���һ���������˵ĺ����б�����
				 */
				//ֻ�б����Լ����յĴ���������ı�
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
					if(ReadWirteFile.ReadAll()<user.getTotalNumber()){//��������Ҵ���������д�������Ұ��Լ��Ĵ������ȱ�����
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
