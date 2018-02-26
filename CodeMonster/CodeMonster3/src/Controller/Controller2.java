package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.robot.impl.FXRobotHelper;

import TCP.ClientConServerThread;
import Util.ReadWirteFile;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.stage.Stage;

public class Controller2  implements Initializable{
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

	@FXML
	Cylinder cylinder_allData;
	DoubleProperty heightProperty = new SimpleDoubleProperty(0);
	int totalNumber = 0;

	@FXML
	AnchorPane tab2;
	@FXML
	AnchorPane tab3;
	@FXML
	Label label_totalNum;
	@FXML
	Label label_weekNum;
	@FXML
	Label label_monthNum;
	StringProperty totalNum = new SimpleStringProperty();
	StringProperty weekNum = new SimpleStringProperty();
	StringProperty  monthNum= new SimpleStringProperty();


	private static final int num = 5000;
	private static final double HEIGHT = 500;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserImage.setStyle("-fx-background-image: url( '/UI/account _1.png')");

		label_init();
		label_totalNum.setText(String.valueOf(ReadWirteFile.ReadAll()));
		label_weekNum.setText("本周" +  ReadWirteFile.ReadAllWeek() + "行");
		label_monthNum.setText("本月" + ReadWirteFile.ReadAllMonth()+ "行");

		// 圆柱初始化
		PhongMaterial phongMaterial = new PhongMaterial(Color.color(52.0/255.0,152.0/255.0,219.0/255.0));
		cylinder_allData.setMaterial(phongMaterial);
		cylinder_allData.heightProperty().bind(heightProperty);
		//得到用户总代码量，并设置
		totalNumber = ReadWirteFile.ReadAll();
		setCylinder_height(totalNumber);

/*********************************************图表**************************************************************/
		//根据最近七天的数据画面积图
		final NumberAxis xAxis1 = new NumberAxis(1, 7, 1);
		xAxis1.setId("axis");
		final NumberAxis yAxis1 = new NumberAxis();
		yAxis1.setId("axis");

		AreaChart<Number, Number> AreaChart1 = new AreaChart<>(xAxis1, yAxis1);
		AreaChart1.setTitle("最近7天代码量变化");

		AreaChart1.setId("chart");
		AreaChart1.getStylesheets().add("/css/chart1.css");

		int[] value1 = new int[7];
		String[] weekCode = ReadWirteFile.ReadWeek();
		for (int i = 0; i < 7; i++){
			value1[i] = Integer.parseInt(weekCode[i]);
		}
		XYChart.Series data_7days = new XYChart.Series();
		data_7days.setName("最近7天代码量");

		for (int i = 1; i <= 7; i++) {
			Data t = new XYChart.Data(i, value1[i - 1]);
			data_7days.getData().add(t);
		}
		AreaChart1.getData().addAll(data_7days);

		tab2.getChildren().add(AreaChart1);
		AreaChart1.setLayoutX(144);
		AreaChart1.setLayoutY(86);

		//根据最近30天数据画折线图
		final NumberAxis xAxis2 = new NumberAxis(1, 30, 1);
		xAxis2.setId("axis");
		final NumberAxis yAxis2 = new NumberAxis();
		yAxis2.setId("axis");

		LineChart<Number, Number> LineChart2 = new LineChart<>(xAxis2, yAxis2);
		LineChart2.setTitle("最近30天代码量变化");
		LineChart2.setId("chart");
		LineChart2.getStylesheets().add("/css/chart2.css");

		int[] value2 = new int[30];
		String[] monthCode = ReadWirteFile.ReadMonth();
		for (int i = 0; i < 30; i++){
			value2[i] = Integer.parseInt(monthCode[i]);
			//value2[i] = (int) (Math.random() * 200);
		}
		XYChart.Series data_30days = new XYChart.Series();
		data_30days.setName("最近30天代码量");

		for (int i = 1; i <= 30; i++) {
			Data t = new XYChart.Data(i, value2[i - 1]);
			data_30days.getData().add(t);
		}
		LineChart2.getData().addAll(data_30days);

		tab3.getChildren().add(LineChart2);
		LineChart2.setLayoutX(144);
		LineChart2.setLayoutY(86);
	}

	private void label_init() {
		ClientConServerThread.Send_Message_fortotalNumber();
		int totalCode = ReadWirteFile.ReadAll();
		int weekCode = ReadWirteFile.ReadAllWeek();
		int monthCode = ReadWirteFile.ReadAllMonth();
		totalNum.set(String.valueOf(totalCode) + "行");
		weekNum.set(String.valueOf(weekCode));
		monthNum.set(String.valueOf(monthCode));
	}

	public void setCylinder_height(int allCodeNum){
		double sigmod=1/(1+Math.pow(Math.E, -allCodeNum));
		// double height = allCodeNum / num * HEIGHT;
		sigmod*=100;
		 this.heightProperty.set(sigmod);
	}

}
