package com.silver.ddnetbindtools.controller;

import com.silver.ddnetbindtools.entity.BindType;
import com.silver.ddnetbindtools.enums.KeyMapping;
import com.silver.ddnetbindtools.manager.BindTypeManager;
import com.silver.ddnetbindtools.utils.DDNetSettingUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AddBindController {

    private Callback<ButtonType, Void> resultCallback;

    public void setResultCallback(Callback<ButtonType, Void> resultCallback) {
        this.resultCallback = resultCallback;
    }
    @FXML
    public ComboBox<String> bind_type_select;

    @FXML
    public ComboBox<String> bind_select;

    @FXML
    public Button view_mapping_btn;

    @FXML
    public TextField bind_input;

    @FXML
    public Button add_btn;

    @FXML
    public Button cancel_btn;
    @FXML
    public Label bind_tips;


    public void addbind(){
        String bindkey = bind_select.getValue();
        if (bindkey == null || bindkey.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("请选择按键");
            alert.showAndWait();
            return;
        }



        String addbind ="bind "+bind_select.getValue()+" \""+DDNetSettingUtil.formatBind(bind_input.getText())+"\"";
        DDNetSettingUtil.addBind(addbind);
        if (resultCallback != null) {
            //添加成功提示
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("添加成功");
            alert.showAndWait();
            resultCallback.call(ButtonType.OK);
        }

    }
    public void canceladd(){
        if (resultCallback != null) {
            resultCallback.call(ButtonType.CANCEL);
        }

    }


    public void initialize() {

        view_mapping_btn.setOnAction(event -> {
                    Dialog<Image> dialog = new Dialog<>();
                    dialog.setTitle("按键映射图");
                    dialog.setHeaderText(null);
                    dialog.setGraphic(null);
                    ImageView imageView = new ImageView(new Image("keboradmapping.png"));
                    dialog.getDialogPane().setContent(imageView);
                    ButtonType closeButton = new ButtonType("关闭", ButtonBar.ButtonData.CANCEL_CLOSE);
                    dialog.getDialogPane().getButtonTypes().add(closeButton);
                    dialog.showAndWait();
        });

        bind_type_select.valueProperty().addListener((observable, oldValue, newValue) -> {
            BindType bindType = BindTypeManager.getBindType(newValue);
            if (bindType != null) {
                bind_tips.setText(bindType.getTips());
            }
            if (Objects.equals(newValue, "自定义")) {
                bind_input.setText("");
                return;
            }
            bind_input.setText(DDNetSettingUtil.formatBind(bindType.getBindstr()));
        });


        bind_type_select.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Font a = new Font("微软雅黑", 18);
                setText(item);
                setFont(a);
                setTextFill(Color.WHITE);
            }
        });
        ListCell<String> buttonCell = bind_type_select.getButtonCell();
        buttonCell.setCursor(javafx.scene.Cursor.HAND);
        BindTypeManager.getAllBindTypes().forEach(bindType -> {
            bind_type_select.getItems().add(bindType.getTypeName());
        });
        bind_type_select.getSelectionModel().select(0);
        bind_type_select.setCursor(javafx.scene.Cursor.HAND);



        bind_select.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Font a = new Font("微软雅黑", 18);
                setText(item);
                setFont(a);
                setTextFill(Color.WHITE);
            }
        });
        ListCell<String> buttonCell2 = bind_select.getButtonCell();
        buttonCell2.setCursor(javafx.scene.Cursor.HAND);
        KeyMapping.getAllMappings().forEach(mapping -> {
            bind_select.getItems().add(mapping.getGameKey());
        });
        bind_select.setCursor(javafx.scene.Cursor.HAND);
        bind_select.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, oldValue)) {
                return;
            }
            if (DDNetSettingUtil.getUsed_key().contains(newValue)) {
                AtomicReference<String> rep_bind = new AtomicReference<>("");
                DDNetSettingUtil.getSettings_binds().forEach(bind -> {
                    if (bind.startsWith("bind "+newValue+" ")) {
                        rep_bind.set(bind);
                    }
                });
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("警告");
                alert.setHeaderText(null);
                alert.setContentText("该按键已被绑定，绑定内容为：\n"+rep_bind.get()+"\n请重新选择按键");
                alert.showAndWait();
                bind_select.getSelectionModel().clearSelection();
            }
        });


    }
}
