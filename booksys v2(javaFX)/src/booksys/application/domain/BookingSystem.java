/*
 * Restaurant Booking System: BookingSystem
 *
 * ������ �����ϴ� ����� �մϴ�.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;

public class BookingSystem
{
  // Attributes:

  Date currentDate ;   //��¥
  Date today ;         //���� ��¥
  
  // Associations:

  Restaurant restaurant = null ;    //������� ��ü
  Vector currentBookings ;          //�����ϴ� ��� ����
  Booking selectedBooking ;         //���õ� ����

  // Singleton:
  
  private static BookingSystem uniqueInstance ;

  public static BookingSystem getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingSystem() ;
    }
    return uniqueInstance ;
  }

  private BookingSystem()
  {
    today = new Date(Calendar.getInstance().getTimeInMillis()) ;    //���� ��¥ ���ϱ�
    restaurant = new Restaurant() ;     //������� ��ü����
  }

  // Observer: this is `Subject/ConcreteSubject'

  Vector observers = new Vector() ;   //������ ��ü����

  //������ �߰�
  public void addObserver(BookingObserver o)
  {
    observers.addElement(o) ;
  }

  //�����ڿ��� ��ȭ �˸�
  public void notifyObservers()
  {
    Enumeration enums = observers.elements() ;
    while (enums.hasMoreElements()) {
      BookingObserver bo = (BookingObserver) enums.nextElement() ;
      bo.update() ;
    }
  }

  //observer�� ���� Ȯ�� �޽��� ���
  public boolean observerMessage(String message, boolean confirm)
  {
    BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
    return bo.message(message, confirm) ;
  }
  
  // System messages:
  //���õ� ��¥�� �������� �ҷ���
  public void display(Date date)
  {
    currentDate = date ;
    currentBookings = restaurant.getBookings(currentDate) ;
    selectedBooking = null ;
    notifyObservers() ;
  }

  //���� ����
  public void makeReservation(int covers, Date date, Time time, int tno,
			      String name, String phone)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {  //�ð��� �ο��� üũ
      Booking b
	    = restaurant.makeReservation(covers, date, time, tno, name, phone) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }

  //�湮���� ����
  public void makeWalkIn(int covers, Date date, Time time, int tno)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {  //�ð��� �ο��� üũ
      Booking b = restaurant.makeWalkIn(covers, date, time, tno) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }

  //���õ� ���� ã��
  public void selectBooking(int tno, Time time)
  {
    selectedBooking = null ;
    Enumeration enums = currentBookings.elements() ;
    while (enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      if (b.getTableNumber() == tno) {  //���̺��ȣ ��
	    if (b.getTime().before(time)   //���� �ð� ��
	        && b.getEndTime().after(time)) {
	        selectedBooking = b ;
	    }
      }
    }
    notifyObservers() ;
  }

  //���� ���
  public void cancel()
  {
    if (selectedBooking != null) {
      if (observerMessage("Are you sure?", true)) {
        currentBookings.remove(selectedBooking) ;
        restaurant.removeBooking(selectedBooking) ;
        selectedBooking = null ;
        notifyObservers() ;
      }
    }
  }

  //���� ���� ����
  public void recordArrival(Time time)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getArrivalTime() != null) {   //���� ���� �̹� �����
	    observerMessage("Arrival already recorded", false) ;
      }
      else {
        selectedBooking.setArrivalTime(time) ;
        restaurant.updateBooking(selectedBooking) ;
        notifyObservers() ;
      }
    }
  }

  //���� �ű�
  public void transfer(Time time, int tno)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getTableNumber() != tno) {
	    if (!doubleBooked(selectedBooking.getTime(), tno, selectedBooking)
	        && !overflow(tno, selectedBooking.getCovers())) {
	      selectedBooking.setTable(restaurant.getTable(tno)) ;
          	      selectedBooking.setArrivalTime(time) ;
	      restaurant.updateBooking(selectedBooking) ;
	    }
      }
      notifyObservers() ;
    }
  }

  //�ð��� ��ġ�� ���� Ȯ��
  private boolean doubleBooked(Time startTime, int tno, Booking ignore)
  {
    boolean doubleBooked = false ;

    Time endTime = (Time) startTime.clone() ;
    endTime.setHours(endTime.getHours() + 2) ;
    
    Enumeration enums = currentBookings.elements() ;
    while (!doubleBooked && enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      if (b != ignore && b.getTableNumber() == tno
	      && startTime.before(b.getEndTime())
	      && endTime.after(b.getTime())) {
	    doubleBooked = true ;
	    observerMessage("Double booking!", false) ;
      }
    }
    return doubleBooked ;
  }

  //�ο� ���� Ȯ��
  private boolean overflow(int tno, int covers)
  {
    boolean overflow = false ;
    Table t = restaurant.getTable(tno) ;
      
    if (t.getPlaces() < covers) {
      overflow = !observerMessage("Ok to overfill table?", true) ;
    }
    return overflow ;
  }
  
  // Other Operations:

  public Date getCurrentDate()
  {
    return currentDate ;
  }
  
  public Enumeration getBookings()
  {
    return currentBookings.elements() ;
  }

  public Booking getSelectedBooking()
  {
    return selectedBooking ;
  }

  public static Vector getTableNumbers()
  {
    return Restaurant.getTableNumbers() ;
  }
}