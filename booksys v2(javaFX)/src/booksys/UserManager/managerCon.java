package booksys.UserManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class managerCon {
	@FXML
    private TableColumn<Person, String> arrivalCount;

    @FXML
    private TableColumn<Person, String> phoneNumber;

    @FXML
    private TableColumn<Person, String> grade;

    @FXML
    private TableColumn<Person, String> name;

    @FXML
    private TableView<Person> userTable;
    
    @FXML
    private TableColumn<Person, String> user;
    
    @FXML
    private TableColumn<Person, String> block;
    
    @FXML
    private TableColumn<Person, String> userId;

    @FXML
    private TableColumn<Person, String> reservationCount;
    
    //유저매니저 참조
    private userManager userM;

    /**
     * 생성자.
     * initialize() 메서드 이전에 호출된다.
     */
    public managerCon() {
    }
    
    /**
     * 컨트롤러 클래스를 초기화한다.
     * fxml 파일이 로드되고 나서 자동으로 호출된다.
     */
	@FXML
    private void initialize() {
    	// 연락처 테이블의 열을 초기화한다.
    	user.setCellValueFactory(cellData -> cellData.getValue().userProperty());
    	userId.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());
    	name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	phoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    	reservationCount.setCellValueFactory(cellData -> cellData.getValue().reservationCountProperty());
    	arrivalCount.setCellValueFactory(cellData -> cellData.getValue().arrivalCountProperty());
    	grade.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
    	block.setCellValueFactory(cellData -> cellData.getValue().blockProperty());
    }
	/**
     * 참조를 다시 유지하기 위해 메인 애플리케이션이 호출한다.
     *
      @param userM
     */
    public void setMainApp(userManager userM) {
        this.userM = userM;

        // 테이블에 observable 리스트 데이터를 추가한다.
        userTable.setItems(userM.getdata());
    }
		
		// TableView를 클릭했을 때 처리
		Person P;
		//Select 된 이전 값을 저장하기 위한 변수
		private Object oldValue;
	        
	    @FXML
	    void tableClick(MouseEvent event) {
	    	if (!userTable.getSelectionModel().isEmpty()) {
	    		 if (event.getPickResult().getIntersectedNode().equals(oldValue)) {
	                    userTable.getSelectionModel().clearSelection();
	                    P = null; //P 초기화
	                    oldValue = null;
	    		 }
	            else {
		            oldValue = event.getPickResult().getIntersectedNode();
					//선택한 데이터 구하기
		    		P = userTable.getSelectionModel().getSelectedItem();

	           }
			}
	    }
	    
	  //차단 버튼
	  		public void Block(ActionEvent actionEvent) {
	  			try {
	  				String value= P.getId();
	  					try {
	  						URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+value);
	  						HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	  						BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  						JSONParser jsonPar=new JSONParser();
	  						JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
	  						int grade=Integer.parseInt(jsonObj.get("grade").toString());
	  						
	  						if(grade<6) {	
	  							url=new URL("http://corodiak.gotdns.org:33333/api/customer/update");
	  							conn=(HttpURLConnection)url.openConnection();
	  							conn.setRequestProperty("Content-Type", "application/json");
	  							conn.setDoOutput(true);
	  							String json="{\"userId\":\""+value+"\",\"grade\":"+(grade+5)+"}";
	  							OutputStream os=conn.getOutputStream();
	  							byte[] input=json.getBytes();
	  							os.write(input,0,input.length);
	  							br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  						}
	  					} catch(Exception e) {}

	  				Alert alert = new Alert(AlertType.WARNING);
	  				alert.setTitle("Warning Dialog");
	  				alert.setHeaderText("차단 성공");
	  				alert.setContentText("차단되었습니다. ");
	  				alert.showAndWait();

	  			} catch(Exception e){
	  				Alert alert = new Alert(AlertType.WARNING);
	  				alert.setTitle("Warning Dialog");
	  				alert.setHeaderText("접근 에러");
	  				alert.setContentText("1개 이상 선택해주세요");
	  				alert.showAndWait();
	  			}
	  			
	  		}
	  		//차단해제 버튼
	  		public void UnBlock(ActionEvent actionEvent) {
	  			try {
	  				String value= P.getId();
	  				try {
	  					URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+value);
	  					HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	  					BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  					JSONParser jsonPar=new JSONParser();
	  					JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
	  					int grade=Integer.parseInt(jsonObj.get("grade").toString());
	  					if(grade>5) {
	  						url=new URL("http://corodiak.gotdns.org:33333/api/customer/update");
	  						conn=(HttpURLConnection)url.openConnection();
	  						conn.setRequestProperty("Content-Type", "application/json");
	  						conn.setDoOutput(true);
	  						String json="{\"userId\":\""+value+"\",\"grade\":"+(grade-5)+"}";
	  						OutputStream os=conn.getOutputStream();
	  						byte[] input=json.getBytes();
	  						os.write(input,0,input.length);
	  						br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  					}
	  				}catch(Exception e) {}
	  				
	  				Alert alert = new Alert(AlertType.WARNING);
	  				alert.setTitle("Warning Dialog");
	  				alert.setHeaderText("차단해제 성공");
	  				alert.setContentText("차단 해제되었습니다. ");
	  				alert.showAndWait();
	  			}catch(Exception e){
	  					Alert alert = new Alert(AlertType.WARNING);
	  					alert.setTitle("Warning Dialog");
	  					alert.setHeaderText("접근 에러");
	  					alert.setContentText("1개 이상 선택해주세요");
	  					alert.showAndWait();
	  				}
	  		}

	  		//삭제 버튼
	  		public void Delete(ActionEvent actionEvent) {
	  			try {
	  				String value= P.getId();
	  				try {
	  					URL url=new URL("http://corodiak.gotdns.org:33333/api/customer/find/"+value);
	  					HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	  					BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  					JSONParser jsonPar=new JSONParser();
	  					JSONObject jsonObj=(JSONObject) jsonPar.parse(br);
	  					int grade=Integer.parseInt(jsonObj.get("grade").toString());
	  					if(grade!=0) {
	  						url=new URL("http://corodiak.gotdns.org:33333/api/customer/delete/"+value);
	  						conn=(HttpURLConnection)url.openConnection();
	  						br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  					}
	  				} catch(Exception e) {}
	  				
	  				Alert alert = new Alert(AlertType.WARNING);
	  				alert.setTitle("Warning Dialog");
	  				alert.setHeaderText("삭제 성공");
	  				alert.setContentText("삭제되었습니다. ");
	  				alert.showAndWait();
	  				
	  			}catch(Exception e){
	  					Alert alert = new Alert(AlertType.WARNING);
	  					alert.setTitle("Warning Dialog");
	  					alert.setHeaderText("접근 에러");
	  					alert.setContentText("1개 이상 선택해주세요");
	  					alert.showAndWait();
	  				}
	  		}
	  		
	  		//새로고침 구현
	  		@FXML private javafx.scene.control.Button ReButton;

	  		@FXML
	  		private void refresh(){
  				Stage stage = (Stage) ReButton.getScene().getWindow();
  			    stage.close();
  				userManager restage = new userManager();
	  		}
	  }