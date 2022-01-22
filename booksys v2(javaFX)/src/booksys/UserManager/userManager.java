package booksys.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class userManager {
	
	ObservableList<Person> data = FXCollections.observableArrayList();
	
	private AnchorPane root;
   //생성자
	public userManager() {
    	//유저 테이블 받아오기
		try {
  		URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/list");
  		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
  		BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
  		JSONParser jsonPar=new JSONParser();
  		JSONArray jsonArray=(JSONArray) jsonPar.parse(br);
  		JSONObject jsonObj;

  		for(int i=0;i<jsonArray.size();i++){
  			jsonObj=(JSONObject) jsonArray.get(i);
  			String blocked=jsonObj.get("grade").toString();
  			if(Integer.parseInt(blocked)>5) {
  				blocked="차단됨";
  			}
  			else {
  				blocked="";
  			}
  			  //리스트 생성
  			data.add(new Person(jsonObj.get("oid").toString(),jsonObj.get("userId").toString(),jsonObj.get("name").toString(),
	    				jsonObj.get("phoneNumber").toString(),jsonObj.get("reservationCount").toString(),
	    				jsonObj.get("arrivalCount").toString(),jsonObj.get("grade").toString(),blocked));
  		}
		}
		catch (Exception e) {
		}
  		
  		
  		try {
  			Stage primaryStage = new Stage();
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(userManager.class.getResource("manager.fxml"));
	        root = (AnchorPane) loader.load();
	        
	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Manage");
	        primaryStage.setScene(scene);
            primaryStage.show();
	        managerCon controller = loader.getController();
	        controller.setMainApp(this);
  		} catch (IOException e) {
  	        e.printStackTrace();
  	    }
    }

    
    /**
	 * 연락처에 대한 observable 리스트를 반환한다.
	 * @return
	 */
	public ObservableList<Person> getdata() {
		return data;
	}


}