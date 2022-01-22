/*
 * Restaurant Booking System: SignUp
 *
 * 회원가입 창
 */

package booksys.presentation;

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
import org.json.simple.parser.JSONParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SignUp {
	protected Label Top; // 회원가입 라벨

	protected Label IDLabel; // 아이디 라벨
	protected Label PWLabel; // 비밀번호 라벨
	protected Label checkPWLabel; // 비밀번호 확인 라벨
	protected Label nameLabel; // 이름 라벨
	protected Label phoneLabel; // 전화번호 라벨

	protected Label spaceLabel; // 공백라벨0
	protected Label spaceLabel1; // 공백라벨1
	protected Label spaceLabel2; // 공백라벨02

	protected TextField ID; // 아이디
	protected TextField PW; // 비밀번호
	protected TextField checkPW; // 비밀번호 확인
	protected TextField name; // 이름
	protected TextField phone; // 전화번호

	protected Button join; // 회원가입
	protected Button cancel; // 취소

	// 회원가입 페이지
	public SignUp() {
		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.setTitle("회원가입");
		primaryStage.setHeight(250);
		primaryStage.setWidth(280);
		primaryStage.setResizable(false);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				primaryStage.hide();
			}
		});

		BorderPane layout = new BorderPane();

		Top = new Label("회원가입페이지입니다\n\n");
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

		checkPWLabel = new Label("비밀번호 확인 : ");
		checkPWLabel.setTextAlignment(TextAlignment.LEFT);
		checkPW = new TextField();

		nameLabel = new Label("이름 : ");
		nameLabel.setTextAlignment(TextAlignment.LEFT);
		name = new TextField();

		phoneLabel = new Label("전화번호 : ");
		phoneLabel.setTextAlignment(TextAlignment.LEFT);
		phone = new TextField();

		join = new Button("회원가입");
		join.setMaxWidth(Double.MAX_VALUE);

		// 가입버튼을 눌렀을 때 동작
		join.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 입력사항 검토
				if (ID.getText().equals("") || PW.getText().equals("") || checkPW.getText().equals("")
						|| name.getText().equals("") || phone.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("입력 항목 오류");
					alert.setContentText("입력하지 않은 사항이 있습니다.");
					alert.showAndWait();
				}

				else if (!PW.getText().equals(checkPW.getText())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("비밀번호 확인 오류");
					alert.setContentText("입력하신 비밀번호가 서로 다릅니다");
					alert.showAndWait();
				}

				else {
					try {
						URL url = new URL("http://corodiak.gotdns.org:33333/api/customer/find/" + ID.getText());
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						JSONParser jsonPar = new JSONParser();
						if (br.readLine() != null) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning Dialog");
							alert.setHeaderText("아이디 항목 오류");
							alert.setContentText("입력하신 아이디가 이미 있습니다.");
							alert.show();
						} else { // 모든 입력사항이 기준에 부합하면 가입 완료
							url = new URL("http://corodiak.gotdns.org:33333/api/customer/save");
							conn = (HttpURLConnection) url.openConnection();
							conn.setRequestProperty("Content-Type", "application/json");
							conn.setDoOutput(true);
							String json = "{\"userId\":\"" + ID.getText() + "\",\"password\":\"" + PW.getText()
									+ "\",\"name\":\"" + name.getText() + "\",\"phoneNumber\":\"" + phone.getText()
									+ "\"}";
							OutputStream os = conn.getOutputStream();
							byte[] input = json.getBytes();
							os.write(input, 0, input.length);
							br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("가입 완료");
							alert.setContentText("회원가입되었습니다.");
							alert.showAndWait();
							primaryStage.hide();
							SignIn SI = new SignIn();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		cancel = new Button("Cancel");
		cancel.setMaxWidth(Double.MAX_VALUE);
		cancel.setOnAction((new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				SignIn SI = new SignIn();

			}
		}));

		// Grid 레이아웃 적용
		GridPane Bottom = new GridPane();

		Bottom.add(spaceLabel, 0, 0);
		Bottom.add(IDLabel, 0, 1);
		Bottom.add(ID, 1, 1);
		Bottom.add(PWLabel, 0, 2);
		Bottom.add(PW, 1, 2);
		Bottom.add(checkPWLabel, 0, 3);
		Bottom.add(checkPW, 1, 3);
		Bottom.add(nameLabel, 0, 4);
		Bottom.add(name, 1, 4);
		Bottom.add(phoneLabel, 0, 5);
		Bottom.add(phone, 1, 5);
		Bottom.add(spaceLabel1, 0, 6);
		Bottom.add(spaceLabel2, 0, 7);
		Bottom.add(join, 0, 8);
		Bottom.add(cancel, 1, 8);

		layout.setTop(Top);

		layout.setCenter(Bottom);
		Scene scene = new Scene(layout, primaryStage.getWidth(), primaryStage.getHeight());
		primaryStage.setScene(scene);
		primaryStage.show();
		;

	}
}