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
import java.io.InputStream;

@RestController
public class MultipartController {

    private final MultipartService multipartService;

    public MultipartController(MultipartService multipartService) {
        this.multipartService = multipartService;
    }

    /**
     * 바이너리 데이터를 <code>multipart/form-data</code> 타입으로 업로드한다.
     * <p>
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
     * @see <a href="https://www.rfc-editor.org/rfc/rfc1867.html">RFC1867 - Form-based File Upload in HTML</a>
     * @see <a href="https://www.rfc-editor.org/rfc/rfc2388.html">RFC2388 - Returning Values from Forms: multipart/form-data</a>
     */
    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)
            throws IOException {
        return multipartService.upload(file);
    }

    /**
     * 바이너리 데이터를 <code>application/x-www-form-urlencoded</code> 타입으로 업로드한다.
     *
     * <pre>
     * curl --verbose --request POST --data-binary "@settings/images/windows11-neofetch.png" host.docker.internal:8080/upload/bin -o test.png
     * </pre>
     * <p>
     * 바이너리 데이터를 <code>text/plain</code> 타입으로 업로드한다.
     *
     * <pre>
     * curl --verbose -H "Content-Type: text/plain" --data "this is raw data" host.docker.internal:8080/upload/bin
     * curl --verbose -H "Content-Type: text/plain" --data "@settings/README.md" host.docker.internal:8080/upload/bin
     * curl --verbose -H "Content-Type: text/plain" --data-binary "@settings/README.md" host.docker.internal:8080/upload/bin
     * curl --verbose -H "Content-Type: text/plain" --data-binary "@settings/images/windows11-neofetch.png" host.docker.internal:8080/upload/bin -o test.png
     * </pre>
     * <p>
     * Non-alphanumeric characters in both keys and values are percent encoded:
     * this is the reason why this type is not suitable to use with binary data
     * (use multipart/form-data instead)
     * <p>
     * MIME 타입이 없을 때, 혹은 클라이언트가 타입이 잘못 설정됐다고 판단한 어떤 다른 경우에, 브라우저들은 MIME 스니핑을 시도할 수도 있는데,
     * 이는 리소스를 훑어보고 정확한 MIME 타입을 추축해내는 것입니다.
     *
     * @param inputStream 바이너리 데이터
     * @return 바이너리 데이터
     * @throws IOException 파일을 읽을 수 없는 경우
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Methods/POST">POST</a>
     * @see <a href="https://developer.mozilla.org/en-US/docs/Glossary/percent-encoding">Percent-encoding</a>
     */
    @PostMapping(path = "/upload/bin")
    public byte[] uploadBinary(InputStream inputStream)
            throws IOException {
        return multipartService.uploadBinary(inputStream);
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
