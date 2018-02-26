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
	public void CodeInfo() {//用户的周代码还是要保存一下，后期处理
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
//		1、设置图片和ID
		this.Username.setText(ClientConServerThread.user.getUserName());
		long Rtime=ClientConServerThread.user.getRegisterTime();
		long Ntime=System.currentTimeMillis();
		int NumberOfDay=(int)(Ntime-Rtime)/1000/60/60/24;
		this.DayNumber.setText(String.valueOf(NumberOfDay)+"天");
		this.LineNumber.setText("累计" + ReadWirteFile.ReadAll()+"行代码");

//		2、设置选择框
//		this.logOutBox.getItems().add("注销");
//		this.logOutBox.getItems().add("切换账号");
//		logOutBox.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
//			logOffBtnAction();
//		});


		MenuItem item1 = new MenuItem("注销");
		item1.setText("注销");
		item1.setOnAction((ActionEvent t) -> {
			logOffBtnAction();
		});
		MenuItem item2 = new MenuItem("切换账号");
		item2.setText("切换账号");
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
	@FXML Button Reflash;//同步按钮
//	@FXML Button logOffBtn;//注销
//	@FXML Button switchAccountBtn;//切换账号
	//@FXML ChoiceBox<String> logOutBox;
	@FXML MenuButton menu;
	/**
	 * 同步应该是没有网以后进行的
	 */
	public void Reflash(){
		ClientConServerThread.Send_Message_writetoday();
		System.out.println("执行1");
		ClientConServerThread.Send_Message_writethisweek();
		System.out.println("执行2");
		ClientConServerThread.Send_Message_writethisMonth();
		System.out.println("执行3");
		ClientConServerThread.Send_Message_writetotalNumber();
		System.out.println("执行4");

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