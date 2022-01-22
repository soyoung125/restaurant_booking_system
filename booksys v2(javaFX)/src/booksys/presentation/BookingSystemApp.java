/*
 * Restaurant Booking System: BookingSystemApp
 *
 * �α��� �� ������ ���� â
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

	StaffUI ui; // ������ �����ִ� ĵ����
	ModifyInfo mi; // �������� â

	public BookingSystemApp(Customer cust) {

		Stage primaryStage = new Stage(StageStyle.UTILITY);
		primaryStage.initModality(Modality.WINDOW_MODAL);

		primaryStage.setTitle("Restaurant Booking System");
		primaryStage.setResizable(false);

		// �޴��ٿ� �޴� ����
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");

		MenuItem quit = new MenuItem("Quit"); // ����
		quit.setOnAction(e -> {
			System.exit(0);
		});

		MenuItem logout = new MenuItem("LogOut"); // �α׾ƿ�
		logout.setOnAction(e -> {
			primaryStage.close();
			SignIn SI = new SignIn();
		});

		fileMenu.getItems().add(quit);
		fileMenu.getItems().add(logout);
		menuBar.getMenus().add(fileMenu);

		Menu dateMenu = new Menu("Date");

		MenuItem display = new MenuItem("Display..."); // ��¥����
		display.setOnAction(e -> {
			ui.displayDate();
		});

		dateMenu.getItems().add(display);
		menuBar.getMenus().add(dateMenu);

		Menu bookingMenu = new Menu("Booking");

		MenuItem newReservation = new MenuItem("New Reservation..."); // ���� ����
		newReservation.setOnAction(e -> {
			ui.addReservation();
		});
		bookingMenu.getItems().add(newReservation);

		MenuItem newWalkIn = new MenuItem("New Walk-in..."); // ��ũ�� ����
		newWalkIn.setOnAction(e -> {
			ui.addWalkIn();
		});
		bookingMenu.getItems().add(newWalkIn);

		MenuItem cancel = new MenuItem("Cancel"); // ���
		cancel.setOnAction(e -> {
			ui.cancel();
		});
		bookingMenu.getItems().add(cancel);

		MenuItem recordArrival = new MenuItem("Record Arrival"); // ���� ���
		recordArrival.setOnAction(e -> {
			ui.recordArrival();
		});
		bookingMenu.getItems().add(recordArrival);

		menuBar.getMenus().add(bookingMenu);

		Menu manage = new Menu("manage");

		MenuItem modify = new MenuItem("Modify"); // ��������
		modify.setOnAction(e -> {
			mi = new ModifyInfo(primaryStage, cust);
		});

		manage.getItems().add(modify);

		// 0����� ������ ����� ���̵���
		if (cust.getGrade() == 0) {
			MenuItem usermanager = new MenuItem("User Manager"); // ��������
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
		// staffUI �����Ͽ� ����
		layout.setCenter(root);

		Scene scene = new Scene(layout, primaryStage.getWidth(), primaryStage.getHeight());
		primaryStage.setScene(scene);

		primaryStage.show();
		;
	}
}