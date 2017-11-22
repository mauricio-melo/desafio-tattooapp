package br.com.newidea.desafiotattooapp.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TattooDTO {

    private long id;
    private String descricao;
    private String localCorpo;
    private String estilo;
    private int numeroSessoes;
    private BigDecimal valor;
}
