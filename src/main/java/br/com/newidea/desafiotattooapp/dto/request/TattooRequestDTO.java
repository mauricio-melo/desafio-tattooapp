package br.com.newidea.desafiotattooapp.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class TattooRequestDTO {

    @ApiModelProperty(notes = "ID da tatuagem.", example = "1", required = true, position = 0)
    private Long id;

    @NotNull(message = "Campo \"descricao\" precisa ser informado.")
    @ApiModelProperty(notes = "Descrição da tatuagem.", example = "Lobo preto e branco com o olho azul.", required = true, position = 1)
    private String descricao;

    @NotNull(message = "Campo \"localCorpo\" precisa ser informado.")
    @ApiModelProperty(notes = "Nome do local do corpo.", example = "Antebraço esquerdo.", required = true, position = 2)
    private String localCorpo;

    @NotNull(message = "Campo \"estilo\" precisa ser informado.")
    @ApiModelProperty(notes = "Estilo da tatuagem", example = "Realismo.", required = true, position = 3)
    private String estilo;

    @NotNull(message = "Campo \"numeroSessoes\" precisa ser informado.")
    @ApiModelProperty(notes = "Numero de sessões da tatuagem", example = "3", required = true, position = 4)
    private int numeroSessoes;

    @NumberFormat(pattern = "#,##0.00")
    @NotNull(message = "Campo \"valor\" precisa ser informado.")
    @ApiModelProperty(notes = "Valor sugerido da tatuagem.", example = "2500.00", required = true, position = 5)
    private BigDecimal valor;

    @ApiModelProperty(notes = "Nome da imagem convertida para base 64 em bytes.", example = "imagem.bpm", required = false, position = 6)
    private String imageFileName;

    @ApiModelProperty(notes = "Imagem do Job convertida para base 64 em bytes.", example = "LKODOIQoiioqj1091nKJSDOJQDWPKLlkjqdopqwdjqwdoiqwoi109-192IJ12IODIODDNAJOIASHIDOAHHh08H9h98", required = false, position = 7)
    private String base64ByteImagem;

}
