/*
 * Restaurant Booking System: Table
 *
 * 테이블 정보를 저장합니다.
 */

package booksys.application.domain ;

public class Table
{
  private int number ;       //테이블 번호
  private int places ;       //테이블 인원
  
  public Table(int n, int p)
  {
    number = n ;
    places = p ;
  }

  public int getNumber()
  {
    return number ;
  }

  public int getPlaces()
  {
    return places ;
  }
}