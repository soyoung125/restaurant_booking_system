/*
 * Restaurant Booking System: Customer
 *
 * �� ������ �����մϴ�.
 */

package booksys.application.domain ;

public class Customer
{
  private String userId;          //���� ���̵�
  private String password;        //���� ��й�ȣ
  private String name ;           //�̸�
  private String phoneNumber ;    //��ȭ��ȣ
  private int reservationCount;   //���� Ƚ��
  private int arrivalCount;       //���� Ƚ��
  private int grade;              //���

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