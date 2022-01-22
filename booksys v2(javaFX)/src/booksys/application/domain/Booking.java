/*
 * Restaurant Booking System: Booking
 *
 * 예약관리 인터페이스입니다.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

/* This needs to be an interface so that PersistentBooking can be
   an interface, and so implemented by PersistentReservation and
   PersistentWalkin which already extend Reservation and Walkin.
*/

public interface Booking
{
  public Time  getArrivalTime() ;
  public int   getCovers() ;
  public Date  getDate() ;
  public Time  getEndTime() ;
  public Time  getTime() ;
  public Table getTable() ;
  public int   getTableNumber() ;
  
  public String getDetails() ;
  
  public void setArrivalTime(Time t) ;
  public void setCovers(int c) ;
  public void setDate(Date d) ;
  public void setTime(Time t) ;
  public void setTable(Table t) ;
}