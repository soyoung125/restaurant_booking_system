/*
 * Restaurant Booking System: ModifyInfo
 *
 * 회원정보 수정 창
 */
package booksys.presentation;

import booksys.application.domain.Customer;
import booksys.application.persistency.CustomerMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModifyInfo {
    Stage stage;    //창
    Customer c;     //고객
    Label title;    //제목(라벨)
    TextField id;      //아이디 입력칸
    TextField passward;    //비밀번호 입력칸
    TextField name;    //이름 입력칸
    TextField phone;   //전화번호 입력칸
    Button finish;     //종료 버튼

    //회원 정보 수정 창
    public ModifyInfo(Stage primaryStage, Customer c){
        this.c = c;
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setTitle("Modify Infomation");

        title = new Label("회원 정보 수정");
        title.setFont(Font.font(40));

        id = new TextField();
        id.setDisable(true);
        id.setText(c.getUserId());

        passward = new TextField();
        passward.setText("password");

        name = new TextField();
        name.setDisable(true);
        name.setText(c.getName());

        phone = new TextField();
        phone.setPromptText(c.getPhoneNumber());

        finish = new Button("finish");
        finish.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String pwd = passward.getText();
                String pn = phone.getText();

                if (!CustomerMapper.getInstance().updateCustomer(c, pwd, pn)) return;
                Alert a1 = new Alert(Alert.AlertType.NONE, "finish", ButtonType.OK);
                a1.showAndWait();
                //stage.close();
            }
        }));
        finish.setMaxWidth(Double.MAX_VALUE);

        VBox root = new VBox();
        root.setPadding(new Insets(10,80,20,80));
        root.getChildren().add(title);
        root.getChildren().add(id);
        root.getChildren().add(passward);
        root.getChildren().add(name);
        root.getChildren().add(phone);
        root.getChildren().add(finish);

        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}