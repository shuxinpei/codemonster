package Common;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	String message_type;
	long RegisterTime;
	int UserID;
	String UserName;
	String UserPsw;
	String  friendname;//���жԺ��ѵĲ�����ͨ�������������������
/**---------------���������е��û���Ϣ���Լ����Ѳ���-------------------**/

	int TodayNumber;
	int WeekNumber;
	int MonthNumber;
	int totalNumber;
/**-------------------------�������д���������--------------------------**/

	ArrayList<User> Friends_Code_list=new ArrayList<User>();

/**-------------------------�������к��Ѳ����й�����---------------------**/
	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public long getRegisterTime() {
		return RegisterTime;
	}

	public void setRegisterTime(long registerTime) {
		RegisterTime = registerTime;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPsw() {
		return UserPsw;
	}

	public void setUserPsw(String userPsw) {
		UserPsw = userPsw;
	}

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	public int getTodayNumber() {
		return TodayNumber;
	}

	public void setTodayNumber(int todayNumber) {
		TodayNumber = todayNumber;
	}

	public int getWeekNumber() {
		return WeekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		WeekNumber = weekNumber;
	}

	public int getMonthNumber() {
		return MonthNumber;
	}

	public void setMonthNumber(int monthNumber) {
		MonthNumber = monthNumber;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public ArrayList<User> getFriends_Code_list() {
		return Friends_Code_list;
	}

	public void setFriends_Code_list(ArrayList<User> friends_Code_list) {
		Friends_Code_list = friends_Code_list;
	}

}
