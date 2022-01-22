/*
 * Restaurant Booking System: WalkIn
 *
 * 입장예약을 관리합니다.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;

public class WalkIn extends BookingImp
{
  public WalkIn(int c, Date d, Time t, Table tab)
  {
    super(c, d, t, tab) ;
  }

  
  public String getDetails() {
    return "Walk-in (" + covers + ")" ;
  }
}