package br.com.newidea.desafiotattooapp.handler;

import br.com.newidea.desafiotattooapp.dto.response.ExceptionResponseDTO;
import br.com.newidea.desafiotattooapp.exception.DropBoxUnavailableException;
import br.com.newidea.desafiotattooapp.utils.TattooConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DropBoxExceptionHandler {

    @ExceptionHandler(DropBoxUnavailableException.class)
    public ResponseEntity<ExceptionResponseDTO> dropBoxUnvailableException(DropBoxUnavailableException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponseDTO.builder()
                        .code(TattooConstants.DROP_BOX_SERVICE_UNVAILABLE)
                        .message("Serviço de hospedagem de imagens indisponível no momento." + "\n" +
                                "Motivo: " + e.getDropBoxErrorMessage())
                        .build());
    }
}
