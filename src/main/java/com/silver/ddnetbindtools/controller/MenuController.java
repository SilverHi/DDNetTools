package com.silver.ddnetbindtools.controller;

import com.silver.ddnetbindtools.Application;
import com.silver.ddnetbindtools.utils.FileUtil;
import com.silver.ddnetbindtools.utils.SettingUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuController {
    @FXML
    private Label topic_label;

    @FXML
    private Button setting_btn;

    @FXML
    private Button bindlist_btn;

    @FXML
    private VBox page_box;

    public Scene setting_scene;
    public Scene bindlist_scene;

    public BindListController bindListController;

//
//    @FXML
//    protected void onHelloButtonClick() {
//    }

    public void resetApp(){
        String appDataPath = System.getenv("APPDATA");
        //是否存在DDNetBindTools目录
        String ddnetBindToolsPath = appDataPath + "\\DDNetBindTools";
        //目录是否存在 不存在则创建
        if (!FileUtil.isExist(ddnetBindToolsPath)) {
            FileUtil.createDir(ddnetBindToolsPath);
        }
        //是否存在setting.ini文件
        String filename = "setting.ini";
        String settingFilePath = ddnetBindToolsPath + "\\" + filename;
        if (FileUtil.isExist(settingFilePath)) {
            FileUtil.deleteFile(settingFilePath);
        }
        //弹框提示重启
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("重置成功");
        dialog.getDialogPane().setContentText("请重启软件");
        dialog.getDialogPane().setPadding(new Insets(20, 20, 20, 20));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES);
        dialog.showAndWait().ifPresent(type -> {
            Platform.exit();
        });

    }


    public void initialize() {
        FXMLLoader bindListfxmlLoader = new FXMLLoader(Application.class.getResource("bindlist-view.fxml"));
        FXMLLoader settingfxmlLoader = new FXMLLoader(Application.class.getResource("setting-view.fxml"));
        try {
            setting_scene = new Scene(settingfxmlLoader.load(), 1300, 900);
            bindlist_scene = new Scene(bindListfxmlLoader.load(), 1300, 900);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bindListController= bindListfxmlLoader.getController();

        page_box.getChildren().clear();

        String value = SettingUtil.getValue("User", "status");
        if (value == null || "false".equals(value)){
            topic_label.setText("通用配置");
            page_box.getChildren().add(setting_scene.getRoot());
        }else {
            topic_label.setText("bind管理");
            page_box.getChildren().add(bindlist_scene.getRoot());
        }
        setting_btn.setOnAction(event -> {
            topic_label.setText("通用配置");
            try {
                page_box.getChildren().clear();
                page_box.getChildren().add(setting_scene.getRoot());
                page_box.getChildren().clear();
                page_box.getChildren().add(setting_scene.getRoot());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bindlist_btn.setOnAction(event -> {
            topic_label.setText("bind管理");
            try {
                page_box.getChildren().clear();
                page_box.getChildren().add(bindlist_scene.getRoot());
                page_box.getChildren().clear();
                page_box.getChildren().add(bindlist_scene.getRoot());
                bindListController.refreshBindList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}