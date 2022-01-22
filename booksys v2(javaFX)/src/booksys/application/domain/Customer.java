/*
 * Restaurant Booking System: Customer
 *
 * 고객 정보를 저장합니다.
 */

package booksys.application.domain ;

public class Customer
{
  private String userId;          //고객의 아이디
  private String password;        //고객의 비밀번호
  private String name ;           //이름
  private String phoneNumber ;    //전화번호
  private int reservationCount;   //예약 횟수
  private int arrivalCount;       //도착 횟수
  private int grade;              //등급

  public Customer(String id, String pw, String n, String p)
  {
    userId = id;
    password = pw;
    name = n ;
    phoneNumber = p ;
    reservationCount = 0;
    arrivalCount = 0;
    grade = 1;
  }

  public String getUserId() { return userId; }

  public String getPassword() { return password; }

  public String getName()
  {
    return name ;
  }

  public String getPhoneNumber()
  {
    return phoneNumber ;
  }

  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

  public int getArrivalCount() {
    return arrivalCount;
  }

  public void setArrivalCount(int arrivalCount) {
    this.arrivalCount = arrivalCount;
  }

  public int getReservationCount() {
    return reservationCount;
  }

  public void setReservationCount(int reservationCount) {
    this.reservationCount = reservationCount;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public void print() {
	// TODO Auto-generated method stub
	System.out.println("userId = " + userId + "password = " + password +
    "name = " + name + "phoneNumber = " + phoneNumber + "reservationCount = " + reservationCount + 
    "arrivalCount = " + arrivalCount + "grade = " + grade);
  }
}