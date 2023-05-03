package com.silver.ddnetbindtools.enums;

import java.util.*;

//游戏按键与键盘按键映射
public enum KeyMapping {
    F1("f1", "F1"),
    F2("f2", "F2"),
    F3("f3", "F3"),
    F4("f4", "F4"),
    F5("f5", "F5"),
    F6("f6", "F6"),
    F7("f7", "F7"),
    F8("f8", "F8"),
    F9("f9", "F9"),
    F10("f10", "F10"),
    F11("f11", "F11"),
    F12("f12", "F12"),

    NUM_1("1", "1"),
    NUM_2("2", "2"),
    NUM_3("3", "3"),
    NUM_4("4", "4"),
    NUM_5("5", "5"),
    NUM_6("6", "6"),
    NUM_7("7", "7"),
    NUM_8("8", "8"),
    NUM_9("9", "9"),
    NUM_0("0", "0"),

    Q("q", "Q"),
    W("w", "W"),
    E("e", "E"),
    R("r", "R"),
    T("t", "T"),
    Y("y", "Y"),
    U("u", "U"),
    I("i", "I"),
    O("o", "O"),
    P("p", "P"),

    A("a", "A"),
    S("s", "S"),
    D("d", "D"),
    F("f", "F"),
    G("g", "G"),
    H("h", "H"),
    J("j", "J"),
    K("k", "K"),
    L("l", "L"),
    Z("z", "Z"),
    X("x", "X"),
    C("c", "C"),
    V("v", "V"),
    B("b", "B"),
    N("n", "N"),
    M("m", "M"),

    PRINT_SCREEN("printscreen", "Print Screen"),
    SCROLL_LOCK("lgui", "Scroll Lock"),
    TILDE("grave", "波浪符"),
    MINUS("minus", "减号"),
    EQUAL("equals", "等号"),
    BACKSPACE("backspace", "退格键"),
    INSERT("insert", "Insert"),
    PAGE_UP("pageup", "Page Up"),
    NUM_LOCK("numlockclear", "Num Lock"),
    KP_DIVIDE("kp_divide", "小键盘除号"),
    KP_MULTIPLY("kp_multiply", "小键盘乘号"),
    KP_MINUS("kp_minus", "小键盘减号"),
    TAB("tab", "Tab"),
    LEFT_BRACKET("leftbracket", "左方括号"),
    RIGHT_BRACKET("rightbracket", "右方括号"),
    BACKSLASH("backslash", "反斜杠"),
    DELETE("delete", "Delete"),
    END("end", "End"),
    PAGE_DOWN("pagedown", "Page Down"),

    KP_PLUS("kp_plus", "小键盘加号"),
    CAPS_LOCK("capslock", "大写锁定"),
    SEMICOLON("semicolon", "分号"),
    APOSTROPHE("apostrophe", "单引号"),
    ENTER("return", "回车"),
    LSHIFT("lshift", "左Shift"),

    COMMA("comma", "逗号"),
    PERIOD("period", "句号"),
    SLASH("slash", "斜杠"),
    RSHIFT("rshift", "右Shift"),

    LCTRL("lctrl", "左Ctrl"),
    LWIN("lgui", "左Win"),
    LALT("lalt", "左Alt"),
    SPACE("space", "空格"),
    RALT("ralt", "右Alt"),
    RWIN("rgui", "右Win"),
    RCTRL("rctrl", "右Ctrl"),
    LEFT("left", "左箭头"),

    UP("up", "上箭头"),
    DOWN("down", "下箭头"),
    RIGHT("right", "右箭头"),
    KP_1("kp_1", "小键盘1"),
    KP_2("kp_2", "小键盘2"),
    KP_3("kp_3", "小键盘3"),
    KP_4("kp_4", "小键盘4"),
    KP_5("kp_5", "小键盘5"),
    KP_6("kp_6", "小键盘6"),
    KP_7("kp_7", "小键盘7"),
    KP_8("kp_8", "小键盘8"),
    KP_9("kp_9", "小键盘9"),
    KP_0("kp_0", "小键盘0"),
    KP_ENTER("kp_enter", "小键盘回车"),
    KP_PERIOD("kp_period", "小键盘句号"),
    MOUSE_1("mouse1", "鼠标左键"),
    MOUSE_2("mouse2", "鼠标右键"),
    MOUSE_3("mouse3", "鼠标中键"),
    MOUSE_4("mouse4", "鼠标第四个键"),
    MOUSE_5("mouse5", "鼠标第五个键"),
    SCROLL_UP("mousewheelup", "滚轮向上滚动"),
    SCROLL_DOWN("mousewheeldown", "滚轮向下滚动");



    private final String gameKey;
    private final String keyboardKey;

    KeyMapping(String gameKey, String keyboardKey) {
        this.gameKey = gameKey;
        this.keyboardKey = keyboardKey;
    }

    public String getGameKey() {
        return gameKey;
    }

    public String getKeyboardKey() {
        return keyboardKey;
    }

    public static KeyMapping getByGameKey(String gameKey) {
        for (KeyMapping keyMapping : values()) {
            if (keyMapping.getGameKey().equals(gameKey)) {
                return keyMapping;
            }
        }
        return null;
    }

    public static KeyMapping getByKeyboardKey(String keyboardKey) {
        for (KeyMapping keyMapping : values()) {
            if (keyMapping.getKeyboardKey().equals(keyboardKey)) {
                return keyMapping;
            }
        }
        return null;
    }

    public static List<KeyMapping> getAllMappings() {
        List<KeyMapping> mappings = new ArrayList<>();
        Collections.addAll(mappings, values());
        return mappings;
    }
}
