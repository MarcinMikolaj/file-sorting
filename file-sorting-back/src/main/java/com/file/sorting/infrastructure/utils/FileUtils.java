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
import java.util.List;

@Component
public class FileUtils {

    public static File saveMultipartFile(MultipartFile file, String path) throws IOException {
        File savedFile = new File(path);
        file.transferTo(savedFile.getAbsoluteFile());
        return savedFile;
    }

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

    public static int getTime(Path path) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attributes.creationTime();
        Instant instant = creationTime.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.getHour();
    }

    public static File createDirectory(String path){
        return new File(path);
    }

    public static String readFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        List<String> lines = Files.readAllLines(path);
        StringBuffer buffer = new StringBuffer();
        lines.forEach(s -> buffer.append(s).append("\n"));
        return buffer.toString();
    }

    private static void writeToFile(File file, String value) throws IOException{
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(value);
        printWriter.close();
    }

    public static void overwriteFile(String filePath, String content) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
    }

    public static File getFileFromPath(String filePath) {
        return new File(filePath);
    }

}
