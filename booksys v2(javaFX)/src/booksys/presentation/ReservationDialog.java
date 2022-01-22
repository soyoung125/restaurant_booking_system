/*
 * Restaurant Booking System: ReservationDialog
 *
 * ���� ����
 */

package booksys.presentation;

import booksys.application.domain.Reservation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;

public class ReservationDialog extends BookingDialog {
	protected Label nameLabel; // �̸� ��
	protected Label phoneLabel; // ��ȭ��ȣ ��
	protected TextField name; // �̸�
	protected TextField phone; // ��ȭ��ȣ
	Random rand = new Random(); // ���� ���

	public ReservationDialog(Stage owner, String title) {
		this(owner, title, null);
	}

	// This constructor initializes fields with data from an existing booking.
	// This is useful for completing Exercise 7.6.

	ReservationDialog(Stage owner, String title, Reservation r) {
		super(owner, title, r);
		/*
		 * nameLabel = new Label("Name:");
		 * nameLabel.setTextAlignment(TextAlignment.RIGHT); name = new TextField(); if
		 * (r != null) { name.setText(r.getCustomer().getName()) ; }
		 * 
		 * phoneLabel = new Label("Phone Number:");
		 * phoneLabel.setTextAlignment(TextAlignment.RIGHT); phone = new TextField(); if
		 * (r != null) { phone.setText(r.getCustomer().getPhoneNumber()) ; }
		 */
		int tablesize = super.getTablesize(); // ���̺� ����
		ok.setOnAction(null);
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (time.getText().isEmpty() || covers.getText().isEmpty()) { // || tableNumber.getValue() == null
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("�Է� �׸� ����");
					alert.setContentText("�Է����� ���� ������ �ֽ��ϴ�.");
					alert.showAndWait();
				} else if (!time.getText().matches("((1(8|9)|2(0|1)):[0-5][0-9]:[0-5][0-9]|22:00:00)")) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("�Է� �׸� ����");
					alert.setContentText("�ð��� �߸� �ԷµǾ����ϴ�. ���� ���� �ð��� 18:00:00 ~ 22:00:00�Դϴ�.");
					alert.showAndWait();
				} else if (!Character.isDigit(covers.getText().charAt(0))) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("�Է� �׸� ����");
					alert.setContentText("���ڰ� �ƴ� ���ڰ� �Է��� �Ǿ����ϴ�.");
					alert.showAndWait();
				} else {
					if (tableNumber.getValue() == null) {
						int a = rand.nextInt(tablesize);
						tableNumber.setValue(a + 1);
					}
					confirmed = true;
					dialog.hide();
				}
			}
		});

		// Grid ���̾ƿ� ����
		GridPane root = new GridPane();
		root.add(timeLabel, 0, 0);
		root.add(time, 1, 0);
		root.add(coversLabel, 0, 1);
		root.add(covers, 1, 1);
		root.add(tableNumberLabel, 0, 2);
		root.add(tableNumber, 1, 2);
		root.add(ok, 0, 3);
		root.add(cancel, 1, 3);
		dialog.setScene(new Scene(root));
		dialog.showAndWait();
	}

	String getCustomerName() {
		return name.getText();
	}

	String getPhoneNumber() {
		return phone.getText();
	}
}