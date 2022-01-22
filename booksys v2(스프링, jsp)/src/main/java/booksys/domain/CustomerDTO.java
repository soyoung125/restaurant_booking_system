package booksys.domain;

public class CustomerDTO {
	private int oid;
	private String userId;
	private String password;
	private String name;
	private String phoneNumber;
	private int reservationCount;
	private int arrivalCount;
	private int grade;

	public CustomerDTO() {
	}

	public CustomerDTO(String userId, String name, String phoneNumber) {
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.reservationCount = 0;
		this.arrivalCount = 0;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(int reservationCount) {
		this.reservationCount = reservationCount;
	}

	public int getArrivalCount() {
		return arrivalCount;
	}

	public void setArrivalCount(int arrivalCount) {
		this.arrivalCount = arrivalCount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}
}
