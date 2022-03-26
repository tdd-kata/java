package org.xpdojo.spring;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class MultipartService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public String upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileUploadException("File is empty.");
        }

        log.info("[file] original file name - {}", file.getOriginalFilename());
        log.info("[file] size - {}", file.getSize());

        final String fileContent = readFileToString(file.getInputStream());
        log.info("[file] content - {}", fileContent);

        return fileContent;
    }

    public byte[] download(String fileName) throws IOException {
        final InputStream in = getClass().getResourceAsStream(fileName);
        // return new InputStreamResource(in);
        return IOUtils.toByteArray(in);
    }

    private String readFileToString(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();

        try (
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
