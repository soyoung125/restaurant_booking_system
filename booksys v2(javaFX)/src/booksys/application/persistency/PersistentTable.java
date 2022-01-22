/*
 * Restaurant Booking System: PersistentTable
 *
 * Table oid 생성, oid 리턴 함수
 */

package booksys.application.persistency ;

import booksys.application.domain.Table ;

class PersistentTable extends Table
{
  private int oid ;

  PersistentTable(int id, int n, int c)
  {
    super(n, c) ;
    oid = id ;
  }

  int getId() {
    return oid ;
  }
}
