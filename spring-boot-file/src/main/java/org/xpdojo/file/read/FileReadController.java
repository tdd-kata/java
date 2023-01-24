package org.xpdojo.file.read;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.xpdojo.file.storage.Storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileReadController {

    private final Storage storage;
    private final FileReadService fileReadService;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        model.addAttribute("filePaths", fileReadService.listFile());
        model.addAttribute("directory", storage.getUploadDirectory());
        return "index";
    }

    @GetMapping("/files/{id}")
    @ResponseBody
    public Set<String> listFileNamesByDirectoryId(@PathVariable int id) throws IOException {
        return fileReadService.listFilesInDirectory(id);
    }

    @GetMapping("/directories/{id}")
    public String getDirectoryById(@PathVariable int id, Model model) throws IOException {
        model.addAttribute("filePaths", fileReadService.listFilesInDirectory(id));
        model.addAttribute("directory", storage.getUploadDirectory() + id);
        return "list";
    }

    @GetMapping("/images/{id}/{path}")
    @ResponseBody
    public Resource showImage(@PathVariable String id, @PathVariable String path) throws MalformedURLException {
        log.info(id + "/" + path);
        return new UrlResource("file:" + storage.getUploadDirectory() + id + "/" + path);
    }

    @GetMapping("/download/{id}/{path:.+}")
    public ResponseEntity<Resource> download(
            @PathVariable String id,
            @PathVariable String path)
            throws MalformedURLException {

        UrlResource resource = new UrlResource("file:" + storage.getUploadDirectory() + id + "/" + path);

        String encodedUploadFileName = UriUtils.encode(path, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
