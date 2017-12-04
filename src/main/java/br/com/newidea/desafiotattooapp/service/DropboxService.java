package br.com.newidea.desafiotattooapp.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DropboxService {
    @Value("${dropbox.appKey}")
    private String dropBoxAppKey;

    @Value("${dropbox.appSecret}")
    private String dropBoxAppSecret;

    @Value("${dropbox.accessToken}")
    private String dropBoxAccessToken;

    @Value("${dropbox.clientIdentifier}")
    private String dropBoxClientIdentifier;


    public DropBoxFileUploadDTO uploadFile(@NonNull final String path, @NonNull final File file){

    }
}
