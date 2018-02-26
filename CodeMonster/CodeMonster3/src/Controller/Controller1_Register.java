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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller1_Register  implements Initializable{
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
	}
	@FXML TextField ID;
	@FXML PasswordField key;
	@FXML PasswordField key1;
	@FXML Button registerBtn;
	@FXML Button back;

	public void back(){
		try{
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			 Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/Main_Login.fxml")));
			 scene.getStylesheets().add("/UI/main2.css");
			 stage.get(1).setScene(scene);
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	public void registerBtn(){
		String UserName=ID.getText();
		String Psw1=key.getText();
		String Psw2=key1.getText();
		System.out.println(Psw1);
		System.out.println(Psw2);
		System.out.println(Psw1.equals(Psw2));
		if(UserName.equals("")){
			new AlertBox().display("警告", "账号不能为空");
		}else if(Psw1.equals("")||Psw2.equals("")){
			new AlertBox().display("警告", "密码不能为空");
		}else if(!Psw1.equals(Psw2)){
			new AlertBox().display("警告", "两次输入密码不一致");
		}else{
			User user=new User();
			user.setMessage_type(MessageType.message_Registe);
			user.setRegisterTime(System.currentTimeMillis());
			user.setUserName(UserName);
			user.setUserPsw(Psw2);
			if(Controller1_Login.myserver==null){
				Controller1_Login.myserver=new ClientOnServer();
			}
			boolean b=Controller1_Login.myserver.sendRegisteInfoToServer(user);
			if(!b){
				new AlertBox().display("失败","注册失败，用户名已经被使用");
			}else{//登录成功进行跳转
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
			ID.clear();
			key.clear();
			key1.clear();
		}
	}
}

