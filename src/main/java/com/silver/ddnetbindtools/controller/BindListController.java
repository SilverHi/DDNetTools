package com.silver.ddnetbindtools.controller;


import com.silver.ddnetbindtools.Application;
import com.silver.ddnetbindtools.utils.DDNetSettingUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class BindListController {

    @FXML
    private VBox bindlist_box;

    @FXML
    private Button add_bind_btn;


    public void initialize() {
        System.out.println("enter BindListController initialize");
        loadBindList();
    }


    public void loadBindList() {
        DDNetSettingUtil.load();
        DDNetSettingUtil.getSettings_binds().forEach(this::addBindToPane);

        add_bind_btn.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("add-bind-view.fxml"));
                Parent root = fxmlLoader.load();
                AddBindController addBindController = fxmlLoader.getController();

                Stage addBindStage = new Stage();
                addBindStage.setTitle("新建bind");
                addBindStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                addBindStage.setScene(new javafx.scene.Scene(root, 800, 400));
                addBindStage.show();
                addBindController.setResultCallback(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        refreshBindList();
                        addBindStage.close();
                    }else if (buttonType == ButtonType.CANCEL){
                        addBindStage.close();
                    }
                    return null;
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }



    public void conformBak(){

    }

    public void  refreshBindList(){
        bindlist_box.getChildren().clear();
        loadBindList();
    }

    private void addBindToPane(String bind) {

        //只切割bind前两个空格
        String[] bindArr = bind.split(" ", 3);
        if (bindArr.length < 3) {
            return;
        }
        String key = bindArr[1];
        String value = bindArr[2];
        //去掉value头尾的双引号
        value = value.substring(1, value.length() - 1);


        HBox hbox = new HBox();
        hbox.setPrefSize(780, 38);
        hbox.setSpacing(20);
        hbox.getStyleClass().add("black2");
        hbox.getStylesheets().add("common.css");

        Label label1 = new Label("bind");
        label1.setPrefSize(44, 32);
        label1.setAlignment(javafx.geometry.Pos.CENTER);
        label1.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label1.setTextFill(javafx.scene.paint.Color.web("#f2f2f2"));
        label1.setFont(new Font(18));

        Label label2 = new Label(key);
        label2.setPrefSize(USE_COMPUTED_SIZE, 32);
        label2.setAlignment(javafx.geometry.Pos.CENTER);
        label2.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label2.setFont(new Font(18));
        label2.getStyleClass().add("black1");
        label2.setMinWidth(100);
        label2.getStylesheets().add("common.css");
        label2.setTextFill(javafx.scene.paint.Color.web("#f2f2f2"));
        label2.setPadding(new Insets(5));

        TextField textField = new TextField(value);
        textField.setPrefSize(300, 35);
        textField.setEditable(false);
        textField.getStyleClass().addAll("black1", "input_font_color");
        textField.getStylesheets().add("common.css");
        textField.setFont(Font.font("System Italic", 15));
        textField.setPadding(new Insets(0, 0, 0, 20));

        Button button1 = new Button("修改");
        button1.setPrefSize(59, USE_COMPUTED_SIZE);
        button1.setAlignment(javafx.geometry.Pos.CENTER);
        button1.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button1.setTextFill(javafx.scene.paint.Color.WHITE);
        button1.setStyle("-fx-background-color: #1668dc; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        button1.setFont(new Font(16));
        button1.setCursor(javafx.scene.Cursor.HAND);



        Button button3 = new Button("确认");
        button3.setPrefSize(59, USE_COMPUTED_SIZE);
        button3.setAlignment(javafx.geometry.Pos.CENTER);
        button3.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button3.setTextFill(javafx.scene.paint.Color.WHITE);
        button3.setStyle("-fx-background-color: #6aab73; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        button3.setFont(new Font(16));
        button3.setCursor(javafx.scene.Cursor.HAND);
        button3.setManaged(false);



        Button button2 = new Button("删除");
        button2.setPrefSize(59, USE_COMPUTED_SIZE);
        button2.setAlignment(javafx.geometry.Pos.CENTER);
        button2.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        button2.setTextFill(javafx.scene.paint.Color.WHITE);
        button2.setStyle("-fx-background-color: #fd676f; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        button2.setFont(new Font(16));
        button2.setCursor(javafx.scene.Cursor.HAND);



        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setEditable(true);
                textField.requestFocus();
                textField.selectAll();
                button1.setManaged(false);
                button2.setManaged(false);
                button3.setManaged(true);
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DDNetSettingUtil.removeBind(bind);
                DDNetSettingUtil.save();
                refreshBindList();
            }
        });


        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setEditable(false);
                button1.setManaged(true);
                button2.setManaged(true);
                button3.setManaged(false);

                String userinput = textField.getText();
                String newBind = "bind " + key + " \""+ DDNetSettingUtil.formatBind(userinput)+"\"";
                DDNetSettingUtil.replaceBind(bind, newBind);
                DDNetSettingUtil.save();
                refreshBindList();
            }
        });





        hbox.getChildren().addAll(label1, label2, textField, button1,button3, button2);
        hbox.setPadding(new Insets(10));
        bindlist_box.getChildren().add(hbox);

    }

    public void checkSuccess(String path) {
    }

}
