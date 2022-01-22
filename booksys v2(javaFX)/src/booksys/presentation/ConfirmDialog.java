/*
 * Restaurant Booking System: ConfirmDialog
 *
 * Ȯ��â�� ���ϴ�. 
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
  protected Label   messageLabel ;	//��Ȳ�� �´� �޼����� �޾� ����մϴ�
  protected Label   blankLabel ;	//�� �Է�ĭ�� �����մϴ�
  protected boolean confirmed ;    //Ȯ���̸� ��, ��ҵǸ� ������ ��ȯ
  protected Button  confirm ;    //Ȯ�ι�ư
  protected Button  cancel ;    //��ҹ�ư
  protected Stage dialog;	//â ����

  //Ȯ��â
  ConfirmDialog(Stage owner, String message, boolean choice)
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
    messageLabel = new Label() ;
    messageLabel.setText(message);
    blankLabel = new Label("") ;

    //��ư �����ϱ�
    Button confirm = new Button() ;    //Ȯ�ι�ư
    confirm.setText("Ok");
    confirm.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			 confirmed = true ;
			 dialog.hide() ;
   		}
    });

    Button cancel = new Button() ;    //��ҹ�ư
    cancel.setText("Cancel");
    cancel.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
		public void handle(ActionEvent e) {
    		confirmed = false ;
    		dialog.hide() ;
    	}
      }) ;
    
    //������ �󺧰� ��ư�� ��ġ�ϱ�
    GridPane root = new GridPane();
    root.add(messageLabel, 0, 0);
    root.add(blankLabel, 1, 0);
    root.add(confirm, 0, 1);
    if (choice) {
    	root.add(cancel, 1, 1);
    }

    //���̾�α� ����
    Scene scene = new Scene(root);
    dialog.setScene(scene);
    dialog.showAndWait();
  }

  boolean isConfirmed()
  {
    return confirmed ;
  }
}