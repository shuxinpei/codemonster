package Common;

public interface MessageType {
	String message_Registe="0";//表明请求进行注册
	String message_Login="1";//表明请求进行登录
	String message_Registesucceed="2";//表明是注册成功
	String message_Registe_Fail="3";//表明是注册失败
	String message_login_success="4";//表明登录成功
	String message_login_fail="5";//表明登录失败

	String message_addFriend="6";//添加好友
	String message_deleteFriend="9";//删除好友

	String message_fortoday="12";//查今天代码
	String message_writetoday="13";//写入今天代码
	String message_forthisweek="14";//查本周代码
	String message_writethisweek="15";//写本周代码
	String message_forthisMonth="16";//查本月代码
	String message_writethisMonth="17";//写本月代码
	String message_writeAll="18";//写总代码量
	String message_forAll="19";//查总代码量

	String message_forAllsort="18";//查总排名
	String message_forDaysort="19";//查日排名
}
