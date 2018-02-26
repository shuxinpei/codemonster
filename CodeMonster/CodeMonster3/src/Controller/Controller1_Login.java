package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.robot.impl.FXRobotHelper;

import Common.MessageType;
import Common.User;
import TCP.ClientConServerThread;
import TCP.ClientOnServer;
import Util.AlertBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller1_Login  implements Initializable{
	@FXML Button UserImage;
	@FXML Button CodeInfo;
	@FXML Button Friend;
	@FXML Button AboutUs;
	@FXML Button Nothing;
	public static ClientOnServer myserver=new ClientOnServer();

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

	}

	@FXML TextField account;
	@FXML TextField key;
	@FXML Button loginBtn;
	@FXML Button registerBtn;

	public void LoginBtnAction(){
		String Username=account.getText();
		String Psw =key.getText();
		if(Username.equals("")){
			new AlertBox().display("警告", "账号不能为空");
		}else if(Psw.equals("")){
			new AlertBox().display("警告", "密码不能为空");
		}else{
		User user=new User();
		user.setMessage_type(MessageType.message_Login);
		user.setUserName(Username);
		user.setUserPsw(Psw);
		boolean b=myserver.sendLoginInfoToServer(user);//发送用户登陆请求
		//若登录成功跳转到用户中心
			if(b){
				ObservableList<Stage> stage = FXRobotHelper.getStages();
				 Scene scene;
				try {
					scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_User.fxml")));
					scene.getStylesheets().add("/UI/main2.css");
					stage.get(1).setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void jumpToRegisterFrame(){
		ObservableList<Stage> stage = FXRobotHelper.getStages();
		 Scene scene;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_Registe.fxml")));
			scene.getStylesheets().add("/UI/main2.css");
			stage.get(1).setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}