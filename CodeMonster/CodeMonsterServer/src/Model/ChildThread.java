package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Common.MessageType;
import Common.User;
import Util.DBUtil;

public class ChildThread extends Thread{
	public Socket so;
	public User user;
	public ChildThread(){}

	public ChildThread(Socket so, User use){
		this.so=so;
		this.user=user;
	}

	public void run(){
		while(true){
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(so.getInputStream());
				Object message = ois.readObject();
				User user = (User) message;
				int FriendID=DBUtil.GetUserID(user.getFriendname());
					if (user.getMessage_type().equals(MessageType.message_addFriend)) {
						//添加好友成功或者失败不用再返回了，客户端本来就已经进行处理了
						if (DBUtil.AddFriend(user.getUserID(),FriendID)){
							//好友添加成功
						}else{
							//好友已经存在，添加失败
						}
					}
					if (user.getMessage_type().equals(MessageType.message_deleteFriend)) {
						if (DBUtil.deleteFriend(user.getUserID(), FriendID)){//删除成功
						}
						else{
							//删除失败
						}
					}
					if(user.getMessage_type().equals(MessageType.message_fortoday)){
						//调用，客户端解析，存到对应数据结构中
						user.setTodayNumber(DBUtil.QueryUserTodayCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forthisweek)){
						//调用，客户端解析，存到对应数据结构中
						user.setWeekNumber(DBUtil.QueryUserWeekCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forthisMonth)){
						//调用，客户端解析，存到对应数据结构中
						user.setMonthNumber(DBUtil.QueryUserMonthCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_writetoday)){
						//调用，改变数据库中数据
						System.out.println("用户ID"+user.getUserID()+"今日代码数目"+user.getTodayNumber());
						DBUtil.UpdateUserTodayCode(user.getUserID(), user.getTodayNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writethisweek)){
						//调用，改变数据库中数据
						System.out.println("用户ID"+user.getUserID()+"周代码数目"+user.getWeekNumber());
						DBUtil.UpdateUserWeekCode(user.getUserID(), user.getWeekNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writethisMonth)){
						//调用，改变数据库中数据
						System.out.println("用户ID"+user.getUserID()+"月代码数目"+user.getMonthNumber());
						DBUtil.UpdateUserMonthCode(user.getUserID(), user.getMonthNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writeAll)){
						//调用，改变数据库中数据
						System.out.println("用户ID"+user.getUserID()+"总代码数目"+user.getTodayNumber());
						DBUtil.UpdateUserAllCode(user.getUserID(), user.getTotalNumber());
					}
					//查排名的直接将好友列表进行返回，在客户端进行处理
					if(user.getMessage_type().equals(MessageType.message_forAllsort)){
						//查总排名
						ArrayList friends=DBUtil.QueryRalation(user.getUserID());
						user.setFriends_Code_list(friends);
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forDaysort)){
						//查日排名
						ArrayList friends=DBUtil.QueryRalation(user.getUserID());
						user.setFriends_Code_list(friends);
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
