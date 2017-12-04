package br.com.newidea.desafiotattooapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IOServiceException extends RuntimeException {

    private String ioErrorMessage;

    public IOServiceException(final String ioErrorMessage) {
        setIoErrorMessage(ioErrorMessage);
    }
}


