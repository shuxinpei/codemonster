package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import Common.MessageType;
import Common.User;
import Util.DBUtil;

public class Server {
	ServerSocket ss;
	public static void main(String[] args) {
		new Server();
	}
	public Server() {
		try {
		 ss= new ServerSocket(9999);
			while (true) {
				Socket s = ss.accept();
				// ���տͻ��˷�������Ϣ.
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object message = ois.readObject();
				User user = (User) message;
				if (user.getMessage_type().equals(MessageType.message_Login)) {
					System.out.println("���ܵ���¼��Ϣ");
					String UserName = user.getUserName();
					String Psw = user.getUserPsw();
					/**
					 * ����DBUtil�е� if��ƥ�䣩 ����message message���óɹ���½ ͬʱ���ÿͻ��˸����̴߳���
					 * else ����ʧ�ܵİ� ObjectOutputStream oos = new
					 * ObjectOutputStream(s.getOutputStream());
					 * oos.writeObject(m);
					 */
					if (DBUtil.canOrNotLogin(UserName, Psw)) {
						user.setMessage_type(MessageType.message_login_success);
						user.setUserID(DBUtil.GetUserID(user.getUserName()));
						user.setFriends_Code_list(DBUtil.QueryRalation(user.getUserID()));
						user.setRegisterTime(DBUtil.getUserRegisteTime(user.getUserID()));
						//����Ϣ�����лش�
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						// �������̴߳���
						ChildThread child1 = new ChildThread(s, user);
						child1.start();
						System.out.println("�ɹ���¼");
					} else {
						((User) message).setMessage_type(MessageType.message_login_fail);
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						System.out.println("��¼ʧ��");
					}
				}
				if (user.getMessage_type().equals(MessageType.message_Registe)) {
					/**
					 * if(���ݿ����Ѿ��и��û�����) ����Message ע��ʧ�� else{ �����û�����д�����ݿ� ���سɹ�ע��
					 * ͬʱ���ÿͻ��˸����̴߳��� }
					 */
					if (DBUtil.AddUser(user.getUserName(), user.getUserPsw())) {
						((User) message).setMessage_type(MessageType.message_Registesucceed);
						user.setUserID(DBUtil.GetUserID(user.getUserName()));
						user.setFriends_Code_list(DBUtil.QueryRalation(user.getUserID()));
						user.setRegisterTime(DBUtil.getUserRegisteTime(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						//���ݿ���г�ʼ��
						DBUtil.setMonth(user.getUserID(),0);
						DBUtil.setWeek(user.getUserID(),0);
						DBUtil.setToday(user.getUserID(),0);
						DBUtil.setAll(user.getUserID(),0);
						// �������̴߳���
						ChildThread child1 = new ChildThread(s, user);
						child1.start();
						System.out.println("�ɹ�ע��");
					} else {
						((User) message).setMessage_type(MessageType.message_Registe_Fail);
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						System.out.println("ע��ʧ��");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
