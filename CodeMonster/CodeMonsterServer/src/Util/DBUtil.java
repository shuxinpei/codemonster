package Util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Common.User;

import java.sql.PreparedStatement;

public class DBUtil {
	// �������ݿ� Connection--��ʾ���ݿ�����
	private final static String url = "jdbc:mysql://localhost:3300/codemonster";
	private final static String username = "root";
	private final static String password = "root";
	private static java.sql.Connection con;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password); // ����ֵ��Connection�ӿڵ�ʵ����,��mysql��������
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/***************** �û���Ϣ����ģ�� *****************/

	/**
	 * ����ע����Ϣ���û�������������û�id �Ӳ������û������أ� ע��ɹ������򷵻�true
	 *
	 * @param useName
	 * @param psw
	 * @throws SQLException
	 */
	public static boolean AddUser(String useName, String psw) throws SQLException {
		if (hadName(useName))
			return false;
		else {
			String sql = "INSERT INTO userdata (UserName, UserPsw,RegisteData) VALUES(?,?,?)";
			java.sql.PreparedStatement pst = con.prepareStatement(sql);
			pst.setObject(1, useName);
			pst.setObject(2, psw);
			pst.setObject(3, System.currentTimeMillis());

			pst.executeUpdate();
			pst.close();
			return true;
		}
	}

	/**
	 * ͨ���û�����ѯ�û���Ӧ��ID
	 */
	public static int GetUserID(String UserName) throws SQLException {
		int UserID = -1;
		String sql = "SELECT * FROM userdata WHERE UserName=?";
		java.sql.PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, UserName);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			if (rs.getString("UserName").equals(UserName)) {
				return rs.getInt("UserID");
			}
		}
		pst.close();
		return UserID;
	}

	/**
	 * ������������������ǵ��û����Ƿ��Ѿ���ע�ᣬ����true���Ѿ���ע����
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static boolean hadName(String name) throws SQLException {
		String sql = "SELECT * FROM userdata WHERE UserName=? "; // �����в���,SQL����еĲ���ȫ�������ʺ�ռλ��
		PreparedStatement pst = con.prepareStatement(sql); // ����Connection�ӿڵķ���prepareStatement,��ȡPrepareStatement�ӿڵ�ʵ����
		pst.setObject(1, name); // ����pst����set����,�����ʺ�ռλ���ϵĲ���
		ResultSet rs = pst.executeQuery();// ���÷���,ִ��SQL,��ȡ�����
		while (rs.next()) {
			// rs.getString("UserName")//ͨ����ǰ����ָ���е�ֵ
			// rs.getString (2)//ͨ���鵽��ǰ����ָ�����ֵ���
			if (rs.getString("UserName").equals(name)) {
				return true;
			}
		}
		rs.close();
		pst.close();
		return false;
	}

	/**
	 * ���������������������֤�Ƿ�ɹ���½,��֤�û���
	 *
	 * @param name
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static boolean canOrNotLogin(String name, String password) throws SQLException {
		String sql = "SELECT * FROM userdata WHERE UserName=? "; // �����в���,SQL����еĲ���ȫ�������ʺ�ռλ��
		PreparedStatement pst = con.prepareStatement(sql); // ����Connection�ӿڵķ���prepareStatement,��ȡPrepareStatement�ӿڵ�ʵ����
		pst.setObject(1, name); // ����pst����set����,�����ʺ�ռλ���ϵĲ���
		ResultSet rs = pst.executeQuery();// ���÷���,ִ��SQL,��ȡ�����
		while (rs.next()) {
			// rs.getString("UserName")//ͨ����ǰ����ָ���е�ֵ
			// rs.getString (2)//ͨ���鵽��ǰ����ָ�����ֵ���
			if (rs.getString("UserPsw").equals(password)) {
				return true;
			} else {
				return false;
			}
		}
		rs.close();
		pst.close();
		return false;
	}

	/**
	 * ע�����رտͻ��˺������´򿪲����Զ���¼
	 *
	 * @throws SQLException
	 */
	public static void logOff() throws SQLException {

	}

	/**
	 * ͨ�������û���id�õ������û���
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static String QueryUserName(int id) throws SQLException {// ----------------------------------------------------
		String name = null;
		String sql = " SELECT * FROM userdata WHERE UserId=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			name = rs.getString(2);
		}
		rs.close();
		pst.close();
		return name;
	}

	/**
	 * ͨ�����ǵ��û��������õ�����ID
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static int QueryGetUserID(String name) throws SQLException {
		int ID = 0;
		String sql = "SELECT * FROM userdata WHERE UserName=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, name);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			ID = rs.getInt(1);
		}
		rs.close();
		pst.close();
		return ID;
	}

	/***************** ������Ϣģ�� *****************/
	// ͨ��id�ҵ����֣�ͼƬ

	/**
	 * ͨ���û���id������Ӻ��ѣ��󶨹�ϵ��һ����������ϵ�����ں��洦��
	 *
	 * @param User1
	 * @param User2
	 * @throws SQLException
	 */
	public static boolean AddFriend(int User1ID, int User2ID) throws SQLException {
		// �໥���Ϊ����
		String sql1 = "INSERT INTO friends (UserIDone,UserIDtwo )VALUES(?,?) ";
		PreparedStatement pst = con.prepareStatement(sql1);
		pst.setObject(1, User1ID);
		pst.setObject(2, User2ID);
		pst.executeUpdate();
		pst.close();
		return false;
	}



	/**
	 * ͨ���û�id�ҵ��û�����friend��friendInfo�����ں�������
	 *
	 * @param useid
	 * @return
	 * @throws SQLException
	 */
