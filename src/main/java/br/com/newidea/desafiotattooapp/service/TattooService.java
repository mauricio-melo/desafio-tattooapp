package br.com.newidea.desafiotattooapp.service;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import br.com.newidea.desafiotattooapp.dto.TattooDTO;
import br.com.newidea.desafiotattooapp.dto.request.TattooRequestDTO;
import br.com.newidea.desafiotattooapp.repository.TattooRepository;
import br.com.newidea.desafiotattooapp.service.transactional.TattooTransactionalService;
import br.com.newidea.desafiotattooapp.translator.TattooTranslator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TattooService {

    @Autowired
    private TattooTranslator translator;

    @Autowired
    private TattooRepository tattooRepository;

    @Autowired
    private TattooTransactionalService tattooTransactionalService;

    //Salvar tattoo no banco de dados(post)
    public TattooEntity create(@NonNull final TattooDTO dto) {
        //Tradução de DTO para Entity
        TattooEntity entity = translator.toEntity(dto);
        //Salvando entity no banco
        entity = tattooTransactionalService.save(entity);
        return entity;
    }

    //Alterar tattoo no banco de dados(put)
    public TattooEntity update(@NonNull final TattooDTO dto) {

        //Efetuando busca da entidade na base dados
        TattooEntity entity = findById(dto.getId());

        //Tradução de DTO para Entity
        //TODO:Diferença entre toEntity com um e com dois parametros
        entity = translator.toEntity(dto, entity);

        //Salvando nova entity
        entity = tattooTransactionalService.save(entity);
        return entity;
    }

    public void delete(Long id) {
        tattooRepository.delete(id);
    }

    public TattooEntity findById(long id) {
        return tattooRepository.findOne(id);
    }

    public List<TattooEntity> listar(){
        return tattooRepository.findAll();
    }

    public List<TattooEntity> findAll() {
        return tattooRepository.findAll();
    }

}
