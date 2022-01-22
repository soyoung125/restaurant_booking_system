/*
 * Restaurant Booking System: OidMapper
 *
 * oid api 서버 연결
 */

package booksys.application.persistency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class OidMapper {
	  
	  // Singleton:
	  
	  private static OidMapper uniqueInstance ;

	  public static OidMapper getInstance()
	  {
	    if (uniqueInstance == null) {
	      uniqueInstance = new OidMapper() ;
	    }
	    return uniqueInstance ;
	  }


	  // Get a fresh object ID

	  public int getId()
	  {
		  int id = 0; //예약 oid 처음 생성시 초기값 지정
		  
		  try {
			  
			  URL url=new URL("http://corodiak.gotdns.org:33333/api/oid/find");	//예약 oid를 위한 oid 찾는 api
			  HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			  BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			  JSONParser jsonPar=new JSONParser();
			  
			  

			  JSONObject jsonObj=(JSONObject) jsonPar.parse(br);


			  id = Integer.parseInt(jsonObj.get("last_id").toString());
			  
			   
			    
			    URL url1=new URL("http://corodiak.gotdns.org:33333/api/oid/update")   ; //oid 업데이트 api
				  HttpURLConnection conn1=(HttpURLConnection)url1.openConnection();
				    
				    conn1.setRequestProperty("Content-Type", "application/json");
				    conn1.setDoOutput(true);
				    conn1.setDoInput(true);
				    
				    id++;
				    
				    String json="{\"last_id\":"+id+"}"; //마지막 예약 oid 저장

				    OutputStream os=conn1.getOutputStream();
				    byte[] input=json.getBytes();
				    os.write(input,0,input.length);  
				    BufferedReader br1=new BufferedReader(new InputStreamReader(conn1.getInputStream()));
				    os.flush();
				    os.close();
			    
			    
		  }
		  
		  catch ( IOException | ParseException e) {
			  
		  }
		  
		  return id;
	  }

}
