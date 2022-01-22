/*
 * Restaurant Booking System: SignIn
 *
 * 프로그램 시작(로그인 창)
 */

package booksys.presentation;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mindrot.BCrypt;

import booksys.application.domain.Customer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SignIn extends Application {

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	// 로그인 화면
	public SignIn() {

		Label Top; // 로그인 라벨

		Label IDLabel; // 아이디 라벨
		Label PWLabel; // 비밀번호 라벨

		Label spaceLabel; // 공백 라벨0
		Label spaceLabel1; // 공백 라벨1
		Label spaceLabel2; // 공백 라벨2

		TextField ID; // 아이디
		TextField PW; // 비밀번호

		Button login; // 로그인버튼
		Button join; // 회원가입 버튼

		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.setTitle("로그인");
		primaryStage.setHeight(190);
		primaryStage.setWidth(280);
		primaryStage.setResizable(false);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.exit(0);
			}
		});

		BorderPane layout = new BorderPane();

		Top = new Label("로그인 화면\n\n");
		Top.setMinWidth(primaryStage.getWidth());
		Top.setAlignment(Pos.CENTER);

		spaceLabel = new Label();
		spaceLabel.setMinWidth(100);
		;
		spaceLabel1 = new Label();
		spaceLabel1.setMinWidth(100);
		;
		spaceLabel2 = new Label();
		spaceLabel2.setMinWidth(100);
		;

		IDLabel = new Label("아이디 : ");
		IDLabel.setTextAlignment(TextAlignment.LEFT);
		ID = new TextField();

		PWLabel = new Label("비밀번호 : ");
		PWLabel.setTextAlignment(TextAlignment.LEFT);
		PW = new TextField();

		login = new Button("로그인");
		login.setMaxWidth(Double.MAX_VALUE);
		// 로그인버튼을 눌렀을 때 동작
		login.setOnAction((new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 입력사항 검토
				if (ID.getText().equals("") || PW.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("입력 항목 오류");
					alert.setContentText("입력하지 않은 사항이 있습니다.");
					alert.showAndWait();
				} else
					try {

						URL url = new URL("http://corodiak.gotdns.org:33333/api/customer/find/" + ID.getText());
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						JSONParser jsonPar = new JSONParser();
						String IDcheck = br.lines().collect(Collectors.joining());
						if (IDcheck.equals("")) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning Dialog");
							alert.setHeaderText("아이디 항목 오류");
							alert.setContentText("입력하신 아이디가 존재하지 않습니다.");
							alert.show();
						}
						JSONObject jsonObj = (JSONObject) jsonPar.parse(IDcheck);
						String password = (String) jsonObj.get("password");
						String temp = jsonObj.get("grade").toString();
						int grade = Integer.parseInt(temp);
						String arrivalCount = jsonObj.get("arrivalCount").toString();
						String reservationCount = jsonObj.get("reservationCount").toString();

						if (BCrypt.checkpw((PW.getText()), password)) {
							Customer cust = new Customer(ID.getText(), PW.getText(), (String) jsonObj.get("name"),
									(String) jsonObj.get("phoneNumber"));
							cust.setGrade(grade);
							cust.setArrivalCount(Integer.parseInt(arrivalCount));
							cust.setReservationCount(Integer.parseInt(reservationCount));

							// 0등급은 관리자, 6이상의 등급은 차단된 사용자
							if (grade == 0) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("관리자님");
								alert.setContentText("환영합니다.");
								alert.showAndWait();
							} else if (grade >= 6) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning Dialog");
								alert.setHeaderText("차단된 사용자");
								alert.setContentText("차단 사유는 관리자에게 문의하세요.");
								alert.show();
								return;
							}
							primaryStage.hide();
							BookingSystemApp BSA = new BookingSystemApp(cust);
						} else {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning Dialog");
							alert.setHeaderText("비밀번호 오류");
							alert.setContentText("비밀번호가 틀립니다.");
							alert.show();
						}

					}

					catch (Exception e) {
					}

			}
		}));

		join = new Button("회원가입");
		join.setMaxWidth(Double.MAX_VALUE);
		join.setOnAction(new EventHandler<ActionEvent>() {

			// 회원가입창으로 이동
			@Override
			public void handle(ActionEvent event) {
				SignUp s = new SignUp();
				primaryStage.hide();

			}
		});

		// Grid 레이아웃 적용
		GridPane Center = new GridPane();

		Center.add(spaceLabel, 0, 0);
		Center.add(IDLabel, 0, 1);
		Center.add(ID, 1, 1);
		Center.add(PWLabel, 0, 2);
		Center.add(PW, 1, 2);
		Center.add(spaceLabel1, 0, 3);
		Center.add(spaceLabel2, 0, 4);
		Center.add(login, 0, 5);
		Center.add(join, 1, 5);

		layout.setTop(Top);
		layout.setCenter(Center);
		Scene scene = new Scene(layout, primaryStage.getWidth(), primaryStage.getHeight());
		primaryStage.setScene(scene);
		primaryStage.show();
		;
	}
}