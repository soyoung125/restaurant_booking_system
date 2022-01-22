/*
 * Restaurant Booking System: PersistentReservation
 *
 * reservation oid, oid 리턴 함수
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;

class PersistentReservation
  extends Reservation implements PersistentBooking
{
  private int oid ;

  public PersistentReservation(int id, int c, java.sql.Date d, java.sql.Time t,
			       Table tab, Customer cust, java.sql.Time arr)
  {
    super(c, d, t, tab, cust, arr) ;
    oid = id ;
  }

  /* public because getId defined in an interface and hence public */
  
  public int getId() {
    return oid ;
  }
}
