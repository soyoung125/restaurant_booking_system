/*
 * Restaurant Booking System: BookingDialog
 *
 * 예약, 방문예약 생성 창
 */

package booksys.presentation;

import booksys.application.domain.Booking;
import booksys.application.domain.BookingSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Time;
import java.util.Enumeration;

public class BookingDialog {
	protected Label timeLabel; // 시간라벨
	protected Label coversLabel; // 인원라벨
	protected Label tableNumberLabel; // 테이블넘버 라벨
	protected TextField time; // 시간
	protected TextField covers; // 인원
	protected Button ok; // 확인버튼
	protected Button cancel; // 취소버튼
	protected ComboBox tableNumber; // 테이블 넘버 선택
	protected boolean confirmed; // 확인버튼 눌렸는지 체크
	protected Stage dialog; // 창
	protected ObservableList<String> items; // 테이블넘버 설정을 위한 리스트

	// 예약창
	public BookingDialog(Stage primaryStage, String title, Booking booking) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle(title);

		Enumeration enums = BookingSystem.getTableNumbers().elements();
		items = FXCollections.observableArrayList();
		while (enums.hasMoreElements()) {
			items.add((enums.nextElement()).toString());
		}

		tableNumberLabel = new Label("Table Number:");
		tableNumberLabel.setMaxWidth(Double.MAX_VALUE);
		tableNumberLabel.setTextAlignment(TextAlignment.RIGHT);
		tableNumber = new ComboBox();
		tableNumber.setMaxWidth(Double.MAX_VALUE);
		tableNumber.setItems(items);
		if (booking != null) {
			tableNumber.setValue(Integer.toString(booking.getTable().getNumber()));
		}

		coversLabel = new Label("Covers:");
		coversLabel.setMaxWidth(Double.MAX_VALUE);
		coversLabel.setTextAlignment(TextAlignment.RIGHT);
		covers = new TextField();
		if (booking != null) {
			covers.setText(Integer.toString(booking.getCovers()));
		}

		timeLabel = new Label("Time:");
		timeLabel.setMaxWidth(Double.MAX_VALUE);
		timeLabel.setTextAlignment(TextAlignment.RIGHT);
		time = new TextField();
		time.setText("HH:MM:SS");
		if (booking != null) {
			time.setText(booking.getTime().toString());
		}

		ok = new Button("OK");
		ok.setMaxWidth(Double.MAX_VALUE);
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmed = true;
				dialog.hide();
			}
		});

		cancel = new Button("Cancel");
		cancel.setMaxWidth(Double.MAX_VALUE);
		cancel.setOnAction((new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmed = false;
				dialog.hide();
			}
		}));
		dialog.setResizable(false);
	}

	int getTableNumber() {
		return Integer.parseInt(String.valueOf(tableNumber.getValue()));
	}

	int getTablesize() {
		return items.size();
	}

	int getCovers() {
		return Integer.parseInt(covers.getText());
	}

	Time getTime() {
		return Time.valueOf(time.getText());
	}

	boolean isConfirmed() {
		return confirmed;
	}
}