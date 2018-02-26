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
				// 接收客户端发来的信息.
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object message = ois.readObject();
				User user = (User) message;
				if (user.getMessage_type().equals(MessageType.message_Login)) {
					System.out.println("接受到登录消息");
					String UserName = user.getUserName();
					String Psw = user.getUserPsw();
					/**
					 * 调用DBUtil中的 if（匹配） 返回message message设置成功登陆 同时将该客户端给子线程处理
					 * else 返回失败的包 ObjectOutputStream oos = new
					 * ObjectOutputStream(s.getOutputStream());
					 * oos.writeObject(m);
					 */
					if (DBUtil.canOrNotLogin(UserName, Psw)) {
						user.setMessage_type(MessageType.message_login_success);
						user.setUserID(DBUtil.GetUserID(user.getUserName()));
						user.setFriends_Code_list(DBUtil.QueryRalation(user.getUserID()));
						user.setRegisterTime(DBUtil.getUserRegisteTime(user.getUserID()));
						//将消息包进行回传
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						// 交给子线程处理
						ChildThread child1 = new ChildThread(s, user);
						child1.start();
						System.out.println("成功登录");
					} else {
						((User) message).setMessage_type(MessageType.message_login_fail);
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						System.out.println("登录失败");
					}
				}
				if (user.getMessage_type().equals(MessageType.message_Registe)) {
					/**
					 * if(数据库中已经有该用户名的) 设置Message 注册失败 else{ 将新用户数据写入数据库 返回成功注册
					 * 同时将该客户端给子线程处理 }
					 */
					if (DBUtil.AddUser(user.getUserName(), user.getUserPsw())) {
						((User) message).setMessage_type(MessageType.message_Registesucceed);
						user.setUserID(DBUtil.GetUserID(user.getUserName()));
						user.setFriends_Code_list(DBUtil.QueryRalation(user.getUserID()));
						user.setRegisterTime(DBUtil.getUserRegisteTime(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						//数据库进行初始化
						DBUtil.setMonth(user.getUserID(),0);
						DBUtil.setWeek(user.getUserID(),0);
						DBUtil.setToday(user.getUserID(),0);
						DBUtil.setAll(user.getUserID(),0);
						// 交给子线程处理
						ChildThread child1 = new ChildThread(s, user);
						child1.start();
						System.out.println("成功注册");
					} else {
						((User) message).setMessage_type(MessageType.message_Registe_Fail);
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						oos.writeObject(message);
						System.out.println("注册失败");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
