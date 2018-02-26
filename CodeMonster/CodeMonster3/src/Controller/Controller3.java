package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.sun.javafx.robot.impl.FXRobotHelper;

import Common.User;
import Data.TableData1;
import TCP.ClientConServerThread;
import Util.AlertBox;
import Util.CodeSortComparator;
import Util.CodeSortComparator2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * 好友排名列表
 *
 * @author SHUXIN
 */

public class Controller3 implements Initializable {
	@FXML
	Button UserImage;
	@FXML
	Button CodeInfo;
	@FXML
	Button Friend;
	@FXML
	Button AboutUs;
	@FXML Button Nothing;

	@FXML
	TableView AllSort;
	@FXML
	TableView DaySort;

	@FXML
	Button addFriendBtn;
	@FXML
	Button deleteFriendBtn;
	@FXML
	TextField friend_add;
	@FXML
	TextField friend_delete;

	public static ObservableList<TableData1> table1_data;
	public static ObservableList<TableData1> table2_data;

	public void UserImage() {
		try {
			if (ClientConServerThread.user == null) {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_Login.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			} else {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_User.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void CodeInfo() {// 用户的周代码还是要保存一下，后期处理
		try {
			if (ClientConServerThread.user == null) {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_Login.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			} else {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_2.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void Friend() {
		try {
			if (ClientConServerThread.user == null) {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_Login.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			} else {
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main3.fxml")));
				scene.getStylesheets().add("/UI/main2.css");
				stage.get(1).setScene(scene);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void AboutUs() {
		try {
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main4.fxml")));
			scene.getStylesheets().add("/UI/main2.css");
			stage.get(1).setScene(scene);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addFriendBtnAction() {
		if(friend_add.getText()!=""){
		System.out.println(friend_add.getText());
		ClientConServerThread.Send_Message_addFriend(friend_add.getText());
		refreshList();
		}else{
			new AlertBox().display("警告","好友名字不能为空");
		}
	}

	public void deleteFriendBtnAction() {
		if(friend_delete.getText()!=""){
		ClientConServerThread.Send_Message_deleteFriend(friend_delete.getText());
		refreshList();
		}else{
			new AlertBox().display("警告","好友名字不能为空");
		}
	}

	/**
	 * public int number; public String Username; public int LineNumber;
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		refreshList();
	}
	public void refreshList(){
		table1_data = FXCollections.observableArrayList();
		ObservableList<TableColumn> Table1List = AllSort.getColumns();
		Table1List.get(0).setCellValueFactory(new PropertyValueFactory("number"));
		Table1List.get(1).setCellValueFactory(new PropertyValueFactory("Username"));
		Table1List.get(2).setCellValueFactory(new PropertyValueFactory("LineNumber"));
		AllSort.setItems(table1_data);

		table2_data = FXCollections.observableArrayList();
		ObservableList<TableColumn> Table2List = DaySort.getColumns();
		Table2List.get(0).setCellValueFactory(new PropertyValueFactory("number"));
		Table2List.get(1).setCellValueFactory(new PropertyValueFactory("Username"));
		Table2List.get(2).setCellValueFactory(new PropertyValueFactory("LineNumber"));
		DaySort.setItems(table2_data);

		/* 这边需要进行发送请求 */
		ClientConServerThread.Send_Message_forAllsort();
		/* 请求返回新的好有列表就行了 */

		ArrayList<User> friendsAndSelf = ClientConServerThread.user.getFriends_Code_list();
		removeDuplicateWithOrder(friendsAndSelf);
		removeDuplicate(friendsAndSelf );
		friendsAndSelf.add(ClientConServerThread.user);
		friendsAndSelf.sort(new CodeSortComparator());// 使用总代码行数进行排序
		for (int i = 0; i < ClientConServerThread.user.getFriends_Code_list().size() && i < 8; i++) {
			User u = friendsAndSelf.get(i);
			table1_data.add(new TableData1(i + 1, u.getUserName(), u.getTotalNumber()));
		} // 上边是好友的排名
		int b = 0;
		for (int j = 0; j < ClientConServerThread.user.getFriends_Code_list().size(); j++) {
			if (friendsAndSelf.get(j).equals(ClientConServerThread.user)) {
				b = j + 1;
			}
		}
		table1_data.add(new TableData1(b, "你的排名", ClientConServerThread.user.getTotalNumber()));

		ArrayList<User> friendsAndSelf2 = ClientConServerThread.user.getFriends_Code_list();
		friendsAndSelf2.add(ClientConServerThread.user);
		//移除重复元素
		removeDuplicateWithOrder(friendsAndSelf2);
		removeDuplicate(friendsAndSelf2);

		friendsAndSelf2.sort(new CodeSortComparator2());// 使用今日代码行数进行排序
		for (int i = 0; i < ClientConServerThread.user.getFriends_Code_list().size() && i < 8; i++) {
			User u = friendsAndSelf2.get(i);
			table2_data.add(new TableData1(i + 1, u.getUserName(), u.getTodayNumber()));
		} // 上边是好友的排名
		int a = 0;
		for (int j = 0; j < ClientConServerThread.user.getFriends_Code_list().size(); j++) {
			if (friendsAndSelf2.get(j).equals(ClientConServerThread.user)) {
				a = j + 1;
			}
		}
		table2_data.add(new TableData1(a, "你的排名", ClientConServerThread.user.getTodayNumber()));
	}

	/**
	 * 删除重重复的元素
	 *
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

	public static void removeDuplicate(ArrayList<User> list) {
		ArrayList<User> list1 = new <User>ArrayList();
		for(int i=0;i<list.size();i++){
			User user=list.get(i);
			if(CompareByname(list1,user)){
				list1.add(user);
			}
		}
		list=list1;
	}
	public static  boolean CompareByname(ArrayList<User>list,User user){
		boolean flag=true;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getUserName().equals(user.getUserName())){
				flag=false;
			}
		}
		return flag;
	}
}