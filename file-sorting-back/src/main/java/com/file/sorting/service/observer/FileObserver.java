package com.file.sorting.service.observer;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.*;

@Slf4j
@NoArgsConstructor
public class FileObserver {
    private static final String HOME_DIR = "HOME";
    private static final String DEV_DIR = "DEV";
    private static final String TEST_DIR = "TEST";

    public void process() throws IOException, InterruptedException {
        Path homePath = Paths.get(HOME_DIR);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        homePath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)){
                    Path filename = (Path) event.context();
                    Path filePath = homePath.resolve(filename);
                    File file = filePath.toFile();

                    if(getFileExtension(file.getName()).equals("jar")){
                        log.info("SAVE TO TEST FOLDER, jar extension");
                        saveFile(file, "TEST/" + file.getName());
                    }
                    if(getFileExtension(file.getName()).equals("xml")){
                        log.info("SAVE TO DEV FOLDER, xml extension");
                        saveFile(file, "DEV/" + file.getName());
                    }

                    log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                }
            }
            key.reset();
        }
    }

    private String getFileExtension(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void saveFile(File file, String path){
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

}
