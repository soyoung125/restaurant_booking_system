/*
 * Restaurant Booking System: ModifyInfo
 *
 * 예약 정보를 저장합니다.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

public class Reservation extends BookingImp
{
  private Customer customer ;       //예약한 고객
  private Time     arrivalTime ;    //도착 시간
  
  public Reservation(int c, Date d, Time t, Table tab, Customer cust, Time arr)
  {
    super(c, d, t, tab) ;
    customer    = cust ;
    arrivalTime = arr ;
  }

  public java.sql.Time getArrivalTime() {
    return arrivalTime ;
  }

  public Customer getCustomer() {
    return customer ;
  }

  //예약의 상세정보를 문자열로 생성하여 반환
  public String getDetails()
  {
    StringBuffer details = new StringBuffer(64) ;
    details.append(customer.getName()) ;
    details.append(" ") ;
    details.append(customer.getPhoneNumber()) ;
    details.append(" (") ;
    details.append(covers) ;
    details.append(")") ;
    if (arrivalTime != null) {
      details.append(" [") ;
      details.append(arrivalTime) ;
      details.append("]") ;
    }
    return details.toString() ;
  }

  public void setArrivalTime(Time t) {
    arrivalTime = t ;
  }

  public void setCustomer(Customer c) {
    customer = c ;
  }
}