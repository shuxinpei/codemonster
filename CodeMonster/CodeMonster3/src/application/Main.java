package application;

import TCP.ClientConServerThread;
import TCP.ClientOnServer;
import Util.KeyHookUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//交互性的功能实现完备
public class Main extends Application {
	double x1;
	double y1;
	double Xoffset = 0;
	double Yoffset = 0;
	boolean flag = false;
	Scene scene;
	public static  Button btn1;
	public static Button btn2;
	public static int CountNumber=0;
	KeyHookUtil keyhook;
	@Override
	public void start(Stage primaryStage) {
		try {
			btn1 = new Button("个人中心");
			btn2 = new Button("暂未开始");
			btn2.setId("notRecord");
			HBox box = new HBox();
			box.getChildren().add(btn2);
			box.getChildren().add(btn1);
			box.setStyle("-fx-background:transparent;");

			scene = new Scene(box, 400, 60);
			scene.setFill(null);
			scene.getStylesheets().add("/UI/main.css");

			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX(primaryScreenBounds.getWidth() - 400);
			primaryStage.setY(primaryScreenBounds.getHeight() - 60);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();

			keyhook=new KeyHookUtil(btn2);

			btn1.setOnMouseClicked((MouseEvent event) -> {
				event.consume();
				changeView();
			});
			btn2.setOnMouseClicked((MouseEvent event) -> {
				event.consume();
				Xoffset = event.getSceneX();
				Yoffset = event.getSceneY();
				CountNumber++;
				if(CountNumber==1){
					System.out.println("执行1");
					btn2.setId("Record");
					btn2.getStylesheets().add("/UI/main.css");
					keyhook.start();
				}
				if(CountNumber%2==0){//不进行改变文本数据
					System.out.println("执行2");
					btn2.setId("notRecord");
					btn2.setText("暂停记录");
					btn2.getStylesheets().add("/UI/main.css");
				}else{								//进行监听
						btn2.setId("Record");
						btn2.setText("正常记录");
						btn2.getStylesheets().add("/UI/main.css");
					}
			});
			btn2.setOnMouseDragged((MouseEvent event) -> {
				event.consume();
				primaryStage.setX(event.getScreenX() - Xoffset);
				if (event.getScreenY() - Yoffset < 0) {
					primaryStage.setY(0);
				} else {
					primaryStage.setY(event.getScreenY() - Yoffset);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeView()  {
		try {
			Parent root ;
			if(ClientConServerThread.user==null)
				root= FXMLLoader.load(getClass().getResource("/UI/Main_Login.fxml"));
			else
				root= FXMLLoader.load(getClass().getResource("/UI/Main_User.fxml"));
			Stage secondWindow=new Stage();
			Scene scene=new Scene(root);
			//使界面大小不变
			secondWindow.setHeight(620);
			secondWindow.setResizable(false);
			scene.getStylesheets().add("/UI/main2.css");
			secondWindow.setTitle("CodeMaster");
			secondWindow.setScene(scene);
			//设置图标,之后还要改一下图片2333
			secondWindow.getIcons().add(new Image("/UI/1172135.gif"));
			secondWindow.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void  ChangeBtn2(int number){
		btn2.setText(String.valueOf(number)+"行");
	}
	public static void main(String[] args) {
		launch(args);
	}
}
