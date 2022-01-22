/*
 * Restaurant Booking System: PersistentCustomer
 *
 * customer oid, oid 리턴 함수
 */

package booksys.application.persistency ;

import booksys.application.domain.Customer;

class PersistentCustomer extends Customer
{
  private int oid ;

  PersistentCustomer(int id, String n, String p, String cId, String pwd)
  {
    super(cId, pwd, n, p) ;
    oid = id ;
  }

  int getId() {
    return oid ;
  }
}
