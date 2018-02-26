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
	 * ����һ��User����ͻὫ�����͸�������
	 */
	public  boolean  sendLoginInfoToServer(Object o){
		boolean b=false;
		try {
			s=new Socket("localhost",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			System.out.println("���ͳɹ�");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			User ms=(User)ois.readObject();
			System.out.println("���յ���Ϣ��");
			//���������֤�û���¼�ĵط�
			if(ms.getMessage_type().equals(MessageType.message_login_success))//��ʾ��¼�ɹ�,���û������ݱ��浽���߳��е��������У�ͬʱ�����������ϢҲ���б���
			{
				System.out.print("���յ��ɹ���½����Ϣ");
				b=true;
				ClientConServerThread ccst=new ClientConServerThread(s);
				ccst.user=ms;
				ccst.user.setTodayNumber(ReadWirteFile.ReadToday());
				ccst.user.setWeekNumber(ReadWirteFile.ReadAllWeek());
				ccst.user.setMonthNumber(ReadWirteFile.ReadAllMonth());
				ccst.user.setTotalNumber(ReadWirteFile.ReadAll());
				System.out.println("�������˷��ص����ݰ�"+"����"+ccst.getName()+"ע��ʱ��"+ccst.user.getRegisterTime()+
						"�û�ID"+ccst.user.getUserID()+
						"��������"+ccst.user.getMonthNumber()+
						"��������"+ccst.user.getTodayNumber()+
						"��������"+ccst.user.getWeekNumber()+
						"������"+ccst.user.getTotalNumber());
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
			//��ע����Ϣ���з���
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			System.out.println("ִ��1");
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			System.out.println("ִ��2");
			User ms=(User)ois.readObject();
			if(ms.getMessage_type().equals("2"))//��ʾע��ɹ�
			{
				b=true;
				ClientConServerThread ccst=new ClientConServerThread(s);
				ccst.user=ms;
				ccst.user.setTodayNumber(ReadWirteFile.ReadToday());
				ccst.user.setWeekNumber(ReadWirteFile.ReadAllWeek());
				ccst.user.setMonthNumber(ReadWirteFile.ReadAllMonth());
				ccst.user.setTotalNumber(ReadWirteFile.ReadAll());
				System.out.println("�������˷��ص����ݰ�"+"����"+ccst.getName()+"ע��ʱ��"+ccst.user.getRegisterTime()+
						"�û�ID"+ccst.user.getUserID()+
						"��������"+ccst.user.getMonthNumber()+
						"��������"+ccst.user.getTodayNumber()+
						"��������"+ccst.user.getWeekNumber()+
						"������"+ccst.user.getTotalNumber());
				//������ͨѶ�߳�
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
