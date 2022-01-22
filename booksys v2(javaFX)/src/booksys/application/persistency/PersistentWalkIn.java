/*
 * Restaurant Booking System: PersistentWalkIn
 *
 * Walkin oid 생성, oid 리턴 함수
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;

class PersistentWalkIn extends WalkIn implements PersistentBooking
{
  private int oid ;

  public PersistentWalkIn(int id, int c, java.sql.Date d,
			  java.sql.Time t, Table tab)
  {
    super(c, d, t, tab) ;
    oid = id ;
  }

  /* public because getId defined in an interface and hence public */

  public int getId() {
    return oid ;
  }
}
