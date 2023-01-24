package org.xpdojo.file.filepond;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.xpdojo.file.storage.Storage;
import org.xpdojo.file.spring.SpringMultipartService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FilepondMultipartController {

    private final Storage storage;
    private final SpringMultipartService springMultipartService;

    @GetMapping("/filepond")
    public String filepond() {
        return "filepond";
    }

    @PostMapping(path = "/filepond", consumes = "multipart/form-data")
    public String saveFilepond(
            @RequestParam(name = "multipleFiles") List<MultipartFile> files
    ) throws IOException {
        springMultipartService.saveMultipart(files);
        return "redirect:/filepond";
    }

    /**
     * Resolved [org.springframework.web.multipart.support.MissingServletRequestPartException: Required request part 'multipleFiles' is not present]
     * <p>
     * org.thymeleaf.TemplateEngine             : [THYMELEAF][http-nio-8080-exec-3] Exception processing template "filepond/1": Error resolving template [filepond/1], template might not exist or might not be accessible by any of the configured Template Resolvers
     *
     * @param id    디렉토리 ID
     * @param files 추가할 파일
     * @throws IOException 파일 저장 실패
     */
    @PostMapping(path = "/filepond-append/{id}", consumes = "multipart/form-data")
    @ResponseBody
    public void addFilepond(
            @PathVariable String id,
            @RequestParam(name = "multipleFiles") List<MultipartFile> files
    ) throws IOException {
        springMultipartService.addMultipart(id, files);
    }

    /**
     * @see <a href="https://pqina.nl/filepond/docs/api/server/#load">Load</a>
     * @see <a href="https://pqina.nl/filepond/docs/api/instance/properties/#files">Files</a>
     */
    @GetMapping(path = "/fileload/{id}/{path}")
    public ResponseEntity<Resource> fileload(
            @PathVariable String id,
            @PathVariable String path
    )
            throws IOException {
        UrlResource resource = new UrlResource("file:" + storage.getUploadDirectory() + id + "/" + path);

        String encodedUploadFileName = UriUtils.encode(path, StandardCharsets.UTF_8);
        String contentDisposition = "inline; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, "image/" + path.substring(path.lastIndexOf(".") + 1))
                .body(resource);
    }

    /**
     * 파일 삭제
     *
     * @param id       디렉토리 ID
     * @param filename 삭제할 파일
     */
    @DeleteMapping("/filepond/{id}/{filename}")
    @ResponseBody
    public void removeFilepond(
            @PathVariable String id,
            @PathVariable String filename) {
        springMultipartService.removeMultipart(id, filename);
    }

}
