/*
 * Restaurant Booking System: TableMapper
 *
 * table api 서버 연결
 */

package booksys.application.persistency ;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration ;
import java.util.Hashtable ;
import java.util.Vector ;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class TableMapper
{
  // Implementation of hidden cache
  
  private Hashtable cache ;

  private PersistentTable getFromCache(int oid) //
  {
    Integer key = new Integer(oid) ; 
    return (PersistentTable) cache.get(key) ; 
  } 

  private PersistentTable getFromCacheByNumber(int tno)
  {
    PersistentTable t = null ; 
    Enumeration enums = cache.elements() ;
    

    while (t == null & enums.hasMoreElements()) {
    	
      PersistentTable tmp = (PersistentTable) enums.nextElement() ;
    
      if (tmp.getNumber() == tno) {
	t = tmp ; 
      }
    }
    return t ; //넘어온 tno와 일치하는 oid, tno, place 정보를 넘긴다
  }
  
  private void addToCache(PersistentTable t)
  {
    Integer key = new Integer(t.getId()) ;
    cache.put(key, t) ;
  } 
  
  // Constructor:
  
  TableMapper()
  {
    cache = new Hashtable() ;
  } 

  // Singleton:
  
  private static TableMapper uniqueInstance ; 

  public static TableMapper getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new TableMapper() ;
    }
    return uniqueInstance ; 
  }

 
  public PersistentTable getTable(int tno) {
	  
	  PersistentTable t = getFromCacheByNumber(tno);
		  
		  if(t == null) {
			  try{
				  URL url=new URL("http://corodiak.gotdns.org:33333/api/table/list"); //table list api
			      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			      int responseCode=conn.getResponseCode();
			      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      JSONParser jsonPar=new JSONParser();
			     		      
			      JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			      JSONObject jsonObj = null;
			      for(int i=0;i<jsonArray.size();i++) {
			    	  jsonObj=(JSONObject) jsonArray.get(i);
			    	  if(jsonObj.get("number").toString().equals(Integer.toString(tno))) {

						  int places = Integer.parseInt(jsonObj.get("places").toString());  //인원
						  int oid = Integer.parseInt(jsonObj.get("oid").toString()); //table oid
						  t = new PersistentTable(oid, tno,places); //table 정보 저장

						  break;
					  }

			}


			      }
	  
			  catch (IOException | ParseException e) {
			      e.printStackTrace() ;
			    }
		  
			  if(t != null) {
				  addToCache(t);
			  }
		  }
		  return t;
	  }
	  
  
 
  PersistentTable getTableForOid(int oid) {
	  PersistentTable t = getFromCacheByNumber(oid);
	  
		  
		  if(t == null) {
			  try{
				  URL url=new URL("http://corodiak.gotdns.org:33333/api/table/list"); //table list api
			      HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			      int responseCode=conn.getResponseCode();
			      BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      JSONParser jsonPar=new JSONParser();
			      
			     
			      
			      JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
			      JSONObject jsonObj = null;
			      for(int i=0;i<jsonArray.size();i++) {
			    	  jsonObj=(JSONObject) jsonArray.get(i);
			    	  if(jsonObj.get("number").toString().equals(Integer.toString(oid))) {
						  int number = Integer.parseInt(jsonObj.get("number").toString()); //table number
						  int places = Integer.parseInt(jsonObj.get("places").toString()); //인원
						  
						  t = new PersistentTable(oid, number,places); //table 정보 저장
						 
						  break;
					  }

			}
				  			
				  
				  
			  }
			  catch (IOException | ParseException e) {
			      e.printStackTrace() ;
			    }
				  
			  
			  
			  if(t != null) {
				  addToCache(t);
			  }
		  }
		  return t;
	  
  }
 
  
  
  public Vector getTableNumbers() 
  {
	  Vector v = new Vector();
	  
	  
	  
	  try {
		  
		  URL url=new URL("http://corodiak.gotdns.org:33333/api/table/list");	//table list api
		  HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		  BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		  JSONParser jsonPar=new JSONParser();
		  		
		  JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
		  JSONObject jsonObj;
		  

		  
		  for(int i=0;i<jsonArray.size();i++) {
			  jsonObj=(JSONObject) jsonArray.get(i);
			  
			  jsonObj.get("number");
			  v.addElement(jsonObj.get("number").toString());
		  }
		  
	    }
	  catch (IOException | ParseException e) {
	      e.printStackTrace() ;
	    }
	    return v ;
	  }

  

 
  
}