/**
	public static ArrayList<User> getFriendsList(int useid) throws SQLException {
		String sql = "SELECT * FROM friends WHERE UseIDone=? "; // �����в���,SQL����еĲ���ȫ�������ʺ�ռλ��
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, useid);
		ResultSet rs = pst.executeQuery();
		ArrayList<friendInfo> friendsList = new ArrayList<>();

		while (rs.next()) {
			int friendID = rs.getInt(2);
			String name = QueryUserName(friendID);
			int friendUserAllCode = QueryUserAllCode(friendID);
			int friendUserWeekCode = QueryUserWeekCode(name);
			int friendUserMonthCode = QueryUserMonthCode(name);
			friendsList.add(new friendInfo(friendID, name, friendUserAllCode, friendUserWeekCode, friendUserMonthCode));
		}
		rs.close();
		pst.close();
		return friendsList;
	}
*/
	/**
	 * ɾ������
	 *
	 * @param friendID
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteFriend(int userID, int friendID) throws SQLException {
		String sql = "delete from friends where UseIDone=? and UseIDtwo=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, userID);
		pst.setObject(2, friendID);
		ResultSet rs = pst.executeQuery();
		rs.close();
		pst.close();
		if (!rs.next())
			return false;
		else
			return true;
	}

	/***************** �û�������Ϣģ�� *****************/
	/**
	 * ͨ���û����õ����Ľ��մ�����
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static int QueryUserTodayCode(int UserID) throws SQLException {
		int todayCode = 0;
		String sql = " SELECT * FROM userday WHERE UserID=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, UserID);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			todayCode = rs.getInt(2);
		}
		rs.close();
		pst.close();
		return todayCode;
	}

	/**
	 * ͨ�������û���id�õ������ܴ�����
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static int QueryUserAllCode(int id) throws SQLException {
		int allCode = 0;
		String sql = " SELECT * FROM userallcode WHERE UserId=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			allCode = rs.getInt(2);
		}
		rs.close();
		pst.close();
		return allCode;
	}

	/**
	 * ͨ���û����õ������´�����
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static int QueryUserMonthCode(int UserID) throws SQLException {
		int monthCode = 0;
		String sql = " SELECT * FROM usermonthcode WHERE MonthNumber=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, UserID);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			monthCode = rs.getInt(2);
		}
		rs.close();
		pst.close();
		return monthCode;
	}

	/**
	 * ͨ���û����õ������ܴ�����
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static int QueryUserWeekCode(int UserID) throws SQLException {
		int weekCode = 0;
		String sql = " SELECT * FROM userweekcode WHERE UserID=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, UserID);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			weekCode = rs.getInt(2);
		}
		rs.close();
		pst.close();
		return weekCode;
	}
	/*д�����Ĵ���*/
	public static void UpdateUserTodayCode(int UserID, int code) throws SQLException {
		String sql = " UPDATE userday set daynumber=? WHERE UserID=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, code);
		pst.setObject(2, UserID);
		int rs = pst.executeUpdate();
		pst.close();
	}
	/*д�뱾�ܵĴ���*/
	public static void UpdateUserWeekCode(int UserID, int code) throws SQLException {
		String sql = " UPDATE userweekcode set WeekNumber=? WHERE UserID=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, code);
		pst.setObject(2, UserID);
		int rs = pst.executeUpdate();
		pst.close();
	}
	/*д�뱾�µĴ���*/
	public static void UpdateUserMonthCode(int UserID, int code) throws SQLException {
		String sql = " UPDATE usermonthcode set MonthNumber=? WHERE UserID=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, code);
		pst.setObject(2, UserID);
		int rs = pst.executeUpdate();
		pst.close();
	}
	public static void UpdateUserAllCode(int UserID, int code) throws SQLException {
		String sql = " UPDATE userallcode set userallcode=? WHERE userID=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, code);
		pst.setObject(2, UserID);
		int rs = pst.executeUpdate();
		pst.close();
	}
	/*��ʼ���û�����*/
	public static boolean setToday(int  userid,int number) throws SQLException {
			String sql = "INSERT INTO userday (UserID,daynumber) VALUES(?,?)";
			java.sql.PreparedStatement pst = con.prepareStatement(sql);
			pst.setObject(1, userid);
			pst.setObject(2, number);

			pst.executeUpdate();
			pst.close();
			return true;
	}
	public static boolean setWeek(int  userid,int number) throws SQLException {
		String sql = "INSERT INTO userweekcode (UserID,WeekNumber) VALUES(?,?)";
		java.sql.PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, userid);
		pst.setObject(2, number);

		pst.executeUpdate();
		pst.close();
		return true;
}
	public static boolean setMonth(int  userid,int number) throws SQLException {
		String sql = "INSERT INTO usermonthcode (UserID,MonthNumber) VALUES(?,?)";
		java.sql.PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, userid);
		pst.setObject(2, number);
		pst.executeUpdate();
		pst.close();
		return true;
}
	public static boolean setAll(int  userid,int number) throws SQLException {
		String sql = "INSERT INTO userallcode (userID,userallcode) VALUES(?,?)";
		java.sql.PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, userid);
		pst.setObject(2, number);
		pst.executeUpdate();
		pst.close();
		return true;
}
	// --------------------------------���D���
	/**
	 * ����һ��û���ظ�Ԫ�صĶ���
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<User> QueryRalation(int id) throws SQLException {
		ArrayList<User> list = new ArrayList();
		String sql = "SELECT * FROM friends  WHERE UserIDone=? OR UserIDtwo=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setObject(1, id);
		pst.setObject(2, id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			// rs.getString("UserName")//ͨ����ǰ����ָ���е�ֵ
			// rs.getString (2)//ͨ���鵽��ǰ����ָ�����ֵ���
			if (rs.getInt(1) != id) {
				User user=new User();
				user.setUserID(rs.getInt(1));
				user.setTotalNumber(QueryUserAllCode(user.getUserID()));
				user.setTodayNumber(QueryUserTodayCode(user.getUserID()));
				user.setUserName(QueryUserName(user.getUserID()));
				list.add(user);
			}
			if (rs.getInt(2) != id) {
				User user=new User();
				user.setUserID(rs.getInt(2));
				user.setTotalNumber(QueryUserAllCode(user.getUserID()));
				user.setTodayNumber(QueryUserTodayCode(user.getUserID()));
				user.setUserName(QueryUserName(user.getUserID()));
				list.add(user);
			}
		}
		rs.close();
		pst.close();
		removeDuplicateWithOrder(list);
		return list;
	}

	/**
	 * �h��list�е����}Ԫ��
	 *
	 * @param list
	 */
	public static void removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
	}

	public static long getUserRegisteTime(int UserID) {
		long RegisteTime = 0;
		try {
			String sql = " SELECT * FROM userData WHERE UserID=? ";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setObject(1, UserID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RegisteTime = rs.getLong("RegisteData");
			}
			rs.close();
			pst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return RegisteTime;
	}
}
