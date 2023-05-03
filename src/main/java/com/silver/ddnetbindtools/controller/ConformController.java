package com.silver.ddnetbindtools.controller;

import com.silver.ddnetbindtools.utils.FileUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.time.LocalDate;


public class ConformController {

    private Callback<ButtonType, Void> resultCallback;

    public void setResultCallback(Callback<ButtonType, Void> resultCallback) {
        this.resultCallback = resultCallback;
    }
    public  Dialog<ButtonType> dialog_window ;

    @FXML
    public TextField bak_input;

    public Dialog<ButtonType> getDialog_window() {
        return dialog_window;
    }

    public void setDialog_window(Dialog<ButtonType> dialog_window) {
        this.dialog_window = dialog_window;
    }

    public void conformBak(){
        String bak = bak_input.getText();
        if (bak == null || bak.isEmpty()) {
            bak = LocalDate.now().toString();
        }
        FileUtil.bakFile(bak);
        if (resultCallback != null) {
            resultCallback.call(ButtonType.OK);
        }
        dialog_window.setResult(ButtonType.OK);
    }

    public void cancelBak(){
        dialog_window.setResult(ButtonType.CANCEL);

    }
    public void initialize() {
        bak_input.setText(LocalDate.now().toString());
    }

}
