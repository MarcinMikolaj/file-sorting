package com.file.sorting;

import com.file.sorting.infrastructure.exception.exceptions.IllegalFileExtensionException;
import com.file.sorting.service.observer.FileObserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FileSortingApplication {

	public static void main(String[] args) throws IOException, InterruptedException, IllegalFileExtensionException {
		SpringApplication.run(FileSortingApplication.class, args);
		FileObserver fileObserver = new FileObserver();
		fileObserver.initialize();
	}

}
