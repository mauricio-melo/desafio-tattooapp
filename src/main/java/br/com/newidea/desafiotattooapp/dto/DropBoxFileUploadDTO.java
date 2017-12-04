package br.com.newidea.desafiotattooapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DropBoxFileUploadDTO {

    private String dropBoxId;

    private String dropBoxPathLower;

    private String dropBoxPathDisplay;

    private String dropBoxContentHash;

    private String dropBoxSharedUrlOrigin;

    private String dropBoxSharedUrlDirect;

    private String dropBoxSharedId;
}
