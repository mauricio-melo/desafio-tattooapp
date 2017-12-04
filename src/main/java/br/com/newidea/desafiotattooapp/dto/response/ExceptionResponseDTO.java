package br.com.newidea.desafiotattooapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponseDTO {

    private int code;

    private String message;
}

