/*
 * Restaurant Booking System: CustomerMapper
 *
 * customer api ���� ����
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
          //�޾ƿ� �� ����
          break;
        }
        else if(jsonObj.get("name").toString().equals(arr[0]) && jsonObj.get("phoneNumber").equals(arr[1])){
          c = new PersistentCustomer(Integer.parseInt(jsonObj.get("oid").toString()), jsonObj.get("name").toString(), jsonObj.get("phoneNumber").toString(), jsonObj.get("userId").toString(), jsonObj.get("password").toString());
          //�޾ƿ� �� ����
          break;
        }
        else if(jsonObj.get("userId").toString().equals(arr[0])){
          c = new PersistentCustomer(Integer.parseInt(jsonObj.get("oid").toString()), jsonObj.get("name").toString(), jsonObj.get("phoneNumber").toString(), jsonObj.get("userId").toString(), jsonObj.get("password").toString());
          //�޾ƿ� �� ����
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
      Alert a1 = new Alert(Alert.AlertType.NONE, "�Է��� �� �� ������ �ֽ��ϴ�.", ButtonType.OK);
      a1.showAndWait();
      return false;
    }
    try {
      URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+c.getUserId()); //�� ���� ã�� api
      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
      JSONParser jsonPar=new JSONParser();
      JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
      String passwordCheck=jsonObj.get("password").toString(); //�� ��й�ȣ

      if(!BCrypt.checkpw(pwd, passwordCheck)){
        Alert a2 = new Alert(Alert.AlertType.NONE, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", ButtonType.OK);
        a2.showAndWait();
        return false;
      }
      else {
        url = new URL("http://corodiak.gotdns.org:33333/api/customer/update"); //customer ���� ������Ʈ
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        String json = "{\"userId\":\"" + c.getUserId() + "\",\"phoneNumber\":\"" + pn + "\"}"; //����� �޴�����ȣ ������ ��´�

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
  
public static void reservationCountUpdate(Customer c, boolean count) { //reservationCount ���� ����, count ���� true ���� false ����
	  
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
              reservationCount = reservationCount + 1; //���� Ƚ�� ����
          }
	      else {
              reservationCount = reservationCount - 1; //���� Ƚ�� ����
          }
	      
	      c.setReservationCount(reservationCount);
	      
		  String json="{\"userId\":\""+c.getUserId()+"\", \"reservationCount\":\""+reservationCount+"\"}"; //������Ʈ�� ���� Ƚ�� ������ ��´�
		  
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

public static void arrivalCountUpdate(Customer c) { //arrivalCount ����
	
	try {
		  URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+c.getUserId()); //���� ���̵�� �� ���� ã�� api
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
