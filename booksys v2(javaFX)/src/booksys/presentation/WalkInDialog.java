package booksys.presentation;
/*
 * Restaurant Booking System: WalkInDialog
 *
 * �湮���� ���� â
 */

import booksys.application.domain.WalkIn;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WalkInDialog extends BookingDialog {
	public WalkInDialog(Stage owner, String title) {
		this(owner, title, null);
	}

	// This constructor initializes fields with data from an existing booking.
	// This is useful for completing Exercise 7.6.

	WalkInDialog(Stage owner, String title, WalkIn w) {
		super(owner, title, w);

		// ���忹���ϱ�
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (time.getText().isEmpty() || covers.getText().isEmpty() || tableNumber.getValue() == null) {
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
					confirmed = true;
					dialog.hide();
				}
			}
		});

		// Grid ���̾ƿ� ����
		GridPane root = new GridPane();
		GridPane.setFillWidth(tableNumberLabel, true);
		root.add(tableNumberLabel, 0, 0);
		root.add(tableNumber, 1, 0);
		root.add(coversLabel, 0, 1);
		root.add(covers, 1, 1);
		root.add(timeLabel, 0, 2);
		root.add(time, 1, 2);
		root.add(ok, 0, 3);
		root.add(cancel, 1, 3);
		dialog.setScene(new Scene(root));
		dialog.showAndWait();
	}
}