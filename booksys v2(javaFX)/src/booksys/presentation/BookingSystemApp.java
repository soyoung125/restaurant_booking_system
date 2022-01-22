/*
 * Restaurant Booking System: BookingSystemApp
 *
 * 로그인 시 나오는 메인 창
 */

package booksys.presentation;

import booksys.UserManager.userManager;
import booksys.application.domain.Customer;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class BookingSystemApp {

	StaffUI ui; // 예약을 보여주는 캔버스
	ModifyInfo mi; // 정보수정 창

	public BookingSystemApp(Customer cust) {

		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);

		primaryStage.setTitle("Restaurant Booking System");
		primaryStage.setResizable(false);

		// 메뉴바와 메뉴 구현
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");

		MenuItem quit = new MenuItem("Quit"); // 종료
		quit.setOnAction(e -> {
			System.exit(0);
		});

		MenuItem logout = new MenuItem("LogOut"); // 로그아웃
		logout.setOnAction(e -> {
			primaryStage.close();
			SignIn SI = new SignIn();
		});

		fileMenu.getItems().add(quit);
		fileMenu.getItems().add(logout);
		menuBar.getMenus().add(fileMenu);

		Menu dateMenu = new Menu("Date");

		MenuItem display = new MenuItem("Display..."); // 날짜변경
		display.setOnAction(e -> {
			ui.displayDate();
		});

		dateMenu.getItems().add(display);
		menuBar.getMenus().add(dateMenu);

		Menu bookingMenu = new Menu("Booking");

		MenuItem newReservation = new MenuItem("New Reservation..."); // 예약 생성
		newReservation.setOnAction(e -> {
			ui.addReservation();
		});
		bookingMenu.getItems().add(newReservation);

		MenuItem newWalkIn = new MenuItem("New Walk-in..."); // 워크인 생성
		newWalkIn.setOnAction(e -> {
			ui.addWalkIn();
		});
		bookingMenu.getItems().add(newWalkIn);

		MenuItem cancel = new MenuItem("Cancel"); // 취소
		cancel.setOnAction(e -> {
			ui.cancel();
		});
		bookingMenu.getItems().add(cancel);

		MenuItem recordArrival = new MenuItem("Record Arrival"); // 도착 기록
		recordArrival.setOnAction(e -> {
			ui.recordArrival();
		});
		bookingMenu.getItems().add(recordArrival);

		menuBar.getMenus().add(bookingMenu);

		Menu manage = new Menu("manage");

		MenuItem modify = new MenuItem("Modify"); // 정보수정
		modify.setOnAction(e -> {
			mi = new ModifyInfo(primaryStage, cust);
		});

		manage.getItems().add(modify);

		// 0등급은 관리자 기능이 보이도록
		if (cust.getGrade() == 0) {
			MenuItem usermanager = new MenuItem("User Manager"); // 유저관리
			usermanager.setOnAction(e -> {
				userManager u = new userManager();

			});
			manage.getItems().add(usermanager);
		}

		menuBar.getMenus().add(manage);

		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});

		BorderPane root = new BorderPane();
		ui = new StaffUI(root, cust);

		primaryStage.setHeight(ui.canvasSize_Height);
		primaryStage.setWidth(ui.canvasSize_Width);

		BorderPane layout = new BorderPane();
		layout.setTop(menuBar);
		// staffUI 참조하여 변경
		layout.setCenter(root);

		Scene scene = new Scene(layout, primaryStage.getWidth(), primaryStage.getHeight());
		primaryStage.setScene(scene);

		primaryStage.show();
		;
	}
}