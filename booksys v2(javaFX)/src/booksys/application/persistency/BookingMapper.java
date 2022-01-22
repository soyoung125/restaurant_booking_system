/*
 * Restaurant Booking System: BookingMapper
 *
 * reservation / walkin api ���� ����
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

			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/list"); //���� ����Ʈ api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			JSONParser jsonPar=new JSONParser();

			JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			JSONObject jsonObj;


			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date);  //���� ��¥

			for(int i=0;i<jsonArray.size();i++) {
				jsonObj=(JSONObject) jsonArray.get(i);
				if(jsonObj.get("date").toString().equals(dateString)) {

					java.util.Date arrivalTime; //date Ÿ�� �����ð�
					java.sql.Time arrivalTime2; //sql.Time Ÿ�� ���� �ð�
					java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("HH:mm:ss");


					if(jsonObj.get("arrivalTime") == null)
						arrivalTime2 = null;
					else {
						String atime = jsonObj.get("arrivalTime").toString();
						arrivalTime = format2.parse(atime);
						arrivalTime2 = new java.sql.Time(arrivalTime.getTime());
					}

					int oid = Integer.parseInt(jsonObj.get("oid").toString()); //���� oid
					int covers = Integer.parseInt(jsonObj.get("covers").toString()); //���� �ο�
					String bdate = jsonObj.get("date").toString(); //���� ��¥
					String btime = jsonObj.get("time").toString(); //���� �ð�
					int table = Integer.parseInt(jsonObj.get("table_id").toString()); //table ��ȣ
					int cust = Integer.parseInt(jsonObj.get("customer_id").toString()); //���� �� oid


					java.util.Date date1 = format.parse(bdate); //String Ÿ�� ��¥�� util.date Ÿ������ ��ȯ
					java.sql.Date date2 = new java.sql.Date(date1.getTime()); //util.date�� sql.date�� ��ȯ


					java.util.Date time = format2.parse(btime); //String Ÿ�� ���� �ð��� util.date Ÿ������ ��ȯ
					java.sql.Time time2 = new java.sql.Time(time.getTime());  //util.date Ÿ���� sql.Time Ÿ������ ��ȯ


					PersistentTable t = TableMapper.getInstance().getTableForOid(table) ; //table ������ �޾ƿ´�
					PersistentCustomer c =
							CustomerMapper.getInstance().getCustomerForOid(cust) ; //customer ������ �޾ƿ´�
					PersistentReservation r
							= new PersistentReservation(oid, covers, date2, time2, t, c, arrivalTime2) ; //���� ����


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
					int covers = Integer.parseInt(jsonObj2.get("covers").toString()); //�ο�
					String bdate = jsonObj2.get("date").toString(); //��¥
					String btime = jsonObj2.get("time").toString(); //�ð�
					int table = Integer.parseInt(jsonObj2.get("table_id").toString()); //table ��ȣ


					java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date1 = format1.parse(bdate); //String Ÿ���� util.Date Ÿ������ ��ȯ
					java.sql.Date date2 = new java.sql.Date(date1.getTime()); //util.Date Ÿ���� sql.Date Ÿ������ ��ȯ

					java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("HH:mm:ss");
					java.util.Date time = format2.parse(btime); //String Ÿ���� util.Date Ÿ������ ��ȯ
					java.sql.Time time2 = new java.sql.Time(time.getTime()); //util.Date Ÿ���� sql.Time���� ��ȯ



					PersistentTable t = TableMapper.getInstance().getTableForOid(table) ; //table ������ �޾ƿ´�
					PersistentWalkIn w
							= new PersistentWalkIn(oid, covers, date2, time2, t) ; //walkin ����


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

			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/save"); //���� ����
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);




			int tid = ((PersistentTable) table).getId(); //table oid
			int cus = ((PersistentCustomer) customer).getId(); //customer oid



			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //���� ��¥
			String timeString = time.toString(); //���� �ð�


			String json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\",\"customer_id\":\""+cus+"\"}";

			boolean count = true; //true = ���� Ƚ�� ����
			CustomerMapper.reservationCountUpdate(customer, count); //reservationCount ����

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

			URL url=new URL("http://corodiak.gotdns.org:33333/api/walkin/save"); //walkin ���� api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			int tid = ((PersistentTable) table).getId();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //��¥

			String timeString = time.toString();  //�ð�


			String json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\"}"; //walkin ����

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
		PersistentBooking pb = (PersistentBooking) b ; //persistentbooking Ÿ������ ��ȯ
		boolean isReservation = b instanceof Reservation ; //����, �湮 ���� �Ǵ�


		try {
			int oid = pb.getId();

			URL url=new URL("http://corodiak.gotdns.org:33333/api/"+(isReservation ? "reservation" : "walkin")+"/update")   ; //����/�湮���� ���� ������Ʈ api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);



			int covers = pb.getCovers(); //�ο�
			Date date = pb.getDate() ; //���� ��¥
			Time time = pb.getTime(); //���� �ð�

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date); //String���� ��ȯ

			String timeString = time.toString(); //String���� ��ȯ


			int tid = ((PersistentTable) pb.getTable()).getId(); //table ��ȣ




			String json;

			if(isReservation) {
				PersistentReservation pr = (PersistentReservation) pb ; //PersistentReservation Ÿ������ ��ȯ (oid�� �����)
				Customer c = pr.getCustomer(); //���� ��
				int cus = ((PersistentCustomer) c).getId(); //���� �� oid

				String atime = pr.getArrivalTime().toString() ; //���� �ð�

				if(atime != null) {
					CustomerMapper.arrivalCountUpdate(c); //arrivalCount ����
				}

				json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\",\"customer_id\":\""+cus+"\",\"arrivalTime\":\""+atime+"\"}";
				//���� ����
			}
			else
				json="{\"oid\":\""+oid+"\",\"covers\":\""+covers+"\",\"date\":\""+dateString+"\",\"time\":\""+timeString+"\",\"table_id\":\""+tid+"\"}";//�湮���� ����

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
		String table = b instanceof Reservation ? "reservation" : "walkin" ; //����, �湮���� �Ǵ�

		try {

			if(table == "reservation") { //reservationCount ����
				PersistentBooking pb = (PersistentBooking) b ;
				PersistentReservation pr = (PersistentReservation) pb ; //PersistentReservation Ÿ������ �����
				Customer c = pr.getCustomer(); //���� ��
				boolean count = false; //false = ���� Ƚ�� ����
				CustomerMapper.reservationCountUpdate(c, count); //���� Ƚ�� ����
			}


			URL url=new URL("http://corodiak.gotdns.org:33333/api/"+table+"/delete/"+((PersistentBooking) b).getId())   ; //����, �湮 ���� ���� api
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			int responseCode=conn.getResponseCode();


		}
		catch (IOException e) {
			e.printStackTrace() ;
		}

	}

	public void deleteReservation_userOid (int userOid) {

		try {
			URL url=new URL("http://corodiak.gotdns.org:33333/api/reservation/list"); //���� ����Ʈ api
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
