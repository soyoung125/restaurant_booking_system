/*
 * Restaurant Booking System: Table
 *
 * ���̺� ������ �����մϴ�.
 */

package booksys.application.domain ;

public class Table
{
  private int number ;       //���̺� ��ȣ
  private int places ;       //���̺� �ο�
  
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