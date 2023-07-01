package com.file.sorting.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalFileExtensionException extends Exception{
    public IllegalFileExtensionException(String message) {
        super(message);
    }

}
