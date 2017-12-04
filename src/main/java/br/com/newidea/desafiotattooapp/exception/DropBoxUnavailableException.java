package br.com.newidea.desafiotattooapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DropBoxUnavailableException extends RuntimeException {

    private String dropBoxErrorMessage;

    public DropBoxUnavailableException(final String dropBoxErrorMessage) {
        setDropBoxErrorMessage(dropBoxErrorMessage);
    }
}