package com.silver.ddnetbindtools.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SettingUtil {


    private static String filePath;
    private static final Map<String, Map<String, String>> sections = new LinkedHashMap<>();


    public static void loadSetting(String path) {
        filePath = path;
        load();
    }

    public static void load() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath,StandardCharsets.UTF_8));
            String line;
            String sectionName = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.matches("\\[.*\\]")) {
                    sectionName = line.substring(1, line.length() - 1);
                    sections.put(sectionName, new LinkedHashMap<>());
                } else if (line.matches(".*=.*")) {
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    sections.get(sectionName).put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void save() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath,StandardCharsets.UTF_8));
            for (String sectionName : sections.keySet()) {
                writer.write("[" + sectionName + "]\n");
                for (String key : sections.get(sectionName).keySet()) {
                    String value = sections.get(sectionName).get(key);
                    writer.write(key + " = " + value + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getValue(String sectionName, String key) {
        if (sections.containsKey(sectionName) && sections.get(sectionName).containsKey(key)) {
            return sections.get(sectionName).get(key);
        }
        return null;
    }

    public static void setValue(String sectionName, String key, String value) {
        if (!sections.containsKey(sectionName)) {
            sections.put(sectionName, new LinkedHashMap<>());
        }
        sections.get(sectionName).put(key, value);
        save();
    }

    public static Set<String> getSectionNameGroup(String sectionName) {
        //返回以sectionName开头的所有sectionName
        Set<String> sectionNameGroup = new HashSet<>();
        for (String name : sections.keySet()) {
            if (name.startsWith(sectionName)) {
                sectionNameGroup.add(name);
            }
        }
        return sectionNameGroup;
    }



    public static Set<String> getSectionNames() {
        return sections.keySet();
    }

    public static Set<String> getKeys(String sectionName) {
        if (sections.containsKey(sectionName)) {
            return sections.get(sectionName).keySet();
        }
        return null;
    }

    public static void removeSection(String sectionName) {
        sections.remove(sectionName);
    }

    public static void removeKey(String sectionName, String key) {
        if (sections.containsKey(sectionName)) {
            sections.get(sectionName).remove(key);
        }
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        SettingUtil.filePath = filePath;
    }
}
