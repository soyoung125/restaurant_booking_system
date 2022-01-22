/*
 * Restaurant Booking System: DateDialog
 *
 * 날짜 이동 기능을 하는 창을 띄웁니다. 
 */

package booksys.presentation ;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.sql.Date;

class DateDialog{
  TextField tf ;	//입력칸을 생성합니다
  protected boolean confirmed;    //확인이면 참, 취소되면 거짓을 반환
  protected Stage dialog;	//창 생성
 
  //날짜 이동 창
  DateDialog(Stage owner, String title)
  {
    dialog = new Stage(StageStyle.UTILITY);
   
	//창닫기 부분
	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
	@Override
	public void handle(WindowEvent e) {
		  confirmed = false ;
		  dialog.hide() ;
	}
      }) ;
	
	//라벨 요소 구현하기
    Label promptLabel = new Label() ;
    promptLabel.setText("Enter date:");
    
    tf = new TextField("YYYY-MM-DD") ;
    
    //버튼 구현하기
    Button confirm = new Button() ;    //확인버튼
    confirm.setText("Ok");
    confirm.setMinWidth(70);
    confirm.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			// TODO Auto-generated method stub
			 confirmed = true ;
			 dialog.hide() ;
   		}
    });

    Button cancel = new Button() ;    //취소버튼
    cancel.setText("Cancel");
    cancel.setMinWidth(165);
    cancel.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
		public void handle(ActionEvent e) {
    		confirmed = false ;
    		dialog.hide() ;
    	}
      }) ;

    //구현한 라벨과 버튼을 배치하기 
    GridPane root = new GridPane();
    root.add(promptLabel, 0, 0);
    root.add(tf, 1, 0);
    root.add(confirm, 0, 1);
    root.add(cancel, 1, 1);
    
    //다이얼로그 띄우기
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    dialog.setTitle("Enter a date");
    dialog.showAndWait();
  }
  boolean isConfirmed()
  {
    return confirmed ;
  }

  Date getDate()
  {
    return Date.valueOf(tf.getText()) ;
  }
}