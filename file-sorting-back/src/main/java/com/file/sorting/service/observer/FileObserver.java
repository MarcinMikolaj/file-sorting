package com.file.sorting.service.observer;

import com.file.sorting.infrastructure.utils.FileUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.*;

@Slf4j
@NoArgsConstructor
public class FileObserver {
    private static final String HOME_DIR = "HOME";

    public void process() throws IOException, InterruptedException {
        log.info("WatchService process start");
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

                    if(FileUtils.getFileExtension(file.getName()).equals("jar")){
                        if(FileUtils.getTime(filePath) % 2 != 0){
                            log.info("SAVE TO TEST FOLDER, jar extension");
                            FileUtils.saveFile(file, "TEST/" + file.getName());
                        }else {
                            log.info("SAVE TO TEST FOLDER, jar extension");
                            FileUtils.saveFile(file, "DEV/" + file.getName());
                        }
                    }
                    if(FileUtils.getFileExtension(file.getName()).equals("xml")){
                        log.info("SAVE TO DEV FOLDER, xml extension");
                        FileUtils.saveFile(file, "DEV/" + file.getName());
                    }
                    log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                }
            }
            key.reset();
        }
    }

}
