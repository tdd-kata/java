package org.xpdojo.spring;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultipartController {

    private final MultipartService multipartService;

    public MultipartController(MultipartService multipartService) {
        this.multipartService = multipartService;
    }

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
        return multipartService.upload(file);
    }

    /**
     * class 파일을 찾아서 응답한다.
     *
     * <pre>
     * curl --verbose --request GET host.docker.internal:8080/download/Application.class -O
     * </pre>
     *
     * @param fileName class 파일명
     * @return 파일 데이터
     * @throws IOException 파일을 찾을 수 없을 경우
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types">MIME 타입</a>
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types">MIME 타입의 전체 목록</a>
     * @see <a href="https://www.iana.org/assignments/media-types/media-types.xhtml">Media Types</a>
     */
    @GetMapping(path = "/download/{fileName:.+}"/*, produces = MediaType.IMAGE_JPEG_VALUE*/)
    public byte[] downloadFile(@PathVariable String fileName)
            throws IOException {
        return multipartService.download(fileName);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileUploadException.class)
    private String handleFileUploadException(FileUploadException e) {
        return e.getLocalizedMessage();
    }
}
