package com.silver.ddnetbindtools.utils;

import com.silver.ddnetbindtools.manager.BindTypeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDNetSettingUtil {

    private static List<String> settings_head = new ArrayList<>();

    private static List<String> settings_binds= new ArrayList<>();

    private static List<String> settings_other= new ArrayList<>();

    private static List<String> used_key = new ArrayList<>();

    public static void load(){
        String status = SettingUtil.getValue("User", "status");
        if (status == null || "false".equals(status)) {
            return;
        }
        String ddrSettingPath = SettingUtil.getValue("User", "settingPath");

        String ddrSetting = FileUtil.readFile(ddrSettingPath);
        if (ddrSetting == null) {
            return;
        }
        settings_binds.clear();
        settings_head.clear();
        settings_other.clear();
        String[] settings = ddrSetting.split("\n");
        boolean isBind = false;
        for (String setting : settings) {
            if ("unbindall".equals(setting)) {
                settings_head.add(setting);
                isBind = true;
                continue;
            }
            if (!isBind ) {
                settings_head.add(setting);
                continue;
            }

            if (isBind && setting.startsWith("bind")) {
                String[] split = setting.split(" ", 3);
                if (split.length < 3) {
                    continue;
                }
                used_key.add(split[1]);
                settings_binds.add(setting);
                continue;
            }
            settings_other.add(setting);
        }
    }

    public static List<String> getSettings_head() {
        return settings_head;
    }

    public static void setSettings_head(List<String> settings_head) {
        DDNetSettingUtil.settings_head = settings_head;
    }

    public static List<String> getSettings_binds() {
        return settings_binds;
    }

    public static void setSettings_binds(List<String> settings_binds) {
        DDNetSettingUtil.settings_binds = settings_binds;
    }

    public static List<String> getUsed_key() {
        return used_key;
    }

    public static void setUsed_key(List<String> used_key) {
        DDNetSettingUtil.used_key = used_key;
    }

    public static List<String> getSettings_other() {
        return settings_other;
    }

    public static void setSettings_other(List<String> settings_other) {
        DDNetSettingUtil.settings_other = settings_other;
    }

    public static void replaceBind(String bind, String newBind) {
        for (int i = 0; i < settings_binds.size(); i++) {
            if (bind.equals(settings_binds.get(i))) {
                settings_binds.set(i, newBind);
                return;
            }
        }
    }

    public static void save(){
        List<String> allSettings = new ArrayList<>();
        allSettings.addAll(settings_head);
        allSettings.addAll(settings_binds);
        allSettings.addAll(settings_other);
        String ddrSettingPath = SettingUtil.getValue("User", "settingPath");
        String ddrSetting = String.join("\n", allSettings);
        FileUtil.writeFile(ddrSettingPath, ddrSetting);
    }

    public static void removeBind(String bind) {
        for (int i = 0; i < settings_binds.size(); i++) {
            if (bind.equals(settings_binds.get(i))) {
                settings_binds.remove(i);
                return;
            }
        }
    }

    public static void addBind(String addbind) {
        //插到头部
        settings_binds.add(0, addbind);
        save();
    }

    public static String formatBind(String bind) {
        DDNetSettingUtil.load();
        String bindstr = bind.replace("\\\"","\"").replace("\"", "\\\"").replace("“", "\\\"").replace("”", "\\\"").replace("‘", "\\\"").replace("’", "\\\"").trim();
        //如果bindstr中含有变量 {{}}， 获取变量名，从settings_other中获取变量值，并替换
        //正则获取变量名
        String regex = "\\{\\{(.+?)\\}\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bindstr);
        while (matcher.find()) {
            String varName = matcher.group(1);
            List<String> settingsHead = DDNetSettingUtil.getSettings_head();
            String varValue = "0";
            if (varName.equals("cl_mouse_max_distance")) {
                varValue = "400";
            }
            if (varName.equals("inp_mousesens")) {
                varValue = "200";
            }
            for (String s : settingsHead) {
                if (s.startsWith(varName)) {
                    varValue = s.split(" ", 2)[1];
                    break;
                }
            }
            bindstr = bindstr.replace("{{"+varName+"}}", varValue);
        }




        return bindstr;
    }


    public static void loadBindType(){
        BindTypeManager.addBindType("自定义", "自定义bind", "");
        Set<String> bind = SettingUtil.getSectionNameGroup("bind");
        for (String s : bind) {
            String typeName = SettingUtil.getValue(s, "typeName");
            String tips = SettingUtil.getValue(s, "tips");
            String bindstr = SettingUtil.getValue(s, "bindstr");
            if (typeName == null || tips == null || bindstr == null||"".equals(typeName)||"".equals(tips)||"".equals(bindstr)){
                continue;
            }
            BindTypeManager.addBindType(typeName, tips, bindstr);
        }
    }




    public static void addinitBind() {
        SettingUtil.setValue("bind-45", "typeName", "固定90度45度角");
        SettingUtil.setValue("bind-45", "tips", "【按住不松】绑定按键时，鼠标瞄准方向将固定为90度45度角");
        SettingUtil.setValue("bind-45", "bindstr", "+toggle cl_mouse_max_distance 2  {{cl_mouse_max_distance}}; +toggle inp_mousesens 1 {{inp_mousesens}}");

        SettingUtil.setValue("bind-slow", "typeName", "鼠标移动一像素");
        SettingUtil.setValue("bind-slow", "tips", "【按住不松】绑定按键时，鼠标移动速度将变为1像素，用于苗人凤时的精细调整，肥肠好用！");
        SettingUtil.setValue("bind-slow", "bindstr", "+toggle inp_mousesens 1 {{inp_mousesens}}");

        SettingUtil.setValue("bind-speak", "typeName", "一键喊话");
        SettingUtil.setValue("bind-speak", "tips", "【按下】绑定按键时，快捷向公屏喊话，修改喊话内容");
        SettingUtil.setValue("bind-speak", "bindstr", "say \"喊话内容\"");

        SettingUtil.setValue("bind-emote", "typeName", "一键发表情");
        SettingUtil.setValue("bind-emote", "tips", "【按下】绑定按键时，发送表情，长按可以快速发表情，修改指令中的emote 0，0为表情编号，0-13");
        SettingUtil.setValue("bind-emote", "bindstr", "emote 0");


        SettingUtil.setValue("bind-spec", "typeName", "一键隐身");
        SettingUtil.setValue("bind-spec", "tips", "【按下】绑定按键时，进入spec状态，隐藏并旁观，需要服务器支持，简单图一般不支持，Gores和高阶比较常用");
        SettingUtil.setValue("bind-spec", "bindstr", "say \"/spec\"");

        SettingUtil.setValue("bind-team", "typeName", "快速进入队伍");
        SettingUtil.setValue("bind-team", "tips", "【按下】绑定按键时，直接进入设置的队伍中，修改指令中的team 1，1为队伍编号");
        SettingUtil.setValue("bind-team", "bindstr", "say \"/team 1\"");

        SettingUtil.setValue("bind-lock", "typeName", "锁定队伍");
        SettingUtil.setValue("bind-lock", "tips", "【按下】绑定按键时，锁定队伍，再次按下解锁");
        SettingUtil.setValue("bind-lock", "bindstr", "say \"/lock\"");

        SettingUtil.setValue("bind-pr", "typeName", "一键键入练习模式");
        SettingUtil.setValue("bind-pr", "tips", "【按下】绑定按键时，进入练习模式");
        SettingUtil.setValue("bind-pr", "bindstr", "say \"/practice\"");

        SettingUtil.setValue("bind-tp", "typeName", "练习模式-tp");
        SettingUtil.setValue("bind-tp", "tips", "练习模式中在旁观状态下【按下】绑定按键时，传送到视角中心位置");
        SettingUtil.setValue("bind-tp", "bindstr", "say \"/tp\"");

        SettingUtil.setValue("bind-r", "typeName", "练习模式-复活");
        SettingUtil.setValue("bind-r", "tips", "练习模式中【按下】绑定按键时，传送到入水前上一个位置");
        SettingUtil.setValue("bind-r", "bindstr", "say \"/r\"");
        SettingUtil.save();

    }


    public static void findDDNetSetting() {
        String ddrSettingPath = SettingUtil.getValue("User", "settingPath");
        if (ddrSettingPath == null || "".equals(ddrSettingPath)) {
            String appDataPath = System.getenv("APPDATA");
            SettingUtil.setValue("User", "settingPath",appDataPath+"\\DDNet");
            SettingUtil.save();
        }
    }
}
