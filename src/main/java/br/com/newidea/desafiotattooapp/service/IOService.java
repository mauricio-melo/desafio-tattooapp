package br.com.newidea.desafiotattooapp.service;

import br.com.newidea.desafiotattooapp.exception.IOServiceException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Calendar;
import java.util.Optional;

@Service
public class IOService {

    @Autowired
    private ServletContext servletContext;


    public File getFile(String imageFileName, @NonNull final String _64ByteBaseImage) {

        //Converte base64 para byte[]
        byte[] decodedByteArray = get64DecodedByteArray(_64ByteBaseImage);

        //armazena o diretorio do Tomcat
        final String destinationPath = servletContext.getRealPath("/");

        //armazena o nome do arquivo temporario
        final String temporaryFileName = getTemporaryFileName(imageFileName);

        //armazena o caminho e o nome do arquivo temporario
        Path destinationFile = Paths.get(destinationPath, temporaryFileName);

        try {
            //joga o byte[] no diretorio
            final Path filePath = Files.write(destinationFile, decodedByteArray);

            //gera um arquivo
            final File imageFile = filePath.toFile();

            //pega a ultima imagem setada no diretorio
            imageFile.setLastModified(Calendar.getInstance().getTimeInMillis());

            //retorna arquivo
            return imageFile;

        } catch (IOException e) {
            throw new IOServiceException(e.getMessage());
        }
    }

    public void deleteFile(final File file) {

        Optional.ofNullable(file)
                .ifPresent(file1 -> file.delete());
    }

    private byte[] get64DecodedByteArray(@NonNull String _64ByteBaseImage) {
        String encodedString = _64ByteBaseImage;
        encodedString = encodedString.replace("\n", "").replace("\r", "");
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(encodedString);
    }

    public String getTemporaryFileName(String fileName) {

        final Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        final int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        final int day = now.get(Calendar.DAY_OF_MONTH);
        final int hour = now.get(Calendar.HOUR_OF_DAY);
        final int minute = now.get(Calendar.MINUTE);
        final int second = now.get(Calendar.SECOND);
        final int millis = now.get(Calendar.MILLISECOND);

        final int endIndex = fileName.lastIndexOf(".");
        final String extension = fileName.substring(endIndex);

        return "teste"
                .concat("")
                .concat("y" + year)
                .concat("m" + month)
                .concat("d" + day)
                .concat("h" + hour)
                .concat("m" + minute)
                .concat("s" + second)
                .concat("mi" + millis)
                .concat(extension);
    }
}