package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.robot.impl.FXRobotHelper;

import TCP.ClientConServerThread;
import TCP.ClientOnServer;
import Util.ReadWirteFile;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller1_Change  implements Initializable{
	@FXML Button UserImage;
	@FXML Button CodeInfo;
	@FXML Button Friend;
	@FXML Button AboutUs;
	@FXML Button Nothing;
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
	public void CodeInfo() {//�û����ܴ��뻹��Ҫ����һ�£����ڴ���
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
	public void AboutUs(){
		try{
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			 Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main4.fxml")));
			 scene.getStylesheets().add("/UI/main2.css");
			 stage.get(1).setScene(scene);
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		1������ͼƬ��ID
		this.Username.setText(ClientConServerThread.user.getUserName());
		long Rtime=ClientConServerThread.user.getRegisterTime();
		long Ntime=System.currentTimeMillis();
		int NumberOfDay=(int)(Ntime-Rtime)/1000/60/60/24;
		this.DayNumber.setText(String.valueOf(NumberOfDay)+"��");
		this.LineNumber.setText("�ۼ�" + ReadWirteFile.ReadAll()+"�д���");

//		2������ѡ���
//		this.logOutBox.getItems().add("ע��");
//		this.logOutBox.getItems().add("�л��˺�");
//		logOutBox.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
//			logOffBtnAction();
//		});


		MenuItem item1 = new MenuItem("ע��");
		item1.setText("ע��");
		item1.setOnAction((ActionEvent t) -> {
			logOffBtnAction();
		});
		MenuItem item2 = new MenuItem("�л��˺�");
		item2.setText("�л��˺�");
		item2.setOnAction((ActionEvent t) -> {
			logOffBtnAction();
		});
		menu.getItems().removeAll(menu.getItems());
		menu.getItems().add(item1);
		menu.getItems().add(item2);
	}

	@FXML Label Username;
	@FXML Label DayNumber;
	@FXML Label LineNumber;
	@FXML Button Reflash;//ͬ����ť
//	@FXML Button logOffBtn;//ע��
//	@FXML Button switchAccountBtn;//�л��˺�
	//@FXML ChoiceBox<String> logOutBox;
	@FXML MenuButton menu;
	/**
	 * ͬ��Ӧ����û�����Ժ���е�
	 */
	public void Reflash(){
		ClientConServerThread.Send_Message_writetoday();
		System.out.println("ִ��1");
		ClientConServerThread.Send_Message_writethisweek();
		System.out.println("ִ��2");
		ClientConServerThread.Send_Message_writethisMonth();
		System.out.println("ִ��3");
		ClientConServerThread.Send_Message_writetotalNumber();
		System.out.println("ִ��4");

	}
	public void logOffBtnAction(){
		try{
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			 Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main1_Login.fxml")));
			 scene.getStylesheets().add("/UI/main2.css");
			 stage.get(1).setScene(scene);
			 ClientConServerThread.user=null;
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	public void switchAccountBtnAction(){
		try{
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			 Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main1_Login.fxml")));
			 scene.getStylesheets().add("/UI/main2.css");
			 stage.get(1).setScene(scene);
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
}