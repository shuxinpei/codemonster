package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.robot.impl.FXRobotHelper;

import TCP.ClientConServerThread;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller1 implements Initializable {
	@FXML
	Button UserImage;
	@FXML
	Button CodeInfo;
	@FXML
	Button Friend;
	@FXML
	Button AboutUs;
	@FXML
	Button Nothing;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
