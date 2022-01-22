/*
 * Restaurant Booking System: StaffUI
 *
 * 에약 캔버스
 */

package booksys.presentation;

import booksys.application.domain.*;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

public class StaffUI extends Canvas implements BookingObserver {
	final static int LEFT_MARGIN = 50; // 좌측 여백
	final static int TOP_MARGIN = 50; // 위 여백
	final static int BOTTOM_MARGIN = 110; // 아래 여백
	final static int ROW_HEIGHT = 30; // 열 높이
	final static int COL_WIDTH = 60; // 행 너비

	final static int PPM = 2; // Pixels per minute
	final static int PPH = 60 * PPM; // Pixels per hours
	final static int TZERO = 18; // Earliest time shown
	final static int SLOTS = 12; // Number of booking slots shown

	// Routines to convert between (x, y) and (time, table)

	private int timeToX(Time time) {
		return LEFT_MARGIN + PPH * (time.getHours() - TZERO) + PPM * time.getMinutes();
	}

	private Time xToTime(int x) {
		x -= LEFT_MARGIN;
		int h = (x / PPH) + TZERO;
		int m = (x % PPH) / PPM;
		return new Time(h, m, 0);
	}

	private int tableToY(int table) {
		return TOP_MARGIN + (ROW_HEIGHT * (table - 1));
	}

	private int yToTable(int y) {
		return ((y - TOP_MARGIN) / ROW_HEIGHT) + 1;
	}

	// Data members
	Customer c; // 고객
	private BookingSystem bs; // 예약시스템
	private Vector tableNumbers; // 테이블 숫자
	private int firstX, firstY, currentX, currentY; // 위치
	private boolean mouseDown; // 마우스 클릭되었는지 확인
	private Stage parentStage; // 창
	double canvasSize_Height; // 캔버스 높이
	double canvasSize_Width; // 캔버스 너비
	GraphicsContext og; // 캔버스 그래픽
	Canvas canvas; // 캔버스
	BorderPane root; // 캔버스 담을 컨테이너

	public StaffUI(BorderPane bookAppRoot, Customer cust) {
		tableNumbers = BookingSystem.getTableNumbers();
		c = cust;
		root = bookAppRoot;
		canvasSize_Height = TOP_MARGIN + tableNumbers.size() * ROW_HEIGHT + BOTTOM_MARGIN;
		canvasSize_Width = LEFT_MARGIN + (SLOTS * COL_WIDTH);

		root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		bs = BookingSystem.getInstance();
		bs.addObserver(this);
		Calendar now = Calendar.getInstance();
		bs.display(new Date(now.getTimeInMillis()));

		canvas = new Canvas(canvasSize_Width, canvasSize_Height);
		// Get the graphics context of the canvas
		og = canvas.getGraphicsContext2D();
		paint(og);

		root.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			currentX = firstX = (int) e.getX();
			currentY = firstY = (int) e.getY();
			if (e.getButton() == MouseButton.PRIMARY) {
				mouseDown = true;
				bs.selectBooking(yToTable(firstY), xToTime(firstX));
			}

		});
		root.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
			mouseDown = false;
			bs.transfer(xToTime(currentX), yToTable(currentY));
		});

		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
			currentX = (int) e.getX();
			currentY = (int) e.getY();
			update();
		});
		root.getChildren().add(canvas);

	}

	public void update() {
		if (canvas != null) {
			canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			update(canvas.getGraphicsContext2D());
		}
	}

	public void paint(GraphicsContext g) {
		update(g);
	}

	public void update(GraphicsContext og) {
		og.strokeRect(0, 0, canvasSize_Width, canvasSize_Height);
		og.setStroke(Color.BLACK);

		// Draw screen outlines

		og.strokeLine(LEFT_MARGIN, 0, LEFT_MARGIN, canvasSize_Height);
		og.strokeLine(0, TOP_MARGIN, canvasSize_Width, TOP_MARGIN);

		// Write table numbers and horizontal rules

		for (int i = 0; i < tableNumbers.size(); i++) {
			int y = TOP_MARGIN + (i + 1) * ROW_HEIGHT;
			og.strokeText(Integer.toString(i + 1), 0, y);
			og.strokeLine(LEFT_MARGIN, y, canvasSize_Width, y);
		}

		// Write time labels and vertical rules

		for (int i = 0; i < SLOTS; i++) {
			String tmp = (TZERO + (i / 2)) + (i % 2 == 0 ? ":00" : ":30");
			int x = LEFT_MARGIN + i * COL_WIDTH;
			og.strokeText(tmp, x, 40);
			og.strokeLine(x, TOP_MARGIN, x, canvasSize_Height - BOTTOM_MARGIN);
		}

		// Display booking information
		og.setFill(Color.BLACK);
		og.fillText(((java.util.Date) bs.getCurrentDate()).toString(), LEFT_MARGIN, 20);

		Enumeration enums = bs.getBookings();
		while (enums.hasMoreElements()) {
			Booking b = (Booking) enums.nextElement();
			int x = timeToX(b.getTime());
			int y = tableToY(b.getTable().getNumber());
			og.setFill(Color.GRAY);
			og.fillRect(x, y, 4 * COL_WIDTH, ROW_HEIGHT);
			if (b == bs.getSelectedBooking()) {
				og.setFill(Color.RED);
				og.fillRect(x, y, 4 * COL_WIDTH, ROW_HEIGHT);
			}
			og.setFill(Color.BLACK);
			og.fillText(b.getDetails(), x, y + ROW_HEIGHT / 2); // 문제
		}

		Booking b = bs.getSelectedBooking();
		if (mouseDown && b != null) {
			int x = timeToX(b.getTime()) + currentX - firstX;
			int y = tableToY(b.getTable().getNumber()) + currentY - firstY;
			og.setFill(Color.RED);
			og.fillRect(x, y, 4 * COL_WIDTH, ROW_HEIGHT);
		}
	}

	public boolean message(String message, boolean confirm) {
		ConfirmDialog d = new ConfirmDialog(parentStage, message, confirm);
		return d.isConfirmed();
	}

	void displayDate() {
		DateDialog d = new DateDialog(parentStage, "Enter a date");
		if (d.isConfirmed()) {
			Date date = d.getDate();
			bs.display(date);
		}

	}

	void addReservation() {
		ReservationDialog d = new ReservationDialog(parentStage, "Enter reservation details");
		if (d.isConfirmed()) {
			bs.makeReservation(d.getCovers(), bs.getCurrentDate(), d.getTime(), d.getTableNumber(), c.getName(),
					c.getPhoneNumber());
		}

	}

	void addWalkIn() {
		WalkInDialog d = new WalkInDialog(parentStage, "Enter walk-in details");
		if (d.isConfirmed()) {
			bs.makeWalkIn(d.getCovers(), bs.getCurrentDate(), d.getTime(), d.getTableNumber());
		}

	}

	void cancel() {
		bs.cancel();
	}

	void recordArrival() {
		Calendar now = Calendar.getInstance();
		bs.recordArrival(new Time(now.getTimeInMillis()));
	}

	int getTablenumbers() {
		return tableNumbers.size();
	}
}