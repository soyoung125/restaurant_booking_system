/*
 * Restaurant Booking System: PersistentBooking
 *
 * 예약 번호 리턴 함수
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;

interface PersistentBooking extends Booking
{
  int getId() ;
}
