package br.com.newidea.desafiotattooapp.service;

import br.com.newidea.desafiotattooapp.dto.DropBoxFileUploadDTO;
import br.com.newidea.desafiotattooapp.exception.DropBoxUnavailableException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

@Service
public class DropBoxService {
    @Value("${dropbox.appKey}")
    private String dropBoxAppKey;

    @Value("${dropbox.appSecret}")
    private String dropBoxAppSecret;

    @Value("${dropbox.accessToken}")
    private String dropBoxAccessToken;

    @Value("${dropbox.clientIdentifier}")
    private String dropBoxClientIdentifier;


    public DropBoxFileUploadDTO uploadFile(@NonNull final String path, @NonNull final File file){

        //criando o client atraves do access token da conta
        final DbxClientV2 dbxClientV2 = new DbxClientV2(getDbxRequestConfig(), dropBoxAccessToken);

        //Iniciando InputStream
        try (InputStream in = new FileInputStream(file)) {

            //armaenando o caminho e nome do arquivo
            final String dropBoxFilePath = getDropBoxFilePath(path, file.getName());

            //Efetuando upload para dropBox, passando o caminho do arquivo
            final FileMetadata fileMetadata = dbxClientV2.files().uploadBuilder(dropBoxFilePath)
                    .withMode(WriteMode.ADD)
                    .withClientModified(new Date(file.lastModified()))
                    .uploadAndFinish(in);

            //Alterando e obtendo shared link
            final SharedLinkMetadata sharedLinkMetadata = dbxClientV2.sharing().createSharedLinkWithSettings(dropBoxFilePath, getSharedLinkSettings());

            //armazenando os retornos do upload na classe DTO e obtendo o link direto e o da origem
            return buildResponseDTO(fileMetadata, sharedLinkMetadata);
        } catch (Exception e) {
            throw new DropBoxUnavailableException(e.getMessage());
        }


    }

    //caminho do arquivo
    private String getDropBoxFilePath(@NonNull final String path, @NonNull final String fileName) {

        return "/".concat(path).concat("/").concat(fileName);
    }

    //armazenando os retornos do upload para o responseDTO
    private DropBoxFileUploadDTO buildResponseDTO(FileMetadata fileMetadata, SharedLinkMetadata sharedLinkMetadata) {

        return DropBoxFileUploadDTO.builder()
                .dropBoxId(fileMetadata.getId())
                .dropBoxPathLower(fileMetadata.getPathLower())
                .dropBoxPathDisplay(fileMetadata.getPathDisplay())
                .dropBoxContentHash(fileMetadata.getContentHash())
                .dropBoxSharedUrlOrigin(sharedLinkMetadata.getUrl())
                .dropBoxSharedUrlDirect(getDirectSharedUrl(sharedLinkMetadata.getUrl()))
                .dropBoxSharedId(sharedLinkMetadata.getId())
                .build();
    }

    //optendo o link direto atraves do link do link compartilhado
    private String getDirectSharedUrl(String url) {

        //isso esta na documentação. Acrescentar raw=1 a frente do link gerado é o link direto
        final int endIndex = url.lastIndexOf("?") + 1;
        return url.substring(0, endIndex).concat("raw=1");
    }


    //tornando o link publico para que o app consiga abrir
    private SharedLinkSettings getSharedLinkSettings() {

        final SharedLinkSettings.Builder sharedLinkBuilder = SharedLinkSettings
                .newBuilder()
                .withRequestedVisibility(RequestedVisibility.PUBLIC);

        return sharedLinkBuilder.build();
    }

    //nao entendi TODO: Ver com o Fabio
    private DbxRequestConfig getDbxRequestConfig() {

        final DbxRequestConfig.Builder builder = DbxRequestConfig
                .newBuilder(dropBoxClientIdentifier)
                .withUserLocale(Locale.getDefault().toString());
        return builder.build();
    }

    //Apagando um arquivo atraves do caminho e nome do arquivo
    public void deleteUploadedFile(@NonNull final String path, @NonNull final String fileName) {
        final String dropBoxFilePath = getDropBoxFilePath(path, fileName);
        deleteUploadedFile(dropBoxFilePath);
    }

    //Apagando um arquivo com o caminho ja capturado
    public void deleteUploadedFile(@NonNull final String dropBoxFilePath) {

        //criando o client
        final DbxClientV2 dbxClientV2 = new DbxClientV2(getDbxRequestConfig(), dropBoxAccessToken);

        //Efetuando delete de possível upload que ocorreu para o dropBox
        try {
            dbxClientV2.files().delete(dropBoxFilePath);
        } catch (Exception e) {
            throw new DropBoxUnavailableException(e.getMessage());
        }
    }
}
