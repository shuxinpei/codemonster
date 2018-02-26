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
 * ���������б�
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

	public void CodeInfo() {// �û����ܴ��뻹��Ҫ����һ�£����ڴ���
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
			new AlertBox().display("����","�������ֲ���Ϊ��");
		}
	}

	public void deleteFriendBtnAction() {
		if(friend_delete.getText()!=""){
		ClientConServerThread.Send_Message_deleteFriend(friend_delete.getText());
		refreshList();
		}else{
			new AlertBox().display("����","�������ֲ���Ϊ��");
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

		/* �����Ҫ���з������� */
		ClientConServerThread.Send_Message_forAllsort();
		/* ���󷵻��µĺ����б������ */

		ArrayList<User> friendsAndSelf = ClientConServerThread.user.getFriends_Code_list();
		removeDuplicateWithOrder(friendsAndSelf);
		removeDuplicate(friendsAndSelf );
		friendsAndSelf.add(ClientConServerThread.user);
		friendsAndSelf.sort(new CodeSortComparator());// ʹ���ܴ���������������
		for (int i = 0; i < ClientConServerThread.user.getFriends_Code_list().size() && i < 8; i++) {
			User u = friendsAndSelf.get(i);
			table1_data.add(new TableData1(i + 1, u.getUserName(), u.getTotalNumber()));
		} // �ϱ��Ǻ��ѵ�����
		int b = 0;
		for (int j = 0; j < ClientConServerThread.user.getFriends_Code_list().size(); j++) {
			if (friendsAndSelf.get(j).equals(ClientConServerThread.user)) {
				b = j + 1;
			}
		}
		table1_data.add(new TableData1(b, "�������", ClientConServerThread.user.getTotalNumber()));

		ArrayList<User> friendsAndSelf2 = ClientConServerThread.user.getFriends_Code_list();
		friendsAndSelf2.add(ClientConServerThread.user);
		//�Ƴ��ظ�Ԫ��
		removeDuplicateWithOrder(friendsAndSelf2);
		removeDuplicate(friendsAndSelf2);

		friendsAndSelf2.sort(new CodeSortComparator2());// ʹ�ý��մ���������������
		for (int i = 0; i < ClientConServerThread.user.getFriends_Code_list().size() && i < 8; i++) {
			User u = friendsAndSelf2.get(i);
			table2_data.add(new TableData1(i + 1, u.getUserName(), u.getTodayNumber()));
		} // �ϱ��Ǻ��ѵ�����
		int a = 0;
		for (int j = 0; j < ClientConServerThread.user.getFriends_Code_list().size(); j++) {
			if (friendsAndSelf2.get(j).equals(ClientConServerThread.user)) {
				a = j + 1;
			}
		}
		table2_data.add(new TableData1(a, "�������", ClientConServerThread.user.getTodayNumber()));
	}

	/**
	 * ɾ�����ظ���Ԫ��
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