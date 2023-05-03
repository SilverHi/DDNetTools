package com.silver.ddnetbindtools;

import com.silver.ddnetbindtools.manager.BindTypeManager;
import com.silver.ddnetbindtools.utils.DDNetSettingUtil;
import com.silver.ddnetbindtools.utils.FileUtil;
import com.silver.ddnetbindtools.utils.SettingUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        initApp();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 900);
        stage.setTitle("DDNetBindTools");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public void initApp(){
        //获取用户appDate目录
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
        if (!FileUtil.isExist(settingFilePath)) {
            FileUtil.createFile(ddnetBindToolsPath, filename);
        }


        //加载配置文件
        SettingUtil.loadSetting(settingFilePath);
        //查找DDnet配置文件
        DDNetSettingUtil.findDDNetSetting();

        //加载bind模板配置
        DDNetSettingUtil.loadBindType();
    }
}