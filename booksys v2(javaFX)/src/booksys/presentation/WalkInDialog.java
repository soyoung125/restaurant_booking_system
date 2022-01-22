package booksys.presentation;
/*
 * Restaurant Booking System: WalkInDialog
 *
 * 방문예약 생성 창
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

		// 현장예약하기
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (time.getText().isEmpty() || covers.getText().isEmpty() || tableNumber.getValue() == null) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("입력 항목 오류");
					alert.setContentText("입력하지 않은 사항이 있습니다.");
					alert.showAndWait();
				} else if (!time.getText().matches("((1(8|9)|2(0|1)):[0-5][0-9]:[0-5][0-9]|22:00:00)")) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("입력 항목 오류");
					alert.setContentText("시간이 잘못 입력되었습니다. 예약 가능 시간은 18:00:00 ~ 22:00:00입니다.");
					alert.showAndWait();
				} else if (!Character.isDigit(covers.getText().charAt(0))) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("입력 항목 오류");
					alert.setContentText("숫자가 아닌 문자가 입력이 되었습니다.");
					alert.showAndWait();
				} else {
					confirmed = true;
					dialog.hide();
				}
			}
		});

		// Grid 레이아웃 적용
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