package br.com.newidea.desafiotattooapp.translator;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import br.com.newidea.desafiotattooapp.dto.TattooDTO;
import br.com.newidea.desafiotattooapp.dto.request.TattooRequestDTO;
import br.com.newidea.desafiotattooapp.dto.response.TattooResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TattooTranslator {

    //Traduz o que veio do request para o DTO
    public TattooDTO toDTO(TattooRequestDTO requestDTO) {
        return TattooDTO.builder()
                .id(requestDTO.getId())
                .descricao(requestDTO.getDescricao())
                .localCorpo(requestDTO.getLocalCorpo())
                .estilo(requestDTO.getEstilo())
                .numeroSessoes(requestDTO.getNumeroSessoes())
                .valor(requestDTO.getValor())
                .build();

    }

    //Traduz o DTO para entity, recebendo o DTO por parametro
    public TattooEntity toEntity(TattooDTO dto) {
        return toEntity(dto, TattooEntity.builder().build());
    }

    //Traduz o DTO para a entity, recebendo um DTO e um entity por parametro
    public TattooEntity toEntity(TattooDTO dto, TattooEntity entity) {
        entity.setId(dto.getId());
        entity.setDescricao(dto.getDescricao());
        entity.setLocalCorpo(dto.getLocalCorpo());
        entity.setEstilo(dto.getEstilo());
        entity.setNumeroSessoes(dto.getNumeroSessoes());
        entity.setValor(dto.getValor());
        return entity;
    }

    //Traduz a entity para a o response
    public TattooResponseDTO toResponse(TattooEntity entity) {
        return TattooResponseDTO.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .localCorpo(entity.getLocalCorpo())
                .estilo(entity.getEstilo())
                .numeroSessoes(entity.getNumeroSessoes())
                .valor(entity.getValor())
                .build();
    }

    //TODO: Pedir ao Fabio para explicar o lambda
    //Traduz uma lista de entity para uma lista de response
    public List<TattooResponseDTO> toResponse(List<TattooEntity> entityList) {
        List<TattooResponseDTO> responseDTOList = new ArrayList<TattooResponseDTO>();
        entityList.forEach(
                tattooEntity ->
                responseDTOList.add(toResponse(tattooEntity))
        );
        return responseDTOList;
    }


}
