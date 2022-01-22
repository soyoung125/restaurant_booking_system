/*
 * Restaurant Booking System: BookingMapper
 *
 * reservation / walkin api 서버 연결
 */
package booksys.application.persistency ;

import booksys.application.domain.Booking ;
import booksys.application.domain.Reservation ;
import booksys.application.domain.Customer ;
import booksys.application.domain.Table ;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.util.Vector ;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BookingMapper
{
	// Singleton:

	private static BookingMapper uniqueInstance ;

	public static BookingMapper getInstance()
	{
		if (uniqueInstance == null) {
			uniqueInstance = new BookingMapper() ;
		}
		return uniqueInstance ;
	}

	public Vector getBookings(Date date)
	{
		Vector v = new Vector() ;
		try
		{

			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/list"); //예약 리스트 api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONParser jsonPar=new JSONParser();

			JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			JSONObject jsonObj;


			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date);  //예약 날짜

			for(int i=0;i<jsonArray.size();i++) {
				jsonObj=(JSONObject) jsonArray.get(i);
				if(jsonObj.get("date").toString().equals(dateString)) {

					java.util.Date arrivalTime; //date 타입 도착시간
					java.sql.Time arrivalTime2; //sql.Time 타입 도착 시간
					java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("HH:mm:ss");


					if(jsonObj.get("arrivalTime") == null)
						arrivalTime2 = null;
					else {
						String atime = jsonObj.get("arrivalTime").toString();
						arrivalTime = format2.parse(atime);
						arrivalTime2 = new java.sql.Time(arrivalTime.getTime());
					}

					int oid = Integer.parseInt(jsonObj.get("oid").toString()); //예약 oid
					int covers = Integer.parseInt(jsonObj.get("covers").toString()); //예약 인원
					String bdate = jsonObj.get("date").toString(); //예약 날짜
					String btime = jsonObj.get("time").toString(); //예약 시간
					int table = Integer.parseInt(jsonObj.get("table_id").toString()); //table 번호
					int cust = Integer.parseInt(jsonObj.get("customer_id").toString()); //예약 고객 oid


					java.util.Date date1 = format.parse(bdate); //String 타입 날짜를 util.date 타입으로 변환
					java.sql.Date date2 = new java.sql.Date(date1.getTime()); //util.date를 sql.date로 변환


					java.util.Date time = format2.parse(btime); //String 타입 예약 시간을 util.date 타입으로 변환
					java.sql.Time time2 = new java.sql.Time(time.getTime());  //util.date 타입을 sql.Time 타입으로 변환


					PersistentTable t = TableMapper.getInstance().getTableForOid(table) ; //table 정보를 받아온다
					PersistentCustomer c =
							CustomerMapper.getInstance().getCustomerForOid(cust) ; //customer 정보를 받아온다
					PersistentReservation r
							= new PersistentReservation(oid, covers, date2, time2, t, c, arrivalTime2) ; //예약 생성


					v.add(r) ; //


				}

			}


			URL url1=new URL("http://corodiak.gotdns.org:33333/api/walkin/list"); //walkin list api
			HttpURLConnection conn1=(HttpURLConnection)url1.openConnection();
			BufferedReader br1=new BufferedReader(new InputStreamReader(conn1.getInputStream()));
			JSONParser jsonPar1=new JSONParser();

			JSONArray jsonArray1=(JSONArray) jsonPar1.parse(br1);
			JSONObject jsonObj2;




			String dateString2 = format.format(date);

			for(int i=0;i<jsonArray1.size();i++) {
				jsonObj2=(JSONObject) jsonArray1.get(i);
				if(jsonObj2.get("date").toString().equals(dateString2)) {


					int oid = Integer.parseInt(jsonObj2.get("oid").toString()); //walkin oid
					int covers = Integer.parseInt(jsonObj2.get("covers").toString()); //인원
					String bdate = jsonObj2.get("date").toString(); //날짜
					String btime = jsonObj2.get("time").toString(); //시간
					int table = Integer.parseInt(jsonObj2.get("table_id").toString()); //table 번호


					java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date1 = format1.parse(bdate); //String 타입을 util.Date 타입으로 변환
					java.sql.Date date2 = new java.sql.Date(date1.getTime()); //util.Date 타입을 sql.Date 타입으로 변환

					java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("HH:mm:ss");
					java.util.Date time = format2.parse(btime); //String 타입을 util.Date 타입으로 변환
					java.sql.Time time2 = new java.sql.Time(time.getTime()); //util.Date 타입을 sql.Time으로 변환



					PersistentTable t = TableMapper.getInstance().getTableForOid(table) ; //table 정보를 받아온다
					PersistentWalkIn w
							= new PersistentWalkIn(oid, covers, date2, time2, t) ; //walkin 생성


					v.add(w);



				}

			}
		}
		catch ( IOException | ParseException | java.text.ParseException e) {
			e.printStackTrace() ;
		}
		return v ;
	}




	public PersistentReservation createReservation(int covers,
												   Date date,
												   Time time,
												   Table table,
												   Customer customer,
												   Time arrivalTime)

	{
		int oid = OidMapper.getInstance().getId();

		try {

			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/save"); //예약 저장
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);




			int tid = ((PersistentTable) table).getId(); //table oid
			int cus = ((PersistentCustomer) customer).getId(); //customer oid



			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //예약 날짜
			String timeString = time.toString(); //예약 시간


			String json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\",\"customer_id\":\""+cus+"\"}";

			boolean count = true; //true = 예약 횟수 증가
			CustomerMapper.reservationCountUpdate(customer, count); //reservationCount 증가

			OutputStream os=conn.getOutputStream();
			byte[] input=json.getBytes();
			os.write(input,0,input.length);
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			os.flush();
			os.close();
		}

		catch (IOException e) {
			e.printStackTrace() ;
		}


		return new PersistentReservation(oid,
				covers,
				date,
				time,
				table,
				customer,
				arrivalTime) ;

	}


	public PersistentWalkIn createWalkIn(int covers,
										 Date date,
										 Time time,
										 Table table)
	{

		int oid = OidMapper.getInstance().getId() ;
		try {

			URL url=new URL("http://corodiak.gotdns.org:33333/api/walkin/save"); //walkin 저장 api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			int tid = ((PersistentTable) table).getId();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //날짜

			String timeString = time.toString();  //시간


			String json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\"}"; //walkin 정보

			OutputStream os=conn.getOutputStream();
			byte[] input=json.getBytes();
			os.write(input,0,input.length);
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			os.flush();
			os.close();
		}

		catch (IOException e) {
			e.printStackTrace() ;
		}
		return new PersistentWalkIn(oid, covers, date, time, table) ;
	}



	public void updateBooking(Booking b)
	{
		PersistentBooking pb = (PersistentBooking) b ; //persistentbooking 타입으로 변환
		boolean isReservation = b instanceof Reservation ; //예약, 방문 예약 판단


		try {
			int oid = pb.getId();

			URL url=new URL("http://corodiak.gotdns.org:33333/api/"+(isReservation ? "reservation" : "walkin")+"/update")   ; //예약/방문예약 정보 업데이트 api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);



			int covers = pb.getCovers(); //인원
			Date date = pb.getDate() ; //예약 날짜
			Time time = pb.getTime(); //예약 시간

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //String으로 변환

			String timeString = time.toString(); //String으로 변환


			int tid = ((PersistentTable) pb.getTable()).getId(); //table 번호




			String json;

			if(isReservation) {
				PersistentReservation pr = (PersistentReservation) pb ; //PersistentReservation 타입으로 변환 (oid가 생긴다)
				Customer c = pr.getCustomer(); //예약 고객
				int cus = ((PersistentCustomer) c).getId(); //예약 고객 oid

				String atime = pr.getArrivalTime().toString() ; //도착 시간

				if(atime != null) {
					CustomerMapper.arrivalCountUpdate(c); //arrivalCount 증가
				}

				json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\",\"customer_id\":\""+cus+"\",\"arrivalTime\":\""+atime+"\"}";
				//예약 정보
			}
			else
				json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\"}";//방문예약 정보

			OutputStream os=conn.getOutputStream();
			byte[] input=json.getBytes();
			os.write(input,0,input.length);
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			os.flush();
			os.close();

		}
		catch (IOException e) {
			e.printStackTrace() ;
		}

	}

	public void deleteBooking(Booking b)
	{
		String table = b instanceof Reservation ? "reservation" : "walkin" ; //예약, 방문예약 판단

		try {

			if(table == "reservation") { //reservationCount 감소
				PersistentBooking pb = (PersistentBooking) b ;
				PersistentReservation pr = (PersistentReservation) pb ; //PersistentReservation 타입으로 만든다
				Customer c = pr.getCustomer(); //예약 고객
				boolean count = false; //false = 예약 횟수 감소
				CustomerMapper.reservationCountUpdate(c, count); //예약 횟수 감소
			}


			URL url=new URL("http://corodiak.gotdns.org:33333/api/"+table+"/delete/"+((PersistentBooking) b).getId())   ; //예약, 방문 예약 삭제 api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			int responseCode=conn.getResponseCode();


		}
		catch (IOException e) {
			e.printStackTrace() ;
		}

	}

	public void deleteReservation_userOid (int userOid) {

		try {
			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/list"); //예약 리스트 api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONParser jsonPar=new JSONParser();

			JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			JSONObject jsonObj;


			for(int i=0;i<jsonArray.size();i++) {
				jsonObj=(JSONObject) jsonArray.get(i);
				if(Integer.parseInt(jsonObj.get("customer_id").toString()) == userOid) {


					int reservationOid = Integer.parseInt(jsonObj.get("oid").toString());
					url=new URL("http://corodiak.gotdns.org:33333/api/reservation/delete/"+reservationOid)   ;
					conn=(HttpURLConnection)url.openConnection();
					br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

				}


			}

		}
		catch ( IOException | ParseException e) {
			e.printStackTrace() ;
		}
	}



}
