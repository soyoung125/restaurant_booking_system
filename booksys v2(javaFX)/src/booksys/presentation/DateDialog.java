/*
 * Restaurant Booking System: DateDialog
 *
 * ��¥ �̵� ����� �ϴ� â�� ���ϴ�. 
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
  TextField tf ;	//�Է�ĭ�� �����մϴ�
  protected boolean confirmed;    //Ȯ���̸� ��, ��ҵǸ� ������ ��ȯ
  protected Stage dialog;	//â ����
 
  //��¥ �̵� â
  DateDialog(Stage owner, String title)
  {
    dialog = new Stage(StageStyle.UTILITY);
   
	//â�ݱ� �κ�
	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
	@Override
	public void handle(WindowEvent e) {
		  confirmed = false ;
		  dialog.hide() ;
	}
      }) ;
	
	//�� ��� �����ϱ�
    Label promptLabel = new Label() ;
    promptLabel.setText("Enter date:");
    
    tf = new TextField("YYYY-MM-DD") ;
    
    //��ư �����ϱ�
    Button confirm = new Button() ;    //Ȯ�ι�ư
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

    Button cancel = new Button() ;    //��ҹ�ư
    cancel.setText("Cancel");
    cancel.setMinWidth(165);
    cancel.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
		public void handle(ActionEvent e) {
    		confirmed = false ;
    		dialog.hide() ;
    	}
      }) ;

    //������ �󺧰� ��ư�� ��ġ�ϱ� 
    GridPane root = new GridPane();
    root.add(promptLabel, 0, 0);
    root.add(tf, 1, 0);
    root.add(confirm, 0, 1);
    root.add(cancel, 1, 1);
    
    //���̾�α� ����
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