/*
 * Restaurant Booking System: Restaurant
 *
 * Mapper를 이용해 예약을 관리합니다.
 */

package booksys.application.domain ;

import booksys.application.persistency.* ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.Vector ;

class Restaurant
{
  BookingMapper  bm = BookingMapper.getInstance() ;      //bookingMapper
  CustomerMapper cm = CustomerMapper.getInstance() ;     //CustomerMapper
  TableMapper    tm = TableMapper.getInstance() ;        //TableMapper
  
  Vector getBookings(Date date)
  {
    return bm.getBookings(date) ;
  }

  Customer getCustomer(String name, String phone)
  {
    return cm.getCustomer(name, phone) ;
  }
  
  Table getTable(int n)
  {
    return tm.getTable(n) ;
  }

  static Vector getTableNumbers()
  {
    return TableMapper.getInstance().getTableNumbers() ;
  }

  //예약 생성
  public Booking makeReservation(int covers, Date date,
				     Time time,
				     int tno, String name, String phone)
  {
    Table t = getTable(tno) ;    //선택한 테이블 번호의 객체
    Customer c = getCustomer(name, phone) ;   //예약한 고객의 객체
    return bm.createReservation(covers, date, time, t, c, null) ;
  }

  public Booking makeWalkIn(int covers, Date date,
			   Time time, int tno)
  {
    Table t = getTable(tno) ;   //선택한 테이블 번호의 객체
    return bm.createWalkIn(covers, date, time, t) ;
  }

  public void updateBooking(Booking b)
  {
    bm.updateBooking(b) ;
  }
  
  public void removeBooking(Booking b) {
    bm.deleteBooking(b) ;
  }
}