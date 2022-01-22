/*
 * Restaurant Booking System: BookingSystem
 *
 * 예약을 관리하는 기능을 합니다.
 */

package booksys.application.domain ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;

public class BookingSystem
{
  // Attributes:

  Date currentDate ;   //날짜
  Date today ;         //오늘 날짜
  
  // Associations:

  Restaurant restaurant = null ;    //레스토랑 객체
  Vector currentBookings ;          //존재하는 모든 예약
  Booking selectedBooking ;         //선택된 예약

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
    today = new Date(Calendar.getInstance().getTimeInMillis()) ;    //오늘 날짜 구하기
    restaurant = new Restaurant() ;     //레스토랑 객체생성
  }

  // Observer: this is `Subject/ConcreteSubject'

  Vector observers = new Vector() ;   //관찰자 객체생성

  //관찰자 추가
  public void addObserver(BookingObserver o)
  {
    observers.addElement(o) ;
  }

  //관찰자에게 변화 알림
  public void notifyObservers()
  {
    Enumeration enums = observers.elements() ;
    while (enums.hasMoreElements()) {
      BookingObserver bo = (BookingObserver) enums.nextElement() ;
      bo.update() ;
    }
  }

  //observer를 통해 확인 메시지 띄움
  public boolean observerMessage(String message, boolean confirm)
  {
    BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
    return bo.message(message, confirm) ;
  }
  
  // System messages:
  //선택된 날짜의 예약정보 불러옴
  public void display(Date date)
  {
    currentDate = date ;
    currentBookings = restaurant.getBookings(currentDate) ;
    selectedBooking = null ;
    notifyObservers() ;
  }

  //예약 생성
  public void makeReservation(int covers, Date date, Time time, int tno,
			      String name, String phone)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {  //시간과 인원을 체크
      Booking b
	    = restaurant.makeReservation(covers, date, time, tno, name, phone) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }

  //방문예약 생성
  public void makeWalkIn(int covers, Date date, Time time, int tno)
  {
    if (!doubleBooked(time, tno, null) && !overflow(tno, covers)) {  //시간과 인원을 체크
      Booking b = restaurant.makeWalkIn(covers, date, time, tno) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
  }

  //선택된 예약 찾기
  public void selectBooking(int tno, Time time)
  {
    selectedBooking = null ;
    Enumeration enums = currentBookings.elements() ;
    while (enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      if (b.getTableNumber() == tno) {  //테이블번호 비교
	    if (b.getTime().before(time)   //예약 시간 비교
	        && b.getEndTime().after(time)) {
	        selectedBooking = b ;
	    }
      }
    }
    notifyObservers() ;
  }

  //예약 취소
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

  //도착 정보 저장
  public void recordArrival(Time time)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getArrivalTime() != null) {   //도착 정보 이미 저장됨
	    observerMessage("Arrival already recorded", false) ;
      }
      else {
        selectedBooking.setArrivalTime(time) ;
        restaurant.updateBooking(selectedBooking) ;
        notifyObservers() ;
      }
    }
  }

  //예약 옮김
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

  //시간이 겹치는 예약 확인
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

  //인원 제한 확인
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