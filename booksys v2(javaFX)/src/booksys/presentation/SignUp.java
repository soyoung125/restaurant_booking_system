/*
 * Restaurant Booking System: SignUp
 *
 * ȸ������ â
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
	protected Label Top; // ȸ������ ��

	protected Label IDLabel; // ���̵� ��
	protected Label PWLabel; // ��й�ȣ ��
	protected Label checkPWLabel; // ��й�ȣ Ȯ�� ��
	protected Label nameLabel; // �̸� ��
	protected Label phoneLabel; // ��ȭ��ȣ ��

	protected Label spaceLabel; // �����0
	protected Label spaceLabel1; // �����1
	protected Label spaceLabel2; // �����02

	protected TextField ID; // ���̵�
	protected TextField PW; // ��й�ȣ
	protected TextField checkPW; // ��й�ȣ Ȯ��
	protected TextField name; // �̸�
	protected TextField phone; // ��ȭ��ȣ

	protected Button join; // ȸ������
	protected Button cancel; // ���

	// ȸ������ ������
	public SignUp() {
		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);
		primaryStage.setTitle("ȸ������");
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

		Top = new Label("ȸ�������������Դϴ�\n\n");
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

		checkPWLabel = new Label("��й�ȣ Ȯ�� : ");
		checkPWLabel.setTextAlignment(TextAlignment.LEFT);
		checkPW = new TextField();

		nameLabel = new Label("�̸� : ");
		nameLabel.setTextAlignment(TextAlignment.LEFT);
		name = new TextField();

		phoneLabel = new Label("��ȭ��ȣ : ");
		phoneLabel.setTextAlignment(TextAlignment.LEFT);
		phone = new TextField();

		join = new Button("ȸ������");
		join.setMaxWidth(Double.MAX_VALUE);

		// ���Թ�ư�� ������ �� ����
		join.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// �Է»��� ����
				if (ID.getText().equals("") || PW.getText().equals("") || checkPW.getText().equals("")
						|| name.getText().equals("") || phone.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("�Է� �׸� ����");
					alert.setContentText("�Է����� ���� ������ �ֽ��ϴ�.");
					alert.showAndWait();
				}

				else if (!PW.getText().equals(checkPW.getText())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("��й�ȣ Ȯ�� ����");
					alert.setContentText("�Է��Ͻ� ��й�ȣ�� ���� �ٸ��ϴ�");
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
							alert.setHeaderText("���̵� �׸� ����");
							alert.setContentText("�Է��Ͻ� ���̵� �̹� �ֽ��ϴ�.");
							alert.show();
						} else { // ��� �Է»����� ���ؿ� �����ϸ� ���� �Ϸ�
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
							alert.setHeaderText("���� �Ϸ�");
							alert.setContentText("ȸ�����ԵǾ����ϴ�.");
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

		// Grid ���̾ƿ� ����
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