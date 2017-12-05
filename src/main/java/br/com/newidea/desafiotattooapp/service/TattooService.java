package br.com.newidea.desafiotattooapp.service;

import br.com.newidea.desafiotattooapp.domain.TattooEntity;
import br.com.newidea.desafiotattooapp.dto.DropBoxFileUploadDTO;
import br.com.newidea.desafiotattooapp.dto.TattooDTO;
import br.com.newidea.desafiotattooapp.repository.TattooRepository;
import br.com.newidea.desafiotattooapp.service.transactional.TattooTransactionalService;
import br.com.newidea.desafiotattooapp.translator.TattooTranslator;
import br.com.newidea.desafiotattooapp.utils.TattooConstants;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class TattooService {

    @Autowired
    private TattooTranslator translator;

    @Autowired
    private TattooRepository tattooRepository;

    @Autowired
    private TattooTransactionalService tattooTransactionalService;

    @Autowired
    private IOService ioService;

    @Autowired
    private DropBoxService dropBoxService;

    //Salvar tattoo no banco de dados(post)
    public TattooEntity create(@NonNull final TattooDTO dto) {
        //Tradução de DTO para Entity
        TattooEntity entity = translator.toEntity(dto);

        //Obtendo file temporary da imagem
        Optional<File> jobPictureFile = getPictureFile(dto, entity);
        try {

            //Atualizando picture referente a entidade
            final Optional<DropBoxFileUploadDTO> dropBoxFileUpload = updateUploadedPicture(entity, jobPictureFile);

            //Atualizando valores referentes ao Upload na entidade
            entity = updateUploadData(entity, dropBoxFileUpload);

            //salvando
            return tattooTransactionalService.save(entity);

        } catch (Exception e) {

            try {
                dropBoxService.deleteUploadedFile(TattooConstants.DROP_BOX_JOB_PATH, jobPictureFile.get().getName());
            } catch (Exception e2) {
            }
            throw e;

        } finally {

            //Deletando arquivo temporario gerado
            //ta dando NoSuchElementException TODO: VER COM O FABIO
            //ioService.deleteFile(jobPictureFile.get());
        }
    }

    //alterando foto da tatuagem
    private Optional<DropBoxFileUploadDTO> updateUploadedPicture(@NonNull final TattooEntity entity,
                                                                 @NonNull final Optional<File> file) {

        //ofNullable = possibilidade de estar vazio o pathDisplay
        //Optional é para evitar o NullPointerException
        final Optional<String> dropBoxPathDisplay = Optional.ofNullable(entity.getDropBoxPathDisplay()).filter(s -> !s.isEmpty());

        //se existe
        dropBoxPathDisplay.ifPresent(path -> {
            //Deletando upload de imagem antiga passando o path
            dropBoxService.deleteUploadedFile(path);
        });

        //retorna o file, caso nao tenha nada, nao da NullPointerException
        //pois map retorna um novo Optional
        return file
                .map(
                        fileToUpload -> {
                            //Efetuando upload de arquivo para o DropBox
                            return dropBoxService.uploadFile(TattooConstants.DROP_BOX_JOB_PATH, fileToUpload);
                        }
                );
    }


    //retornando a foto
    private Optional<File> getPictureFile(@NonNull final TattooDTO dto, @NonNull final TattooEntity entity) {

        //ofNullable = se não tiver nada, não retorna um NullPointerException

        return Optional.ofNullable(dto.getBase64ByteImagem())
                .filter(image64Bytes -> !image64Bytes.isEmpty())
                .map(
                        image64Bytes -> {

                            //Gerando arquivo temporário para Upload
                            return ioService.getFile(
                                    dto.getImageFileName(),
                                    dto.getBase64ByteImagem()
                            );
                        }
                );
    }

    //alterando valores do dropbox na entity
    private TattooEntity updateUploadData(@NonNull final TattooEntity entity, @NonNull final Optional<DropBoxFileUploadDTO> dropBoxFileDTO) {
        return dropBoxFileDTO
                .map(
                        dtoFile -> {
                            entity.setDropBoxId(dtoFile.getDropBoxId());
                            entity.setDropBoxPathLower(dtoFile.getDropBoxPathLower());
                            entity.setDropBoxPathDisplay(dtoFile.getDropBoxPathDisplay());
                            entity.setDropBoxContentHash(dtoFile.getDropBoxContentHash());
                            entity.setDropBoxSharedUrlOrigin(dtoFile.getDropBoxSharedUrlOrigin());
                            entity.setDropBoxSharedUrlDirect(dtoFile.getDropBoxSharedUrlDirect());
                            entity.setDropBoxSharedId(dtoFile.getDropBoxSharedId());
                            return entity;
                        }
                ).orElseGet(() -> {
                            entity.setDropBoxId("");
                            entity.setDropBoxPathLower("");
                            entity.setDropBoxPathDisplay("");
                            entity.setDropBoxContentHash("");
                            entity.setDropBoxSharedUrlOrigin("");
                            entity.setDropBoxSharedUrlDirect("");
                            entity.setDropBoxSharedId("");
                            return entity;
                        }
                );
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


    public List<TattooEntity> findAll() {
        return tattooRepository.findAll();
    }

}
