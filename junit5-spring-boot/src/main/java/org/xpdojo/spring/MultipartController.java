package org.xpdojo.spring;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class MultipartController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * curl을 사용해서 테스트하는 경우
     * <p>
     * <pre>
     * curl --verbose --request POST --form 'file=@./README.md' host.docker.internal:8080/upload
     * curl --verbose --request POST --form 'file=@./README.md' --form 'title=hello' host.docker.internal:8080/upload
     * </pre>
     * 파일 여러 개를 전송할 경우
     * <pre>
     * curl --verbose --request POST --form 'file1=@./README.md' --form 'file2=@./.vimrc' host.docker.internal:8080/upload
     * </pre>
     * 변수가 배열인 경우
     * <pre>
     * curl --verbose --request POST --form 'file[]=@./README.md' --form 'file[]=@./.vimrc' host.docker.internal:8080/upload
     * </pre>
     *
     * @param file 업로드 파일
     * @throws IOException 파일을 찾을 수 없는 경우
     */
    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) {
            throw new FileUploadException("File is empty.");
        }

        log.info("[file] original file name - {}", file.getOriginalFilename());
        log.info("[file] size - {}", file.getSize());

        final String fileContent = readFileToString(file.getInputStream());
        log.info("[file] content - {}", fileContent);

        return fileContent;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileUploadException.class)
    private String handleFileUploadException(FileUploadException e) {
        return e.getLocalizedMessage();
    }
}
