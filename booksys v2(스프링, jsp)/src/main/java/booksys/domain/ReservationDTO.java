package booksys.domain;

import java.sql.Date;
import java.sql.Time;

public class ReservationDTO extends WalkInDTO {
	private int customer_id;
	private Time arrivalTime;
	
	public ReservationDTO(int oid, int covers, Date date, Time time, int table_id, int customer_id) {
		super(oid, covers, date, time, table_id);
		this.customer_id=customer_id;
		this.arrivalTime=null;
	}

	public Time getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getCustomer_id() {
		return this.customer_id;
	}
	
	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setCustomer_id(int customer) {
		this.customer_id = customer_id ;
	}
}
