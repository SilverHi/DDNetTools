package com.silver.ddnetbindtools.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static boolean isExist(String path) {
        //判断文件或文件夹是否存在
        return new File(path).exists();
    }

    public static void createDir(String path) {
        //创建文件夹
        new File(path).mkdir();
    }

    //创建文件
    public static void createFile(String path, String fileName){
        try {
            new File(path + "\\" + fileName).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void copy(String sourceFilePath, String targetFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);

        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    public static void deleteFile(String path){
        File file = new File(path); // 创建File对象
        // 调用delete()方法删除文件
        if (!file.delete()) {
            //TODO 弹框解除占用

        }
    }

    public static String bak(String path){
        String newPath = path+".bak";
        boolean fileExist ;
        int index = 1;
        String currentPath = newPath;
        do {
            fileExist = FileUtil.isExist(currentPath);
            if (fileExist){
                currentPath =newPath+index;
                index++;
            }
        }
        while (fileExist);

        try {
            FileUtil.copy(path,currentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentPath;
    }
    public static void bakFile(String remark){
        String value = SettingUtil.getValue("User", "settingPath");
        if (value!=null){
            String newPath = bak(value);
            String files = SettingUtil.getValue("BackFile", "file");
            if (files!=null){
                SettingUtil.setValue("BackFile", "file",files+","+new File(newPath).getName()+"|"+remark);
            }else {
                SettingUtil.setValue("BackFile", "file",new File(newPath).getName()+"|"+remark);
            }
            SettingUtil.save();
        }
    }

    public static String readFile(String ddrSettingPath) {
        File file = new File(ddrSettingPath);
        if (file.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                br.close();
                return sb.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static void writeFile(String ddrSettingPath, String ddrSetting) {
        File file = new File(ddrSettingPath);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            bw.write(ddrSetting);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
