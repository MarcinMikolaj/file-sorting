package com.file.sorting.service.observer;

import com.file.sorting.infrastructure.exception.exceptions.IllegalFileExtensionException;
import com.file.sorting.infrastructure.utils.FileUtils;
import com.file.sorting.model.Directory;
import com.file.sorting.model.Extension;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.*;
import java.text.MessageFormat;

@Slf4j
@NoArgsConstructor
public final class FileObserver {
    private final String COUNT_FILE_TRANSFER_FILE = "HOME/count.txt";

    public void initialize() throws IOException, InterruptedException, IllegalFileExtensionException {
        log.info("WatchService Process Start For HOME Directory");
        Path homePath = Paths.get(Directory.HOME.toString());
        WatchService watchService = FileSystems.getDefault().newWatchService();
        homePath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)){
                    Path filePath = homePath.resolve((Path) event.context());
                    File file = filePath.toFile();
                    processFile(file, filePath);
                    log.info(MessageFormat.format("Event kind: {0}. File affected: {1}.", event.kind(), event.context()));
                }
            }
            key.reset();
        }
    }

    private void processFile(File file, Path filePath) throws IllegalFileExtensionException, IOException {
        String extension = FileUtils.getFileExtension(file.getName());
        if(extension.equals(Extension.JAR.toString().toLowerCase())){
            if(FileUtils.getTime(filePath) % 2 != 0)
                assignFile(file, MessageFormat.format("{0}/{1}", Directory.TEST, file.getName()));
            else
                assignFile(file, MessageFormat.format("{0}/{1}", Directory.DEV, file.getName()));
        } else if(extension.equals(Extension.XML.toString().toLowerCase()))
            assignFile(file, MessageFormat.format("{0}/{1}", Directory.DEV, file.getName()));
         else
            throw new IllegalFileExtensionException("Illegal file extension: " + extension);
    }

    private void assignFile(File file, String path) throws IOException {
        FileUtils.saveFile(file, path);
        countFileTransfer(FileUtils.getFileFromPath(COUNT_FILE_TRANSFER_FILE));
    }

    private void countFileTransfer(File file) throws IOException {
        String value = FileUtils.readFile(file.getPath());
        if(value.equals(""))
            FileUtils.overwriteFile(file.getPath(), String.valueOf(1));
        else
            FileUtils.overwriteFile(file.getPath(), String.valueOf(Integer.parseInt(value.trim()) + 1));
    }
}
