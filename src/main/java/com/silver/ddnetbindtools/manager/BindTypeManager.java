package com.silver.ddnetbindtools.manager;

import com.silver.ddnetbindtools.entity.BindType;

import java.util.*;

public class BindTypeManager {
    private static Map<String, BindType> bindTypeMap = new LinkedHashMap<>();

    static {
        // 初始化绑定类型
        bindTypeMap.put("自定义", new BindType("自定义", "自定义bind", ""));
    }

    public static void addBindType(String typeName, String tips, String bindstr) {
        BindType bindType = new BindType(typeName, tips, bindstr);
        bindTypeMap.put(typeName, bindType);
    }

    public static BindType getBindType(String typeName) {
        return bindTypeMap.get(typeName);
    }

    public static List<BindType> getAllBindTypes() {
        Collection<BindType> values = bindTypeMap.values();
        List<BindType> reslet = new ArrayList<>();
        reslet.addAll(values);
        return reslet;
    }
}
