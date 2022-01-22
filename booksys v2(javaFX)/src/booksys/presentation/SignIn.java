/*
 * Restaurant Booking System: SignIn
 *
 * ���α׷� ����(�α��� â)
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

	// �α��� ȭ��
	public SignIn() {

		Label Top; // �α��� ��

		Label IDLabel; // ���̵� ��
		Label PWLabel; // ��й�ȣ ��

		Label spaceLabel; // ���� ��0
		Label spaceLabel1; // ���� ��1
		Label spaceLabel2; // ���� ��2

		TextField ID; // ���̵�
		TextField PW; // ��й�ȣ

		Button login; // �α��ι�ư
		Button join; // ȸ������ ��ư

		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.setTitle("�α���");
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

		Top = new Label("�α��� ȭ��\n\n");
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

		IDLabel = new Label("���̵� : ");
		IDLabel.setTextAlignment(TextAlignment.LEFT);
		ID = new TextField();

		PWLabel = new Label("��й�ȣ : ");
		PWLabel.setTextAlignment(TextAlignment.LEFT);
		PW = new TextField();

		login = new Button("�α���");
		login.setMaxWidth(Double.MAX_VALUE);
		// �α��ι�ư�� ������ �� ����
		login.setOnAction((new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// �Է»��� ����
				if (ID.getText().equals("") || PW.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("�Է� �׸� ����");
					alert.setContentText("�Է����� ���� ������ �ֽ��ϴ�.");
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
							alert.setHeaderText("���̵� �׸� ����");
							alert.setContentText("�Է��Ͻ� ���̵� �������� �ʽ��ϴ�.");
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

							// 0����� ������, 6�̻��� ����� ���ܵ� �����
							if (grade == 0) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("�����ڴ�");
								alert.setContentText("ȯ���մϴ�.");
								alert.showAndWait();
							} else if (grade >= 6) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning Dialog");
								alert.setHeaderText("���ܵ� �����");
								alert.setContentText("���� ������ �����ڿ��� �����ϼ���.");
								alert.show();
								return;
							}
							primaryStage.hide();
							BookingSystemApp BSA = new BookingSystemApp(cust);
						} else {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning Dialog");
							alert.setHeaderText("��й�ȣ ����");
							alert.setContentText("��й�ȣ�� Ʋ���ϴ�.");
							alert.show();
						}

					}

					catch (Exception e) {
					}

			}
		}));

		join = new Button("ȸ������");
		join.setMaxWidth(Double.MAX_VALUE);
		join.setOnAction(new EventHandler<ActionEvent>() {

			// ȸ������â���� �̵�
			@Override
			public void handle(ActionEvent event) {
				SignUp s = new SignUp();
				primaryStage.hide();

			}
		});

		// Grid ���̾ƿ� ����
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