/*
 * Restaurant Booking System: BookingImp
 *
 * 예약 정보를 저장합니다.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

public abstract class BookingImp implements Booking
{
  protected int   covers ;   //인원
  protected Date  date ;     //날짜
  protected Time  time ;     //시간
  protected Table table ;    //테이블 번호
  
  public BookingImp(int c, Date d, Time t, Table tab) {
    covers    = c ;
    date      = d ;
    time 	  = t ;
    table     = tab ;
  }

  public Time getArrivalTime() {
    return null ;
  }

  public int getCovers() {
    return covers;
  }
  
  public Date getDate() {
    return date;
  }

  // End time defaults to 2 hours after time of booking
  //예약시간에서 2시간을 더해 구한 종료시간 반환
  public Time getEndTime() {
    Time endTime = (Time) time.clone() ;
    endTime.setHours(endTime.getHours() + 2) ;
    return endTime ;
  }
  
  public Time getTime() {
    return time;
  }
  
  public Table getTable() {
    return table;
  }

  public int getTableNumber() {
    return table.getNumber() ;
  }
  
  public void setArrivalTime(Time t) { }

  public void setCovers(int c) {
    covers = c ;
  }

  public void setDate(Date d) {
    date = d ;
  }

  public void setTime(Time t) {
    time = t ;
  }
  
  public void setTable(Table t) {
    table = t ;
  }
}