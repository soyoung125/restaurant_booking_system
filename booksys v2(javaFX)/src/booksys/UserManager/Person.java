package booksys.UserManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final StringProperty user,block;
	private final StringProperty userId,name,phoneNumber,reservationCount,arrivalCount,grade;
	
	public Person(String user, String userId,String name,String phoneNumber,String reservationCount,String arrivalCount,String grade, String block) {
		this.user = new SimpleStringProperty(user);
		this.userId = new SimpleStringProperty(userId);
		this.name = new SimpleStringProperty(name);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.reservationCount = new SimpleStringProperty(reservationCount);
		this.arrivalCount = new SimpleStringProperty(arrivalCount);
		this.grade = new SimpleStringProperty(grade);
		this.block = new SimpleStringProperty(block);
	}
	
	public String getuser() {
		return user.get();
	}
	public String getId() {
		return userId.get();
	}
	public String getname() {
		return name.get();
	}
	public String getphoneNumber() {
		return phoneNumber.get();
	}
	public String getreservationCount() {
		return reservationCount.get();
	}
	public String getarrivalCount() {
		return arrivalCount.get();
	}
	public String getgrade() {
		return grade.get();
	}
	
	public String getblock() {
		return block.get();
	}
	
	
	public StringProperty userProperty() {
		return user;
	}
	public StringProperty userIdProperty() {
		return userId;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}
	
	public StringProperty reservationCountProperty() {
		return reservationCount;
	}
	
	public StringProperty arrivalCountProperty() {
		return arrivalCount;
	}
	
	public StringProperty gradeProperty() {
		return grade;
	}

	public StringProperty blockProperty() {
		return block;
	}
	
}