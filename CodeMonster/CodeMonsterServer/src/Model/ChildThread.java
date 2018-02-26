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
						//��Ӻ��ѳɹ�����ʧ�ܲ����ٷ����ˣ��ͻ��˱������Ѿ����д�����
						if (DBUtil.AddFriend(user.getUserID(),FriendID)){
							//������ӳɹ�
						}else{
							//�����Ѿ����ڣ����ʧ��
						}
					}
					if (user.getMessage_type().equals(MessageType.message_deleteFriend)) {
						if (DBUtil.deleteFriend(user.getUserID(), FriendID)){//ɾ���ɹ�
						}
						else{
							//ɾ��ʧ��
						}
					}
					if(user.getMessage_type().equals(MessageType.message_fortoday)){
						//���ã��ͻ��˽������浽��Ӧ���ݽṹ��
						user.setTodayNumber(DBUtil.QueryUserTodayCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forthisweek)){
						//���ã��ͻ��˽������浽��Ӧ���ݽṹ��
						user.setWeekNumber(DBUtil.QueryUserWeekCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forthisMonth)){
						//���ã��ͻ��˽������浽��Ӧ���ݽṹ��
						user.setMonthNumber(DBUtil.QueryUserMonthCode(user.getUserID()));
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_writetoday)){
						//���ã��ı����ݿ�������
						System.out.println("�û�ID"+user.getUserID()+"���մ�����Ŀ"+user.getTodayNumber());
						DBUtil.UpdateUserTodayCode(user.getUserID(), user.getTodayNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writethisweek)){
						//���ã��ı����ݿ�������
						System.out.println("�û�ID"+user.getUserID()+"�ܴ�����Ŀ"+user.getWeekNumber());
						DBUtil.UpdateUserWeekCode(user.getUserID(), user.getWeekNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writethisMonth)){
						//���ã��ı����ݿ�������
						System.out.println("�û�ID"+user.getUserID()+"�´�����Ŀ"+user.getMonthNumber());
						DBUtil.UpdateUserMonthCode(user.getUserID(), user.getMonthNumber());
					}
					if(user.getMessage_type().equals(MessageType.message_writeAll)){
						//���ã��ı����ݿ�������
						System.out.println("�û�ID"+user.getUserID()+"�ܴ�����Ŀ"+user.getTodayNumber());
						DBUtil.UpdateUserAllCode(user.getUserID(), user.getTotalNumber());
					}
					//��������ֱ�ӽ������б���з��أ��ڿͻ��˽��д���
					if(user.getMessage_type().equals(MessageType.message_forAllsort)){
						//��������
						ArrayList friends=DBUtil.QueryRalation(user.getUserID());
						user.setFriends_Code_list(friends);
						ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
				    	oos.writeObject(message);
					}
					if(user.getMessage_type().equals(MessageType.message_forDaysort)){
						//��������
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
