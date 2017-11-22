package br.com.newidea.desafiotattooapp.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@Builder
public class TattooResponseDTO {

    @ApiModelProperty(notes = "ID da tatuagem.", example = "1", required = true, position = 0)
    private long id;

    @ApiModelProperty(notes = "Descrição da tatuagem.", example = "Lobo preto e branco com o olho azul.", required = true, position = 1)
    private String descricao;

    @ApiModelProperty(notes = "Nome do local do corpo.", example = "Antebraço esquerdo.", required = true, position = 2)
    private String localCorpo;

    @ApiModelProperty(notes = "Estilo da tatuagem", example = "Realismo.", required = true, position = 3)
    private String estilo;

    @ApiModelProperty(notes = "Numero de sessões da tatuagem", example = "3", required = true, position = 4)
    private int numeroSessoes;

    @NumberFormat(pattern = "#,##0.00")
    @ApiModelProperty(notes = "Valor sugerido da tatuagem.", example = "2500.00", required = true, position = 5)
    private BigDecimal valor;
}
