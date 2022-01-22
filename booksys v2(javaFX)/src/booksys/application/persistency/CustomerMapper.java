/*
 * Restaurant Booking System: CustomerMapper
 *
 * customer api 서버 연결
 */

package booksys.application.persistency ;

import booksys.application.domain.Customer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mindrot.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class CustomerMapper
{
  // Implementation of hidden cache
  
  private Hashtable cache ;

  private PersistentCustomer getFromCache(int oid)
  {
    Integer key = new Integer(oid) ;
    return (PersistentCustomer) cache.get(key) ;
  }

  private PersistentCustomer getFromCacheByDetails(String name, String phone)
  {
    PersistentCustomer c = null ;
    Enumeration enums = cache.elements() ;
    while (c == null && enums.hasMoreElements()) {
      PersistentCustomer tmp = (PersistentCustomer) enums.nextElement() ;
      if (name.equals(tmp.getName()) && phone.equals(tmp.getPhoneNumber())) {
	c = tmp ;
      }
    }
    return c ;
  }

  private void addToCache(PersistentCustomer c)
  {
    Integer key = new Integer(c.getId()) ;
    cache.put(key, c) ;
  }
  
  // Constructor:
  
  private CustomerMapper()
  {
    cache = new Hashtable() ;
  }

  // Singleton:
  
  private static CustomerMapper uniqueInstance ;

  public static CustomerMapper getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new CustomerMapper() ;
    }
    return uniqueInstance ;
  }

  public PersistentCustomer getCustomer(String n, String p)
  {
    PersistentCustomer c = getFromCacheByDetails(n, p) ;
    String arr[] = {n, p};
    if (c == null) {
      c = getCustomer(arr) ;
      if (c == null) {
	    c = createCustomer(n, p) ;
      }
      addToCache(c) ;
    }
    return c ;
  }
  
  PersistentCustomer getCustomerForOid(int oid)
  {
    PersistentCustomer c = getFromCache(oid) ;
    String arr[] = {oid+""};
    if (c == null) {
      c = getCustomer(arr) ;
      if (c != null) {
	addToCache(c) ;
      }
    }
    return c ;
  }

  // Add a customer to the database
  
  PersistentCustomer createCustomer(String name, String phone)
  {
    PersistentCustomer c = getFromCacheByDetails(name, phone) ;
    Random random = new Random();
    if (c == null) {
      try {
        URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/save");
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        String json="{\"oid\":1,\"userId\":\"testtest"+random.nextInt(100)+"\",\"password\":\"test\",\"name\":\""+name+"\",\"phoneNumber\":\""+phone+"\"}";

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
      c = getCustomer(name, phone) ;
    }
    return c ;
  }

  private PersistentCustomer getCustomer(String arr[])
  {
    PersistentCustomer c = null ;
    try {
      URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/list");	 //customer list api
      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
      JSONParser jsonPar=new JSONParser();
      JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
      JSONObject jsonObj = null;
      for(int i=0;i<jsonArray.size();i++) {
        jsonObj = (JSONObject) jsonArray.get(i);
        if (jsonObj.get("oid").toString().equals(arr[0])){
          c = new PersistentCustomer(Integer.parseInt(jsonObj.get("oid").toString()), jsonObj.get("name").toString(), jsonObj.get("phoneNumber").toString(), jsonObj.get("userId").toString(), jsonObj.get("password").toString());
          //받아온 고객 정보
          break;
        }
        else if(jsonObj.get("name").toString().equals(arr[0]) && jsonObj.get("phoneNumber").equals(arr[1])){
          c = new PersistentCustomer(Integer.parseInt(jsonObj.get("oid").toString()), jsonObj.get("name").toString(), jsonObj.get("phoneNumber").toString(), jsonObj.get("userId").toString(), jsonObj.get("password").toString());
          //받아온 고객 정보
          break;
        }
        else if(jsonObj.get("userId").toString().equals(arr[0])){
          c = new PersistentCustomer(Integer.parseInt(jsonObj.get("oid").toString()), jsonObj.get("name").toString(), jsonObj.get("phoneNumber").toString(), jsonObj.get("userId").toString(), jsonObj.get("password").toString());
          //받아온 고객 정보
          break;
        }
      }


      } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (org.json.simple.parser.ParseException e) {
      e.printStackTrace();
    }
    return c ;
  }

  public boolean updateCustomer(Customer c, String pwd, String pn){
    if (pwd.isEmpty() || pn.isEmpty()){
      Alert a1 = new Alert(Alert.AlertType.NONE, "입력이 안 된 사항이 있습니다.", ButtonType.OK);
      a1.showAndWait();
      return false;
    }
    try {
      URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+c.getUserId()); //고객 정보 찾는 api
      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
      JSONParser jsonPar=new JSONParser();
      JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
      String passwordCheck=jsonObj.get("password").toString(); //고객 비밀번호

      if(!BCrypt.checkpw(pwd, passwordCheck)){
        Alert a2 = new Alert(Alert.AlertType.NONE, "비밀번호가 일치하지 않습니다.", ButtonType.OK);
        a2.showAndWait();
        return false;
      }
      else {
        url = new URL("http://corodiak.gotdns.org:33333/api/customer/update"); //customer 정보 업데이트
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        String json = "{\"userId\":\"" + c.getUserId() + "\",\"phoneNumber\":\"" + pn + "\"}"; //변경된 휴대폰번호 정보를 담는다

        OutputStream os = conn.getOutputStream();
        byte[] input = json.getBytes();
        os.write(input, 0, input.length);
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        c.setPhoneNumber(pn);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return true;
  }
  
public static void reservationCountUpdate(Customer c, boolean count) { //reservationCount 증가 감소, count 값이 true 증가 false 감소
	  
	  try {
		  URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+c.getUserId());
		  HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      JSONParser jsonPar=new JSONParser();
	      JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
	      
	      int reservationCount = Integer.parseInt(jsonObj.get("reservationCount").toString());
	      
	      url = new URL("http://corodiak.gotdns.org:33333/api/customer/reservationCount/update");
	      HttpURLConnection conn1=(HttpURLConnection)url.openConnection();
	      conn1.setRequestProperty("Content-Type", "application/json");
	      conn1.setDoOutput(true);
	      conn1.setDoInput(true);
	      
	      if(count == true) {
              reservationCount = reservationCount + 1; //예약 횟수 증가
          }
	      else {
              reservationCount = reservationCount - 1; //예약 횟수 감소
          }
	      
	      c.setReservationCount(reservationCount);
	      
		  String json="{\"userId\":\""+c.getUserId()+"\", \"reservationCount\":\""+reservationCount+"\"}"; //업데이트된 예약 횟수 정보를 담는다
		  
	        OutputStream os=conn1.getOutputStream();
	        byte[] input=json.getBytes();
	        os.write(input,0,input.length);  
	        br=new BufferedReader(new InputStreamReader(conn1.getInputStream()));
	        os.flush();
	        os.close();
		    
	  }
	  catch (IOException | ParseException e) {
	      e.printStackTrace();
	    }
	  
  }

public static void arrivalCountUpdate(Customer c) { //arrivalCount 증가
	
	try {
		  URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+c.getUserId()); //유저 아이디로 고객 정보 찾는 api
		  HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      JSONParser jsonPar=new JSONParser();
	      JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
	      
	      int arrivalCount = Integer.parseInt(jsonObj.get("arrivalCount").toString()) + 1;
	      c.setArrivalCount(arrivalCount);
	      
	      url = new URL("http://corodiak.gotdns.org:33333/api/customer/update");
	      HttpURLConnection conn1=(HttpURLConnection)url.openConnection();
	      conn1.setRequestProperty("Content-Type", "application/json");
	      conn1.setDoOutput(true);
	      conn1.setDoInput(true);
	      
		  String json="{\"userId\":\""+c.getUserId()+"\", \"arrivalCount\":\""+arrivalCount+"\"}";

	        OutputStream os=conn1.getOutputStream();
	        byte[] input=json.getBytes();
	        os.write(input,0,input.length);  
	        br=new BufferedReader(new InputStreamReader(conn1.getInputStream()));
	        os.flush();
	        os.close();
		    
	  }
	  catch (IOException | ParseException e) {
	      e.printStackTrace();
	    }
	
}
}
