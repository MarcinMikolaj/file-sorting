package com.file.sorting.infrastructure.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class FileUtils {

    public static void saveFile(File file, String path){
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = new FileOutputStream(path)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0)
                outputStream.write(buffer, 0, length);

            System.out.println("File saved");
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static String getFileExtension(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String saveFileToFolder(MultipartFile file, String path) throws IOException {
        File file1 = new File(path);
        file.transferTo(file1.getAbsoluteFile());
        return path;
    }

    public static int getTime(Path path) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attributes.creationTime();
        Instant instant = creationTime.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.getHour();
    }
    public static void createDirectoryStructure() {
        new File("HOME").mkdirs();
        new File("DEV").mkdirs();
        new File("TEST").mkdirs();
    }
}
