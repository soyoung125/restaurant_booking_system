/*
 * Restaurant Booking System: ConfirmDialog
 *
 * 확인창을 띄웁니다. 
 */

package booksys.presentation ;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

class ConfirmDialog{
  protected Label   messageLabel ;	//상황에 맞는 메세지를 받아 출력합니다
  protected Label   blankLabel ;	//빈 입력칸을 생성합니다
  protected boolean confirmed ;    //확인이면 참, 취소되면 거짓을 반환
  protected Button  confirm ;    //확인버튼
  protected Button  cancel ;    //취소버튼
  protected Stage dialog;	//창 생성

  //확인창
  ConfirmDialog(Stage owner, String message, boolean choice)
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
    messageLabel = new Label() ;
    messageLabel.setText(message);
    blankLabel = new Label("") ;

    //버튼 구현하기
    Button confirm = new Button() ;    //확인버튼
    confirm.setText("Ok");
    confirm.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			 confirmed = true ;
			 dialog.hide() ;
   		}
    });

    Button cancel = new Button() ;    //취소버튼
    cancel.setText("Cancel");
    cancel.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
		public void handle(ActionEvent e) {
    		confirmed = false ;
    		dialog.hide() ;
    	}
      }) ;
    
    //구현한 라벨과 버튼을 배치하기
    GridPane root = new GridPane();
    root.add(messageLabel, 0, 0);
    root.add(blankLabel, 1, 0);
    root.add(confirm, 0, 1);
    if (choice) {
    	root.add(cancel, 1, 1);
    }

    //다이얼로그 띄우기
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    dialog.showAndWait();
  }

  boolean isConfirmed()
  {
    return confirmed ;
  }
}