package booksys.domain;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class WalkInDTO{
	private int oid;
	private int covers;
	private Date date;
	private Time time;
	private int table_id;
	
	public WalkInDTO(int oid, int covers, Date date, Time time, int table_id) {
		this.oid=oid;
		this.covers=covers;
		this.date=date;
		this.time=time;
		this.table_id=table_id;
	}
	
	public int getCovers() {
		return this.covers;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Time getEndTime() {
		Time endTime=(Time) time.clone();
		endTime.setHours(endTime.getHours()+2);
		return endTime;
	}
	
	public Time getTime() {
		return this.time;
	}

	public int getTable_id() {
		return this.table_id ;
	}
	
	public void setCovers(int covers) {
		this.covers = covers ;
	}

	public void setDate(Date date) {
		this.date = date ;
	}

	public void setTime(Time time) {
		this.time = time ;
	}
		  
	public void setTable_id(int table_id) {
		this.table_id=table_id;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	@Override
	public String toString() {
		return "WalkInDTO [oid=" + oid + ", covers=" + covers + ", date=" + date + ", time=" + time + ", table_id="
				+ table_id + "]";
	}
}
