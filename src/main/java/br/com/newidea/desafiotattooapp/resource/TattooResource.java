package br.com.newidea.desafiotattooapp.resource;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import br.com.newidea.desafiotattooapp.dto.TattooDTO;
import br.com.newidea.desafiotattooapp.dto.request.TattooRequestDTO;
import br.com.newidea.desafiotattooapp.dto.response.TattooResponseDTO;
import br.com.newidea.desafiotattooapp.service.TattooService;
import br.com.newidea.desafiotattooapp.translator.TattooTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/tattoo")
@Api(value = "Tatuagem.", description = "Operações disponíveis para o recurso tattoo")
public class TattooResource {

    @Autowired
    private TattooTranslator translator;

    @Autowired
    private TattooService service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Criação de um novo recurso tattoo", responseReference = "Novo recurso criado.")
    public ResponseEntity<TattooEntity> create(@Valid @RequestBody TattooRequestDTO requestDTO) {

        //Traduzindo requisição recebida para DTO
        final TattooDTO tattooDTO = translator.toDTO(requestDTO);

        //Acionando service create
        final TattooEntity entity = service.create(tattooDTO);

        //retornando a responseActivity com o status created
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Atualização de um recurso tattoo", responseReference = "200 = Recurso atualizado com sucesso.")
    public ResponseEntity<TattooEntity> update(@Valid @RequestBody final TattooRequestDTO requestDTO) {

        //Traduzindo requisição recebida para DTO
        final TattooDTO tattooDTO = translator.toDTO(requestDTO);

        //Acionando service update
        final TattooEntity entity = service.update(tattooDTO);

        //retornando a responseActivity com o status ok
        return ResponseEntity.ok(entity);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Pesquisa de um recurso tattoo em específico", response = TattooResponseDTO.class)
    public ResponseEntity<TattooResponseDTO> findById(@PathVariable final Long id) {

        //Efetuando busca da entidade na base dados pelo id
        final TattooEntity entity = service.findById(id);

        //Traduzindo Entity para Response
        final TattooResponseDTO responseDTO = translator.toResponse(entity);

        //Retornando a responseEntity com o response com o status ok
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lista todos as tattoo's", response = TattooResponseDTO.class, responseContainer = "List")
    public ResponseEntity<List<TattooResponseDTO>> list() {

        //Buscando todos os recursos tattoo para Requisição recebida
        final List<TattooEntity> entityList = service.findAll();

        //Traduzindo entity para response
        final List<TattooResponseDTO> responseList = translator.toResponse(entityList);

        //Retornando a responseEntity com o response com status ok
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        //Acinando o service de conclusão
        service.delete(id);

        //Retornando a responseEntity com o response com status ok
        return ResponseEntity.ok().build();
    }



}
