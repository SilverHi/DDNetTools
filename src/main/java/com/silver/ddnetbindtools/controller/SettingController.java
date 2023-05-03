package com.silver.ddnetbindtools.controller;


import com.silver.ddnetbindtools.Application;
import com.silver.ddnetbindtools.utils.DDNetSettingUtil;
import com.silver.ddnetbindtools.utils.FileUtil;
import com.silver.ddnetbindtools.utils.SettingUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingController {


    @FXML
    private Label check_flag_label;

    @FXML
    private TextField path_input;


    @FXML
    private VBox bakfilebox;

    public void checkPathValid() {

        String path = path_input.getText();
        if (path == null || path.isEmpty()) {
            check_flag_label.setText("路径不能为空");
            SettingUtil.setValue("User","status","false");
            check_flag_label.setStyle("-fx-text-fill: red");
            SettingUtil.save();
            return;
        }
        if (path.endsWith("settings_ddnet.cfg")) {
            boolean exist = FileUtil.isExist(path);
            if (exist) {
                checkSuccess(path);
                return;
            }
        }
        //如果路径中包含DDNet
        if (path.contains("DDNet")) {
            //截取路径中DDNet之前的部分
            String path_prefix = path.substring(0, path.indexOf("DDNet"));
            //拼接路径
            path = path_prefix + "DDNet\\settings_ddnet.cfg";
            boolean exist = FileUtil.isExist(path);
            if (exist) {
                checkSuccess(path);
                return;
            }
        }
        check_flag_label.setText("识别失败，未找到配置文件");
        SettingUtil.setValue("User","status","false");
        SettingUtil.save();
        check_flag_label.setStyle("-fx-text-fill: red");
    }

    public void initialize() {
        System.out.println("enter setting initialize");


        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("setting-view.fxml"));
        fxmlLoader.setController(this);
        String settingPath = SettingUtil.getValue("User","settingPath");
        if (settingPath != null && !settingPath.isEmpty()) {
            path_input.setText(settingPath);
            checkPathValid();
        }
        String value = SettingUtil.getValue("BackFile", "file");
        if (value!=null && !value.isEmpty()){
            String[] fileList = value.split(",");
            for (String file : fileList) {
                addFileToPane(file);
            }
        }


    }




    public void conformBak(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("conform.fxml"));
            Parent root = fxmlLoader.load();
            ConformController conformController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            conformController.setDialog_window(dialog);
            dialog.setTitle("备份当前配置文件");
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().setPadding(new Insets(0,0 , -20, 0));
            conformController.setResultCallback(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    refreshBakFile();
                }
                return null;
            });
            dialog.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void  refreshBakFile(){
        bakfilebox.getChildren().clear();
        String value = SettingUtil.getValue("BackFile", "file");
        if (value!=null && !value.isEmpty()){
            String[] fileList = value.split(",");
            for (String file : fileList) {
                addFileToPane(file);
            }
        }
    }

    private void addFileToPane(String file) {
        if (file == null || file.isEmpty()) {
            return;
        }
        String[] split = file.split("\\|");
        RadioButton radioButton = new RadioButton(split[0]);
        Label label = new Label(split[1]);
        // 设置控件样式
        radioButton.getStyleClass().add("black2");
        radioButton.setTextFill(javafx.scene.paint.Color.GAINSBORO);
        radioButton.setStyle("-fx-font-size: 16px;");
        label.setTextFill(javafx.scene.paint.Color.web("#838383"));
        label.setStyle("-fx-font-size: 17px;");
        Button button = new Button("恢复");
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setStyle("-fx-background-color: #1668dc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 16px;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("恢复配置文件");
                dialog.getDialogPane().setContentText("是否恢复配置文件");
                dialog.getDialogPane().setPadding(new Insets(20, 20, 20, 20));
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                dialog.showAndWait().ifPresent(type -> {
                    if (type == ButtonType.YES) {
                        String ddrSetting = SettingUtil.getValue("User", "settingPath");
                        if (ddrSetting != null) {
                            File ddrSettingFile = new File(ddrSetting);
                            FileUtil.deleteFile(ddrSetting);
                            try {
                                FileUtil.copy(ddrSettingFile.getPath().replace("\\"+ddrSettingFile.getName(),"")+"\\"+split[0],ddrSetting);
                            } catch (IOException e) {
                                Dialog<ButtonType> dialog1 = new Dialog<>();
                                dialog1.setTitle("恢复失败");
                                dialog1.getDialogPane().setContentText("恢复失败,请检查配置文件是否被占用，请关闭游戏后重试");
                                dialog1.getDialogPane().setPadding(new Insets(20, 20, 20, 20));
                                dialog1.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                                dialog1.showAndWait().ifPresent(type1 -> {
                                    if (type1 == ButtonType.OK) {
                                        // 在这里编写Button被点击后要执行的代码
                                    }
                                });
                                throw new RuntimeException(e);
                            }
                            // 在这里编写Button被点击后要执行的代码
                            // 恢复成功
                            Dialog<ButtonType> dialog1 = new Dialog<>();
                            dialog1.setTitle("恢复成功");
                            dialog1.getDialogPane().setContentText("恢复成功");
                            dialog1.getDialogPane().setPadding(new Insets(20, 20, 20, 20));
                            dialog1.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                            dialog1.showAndWait().ifPresent(type1 -> {
                                if (type1 == ButtonType.OK) {
                                    // 在这里编写Button被点击后要执行的代码
                                    refreshBakFile();
                                }
                            });

                        }
                    }
                });
            }
        });


        Button del_button = new Button("删除");
        del_button.setTextFill(javafx.scene.paint.Color.WHITE);
        del_button.setStyle("-fx-background-color: red; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 16px;");
        del_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //弹框确认删除
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("删除配置文件");
                dialog.getDialogPane().setContentText("是否删除配置文件");
                dialog.getDialogPane().setPadding(new Insets(20, 20, 20, 20));
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                dialog.showAndWait().ifPresent(type -> {
                    if (type == ButtonType.YES) {
                        String ddrSetting = SettingUtil.getValue("User", "settingPath");
                        if (ddrSetting != null) {
                            File ddrSettingFile = new File(ddrSetting);
                            String path = ddrSettingFile.getPath().replace("\\" + ddrSettingFile.getName(), "") + "\\" + split[0];
                            FileUtil.deleteFile(path);

                            String value = SettingUtil.getValue("BackFile", "file");
                            String[] fileList = value.split(",");
                            List<String> list = new ArrayList<>();
                            for (String file : fileList) {
                                if (!file.split("\\|")[0].equals(split[0])){
                                    list.add(file);
                                }
                            }
                            String join = String.join(",", list);
                            SettingUtil.setValue("BackFile","file",join);
                            refreshBakFile();
                            // 在这里编写Button被点击后要执行的代码
                        }

                    }
                });
            }
        });
        if ("首次启动备份".equals(split[1])){
            del_button.setVisible(false);
        }
        // 创建HBox并添加子控件
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setMinHeight(60);
        hBox.setMaxHeight(60);
        hBox.setMaxWidth(600);
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0, 0, 0, 20));
        hBox.getStyleClass().add("black2");
        hBox.getChildren().addAll(radioButton, label, button,del_button);
        bakfilebox.getChildren().add(hBox);
    }

    public void checkSuccess(String path) {
        check_flag_label.setText("识别成功，已自动修改为配置文件路径");
        check_flag_label.setStyle("-fx-text-fill: green");
        SettingUtil.setValue("User","settingPath",path);
        SettingUtil.setValue("User","status","true");
        SettingUtil.save();
        path_input.setText(path);

        String first_flag = SettingUtil.getValue("User", "firstRun");
        if (first_flag == null || first_flag.isEmpty()||first_flag.equals("true")) {
            //第一次运行
            //备份配置文件
            String newPath = FileUtil.bak(path);
            //从newPath 获取文件名
            SettingUtil.setValue("BackFile", "file",new File(newPath).getName()+"|首次启动备份");
            SettingUtil.setValue("User", "firstRun", "false");
            DDNetSettingUtil.addinitBind();
            DDNetSettingUtil.loadBindType();
            SettingUtil.save();
            refreshBakFile();
        }
    }

}
