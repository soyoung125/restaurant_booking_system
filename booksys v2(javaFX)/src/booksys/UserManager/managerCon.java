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
    
    //�����Ŵ��� ����
    private userManager userM;

    /**
     * ������.
     * initialize() �޼��� ������ ȣ��ȴ�.
     */
    public managerCon() {
    }
    
    /**
     * ��Ʈ�ѷ� Ŭ������ �ʱ�ȭ�Ѵ�.
     * fxml ������ �ε�ǰ� ���� �ڵ����� ȣ��ȴ�.
     */
	@FXML
    private void initialize() {
    	// ����ó ���̺��� ���� �ʱ�ȭ�Ѵ�.
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
     * ������ �ٽ� �����ϱ� ���� ���� ���ø����̼��� ȣ���Ѵ�.
     *
      @param userM
     */
    public void setMainApp(userManager userM) {
        this.userM = userM;

        // ���̺� observable ����Ʈ �����͸� �߰��Ѵ�.
        userTable.setItems(userM.getdata());
    }
		
		// TableView�� Ŭ������ �� ó��
		Person P;
		//Select �� ���� ���� �����ϱ� ���� ����
		private Object oldValue;
	        
	    @FXML
	    void tableClick(MouseEvent event) {
	    	if (!userTable.getSelectionModel().isEmpty()) {
	    		 if (event.getPickResult().getIntersectedNode().equals(oldValue)) {
	                    userTable.getSelectionModel().clearSelection();
	                    P = null; //P �ʱ�ȭ
	                    oldValue = null;
	    		 }
	            else {
		            oldValue = event.getPickResult().getIntersectedNode();
					//������ ������ ���ϱ�
		    		P = userTable.getSelectionModel().getSelectedItem();

	           }
			}
	    }
	    
	  //���� ��ư
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
	  				alert.setHeaderText("���� ����");
	  				alert.setContentText("���ܵǾ����ϴ�. ");
	  				alert.showAndWait();

	  			} catch(Exception e){
	  				Alert alert = new Alert(AlertType.WARNING);
	  				alert.setTitle("Warning Dialog");
	  				alert.setHeaderText("���� ����");
	  				alert.setContentText("1�� �̻� �������ּ���");
	  				alert.showAndWait();
	  			}
	  			
	  		}
	  		//�������� ��ư
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
	  				alert.setHeaderText("�������� ����");
	  				alert.setContentText("���� �����Ǿ����ϴ�. ");
	  				alert.showAndWait();
	  			}catch(Exception e){
	  					Alert alert = new Alert(AlertType.WARNING);
	  					alert.setTitle("Warning Dialog");
	  					alert.setHeaderText("���� ����");
	  					alert.setContentText("1�� �̻� �������ּ���");
	  					alert.showAndWait();
	  				}
	  		}

	  		//���� ��ư
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
	  				alert.setHeaderText("���� ����");
	  				alert.setContentText("�����Ǿ����ϴ�. ");
	  				alert.showAndWait();
	  				
	  			}catch(Exception e){
	  					Alert alert = new Alert(AlertType.WARNING);
	  					alert.setTitle("Warning Dialog");
	  					alert.setHeaderText("���� ����");
	  					alert.setContentText("1�� �̻� �������ּ���");
	  					alert.showAndWait();
	  				}
	  		}
	  		
	  		//���ΰ�ħ ����
	  		@FXML private javafx.scene.control.Button ReButton;

	  		@FXML
	  		private void refresh(){
  				Stage stage = (Stage) ReButton.getScene().getWindow();
  			    stage.close();
  				userManager restage = new userManager();
	  		}
	  }